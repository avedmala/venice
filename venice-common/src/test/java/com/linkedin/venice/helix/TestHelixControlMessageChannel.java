package com.linkedin.venice.helix;

import com.linkedin.venice.controlmessage.StatusUpdateMessage;
import com.linkedin.venice.controlmessage.StatusUpdateMessageHandler;
import com.linkedin.venice.exceptions.VeniceException;
import com.linkedin.venice.utils.ZkServerWrapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.helix.HelixAdmin;
import org.apache.helix.HelixManager;
import org.apache.helix.HelixManagerFactory;
import org.apache.helix.InstanceType;
import org.apache.helix.controller.HelixControllerMain;
import org.apache.helix.manager.zk.ZKHelixAdmin;
import org.apache.helix.manager.zk.ZKHelixManager;
import org.apache.helix.model.HelixConfigScope;
import org.apache.helix.model.IdealState;
import org.apache.helix.model.Message;
import org.apache.helix.model.builder.HelixConfigScopeBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Test cases for HelixControlMessageChannel
 */
public class TestHelixControlMessageChannel {
  private String cluster = "UnitTestCluster";
  private String kafkaTopic = "test_resource_1";
  private int partitionId = 0;
  private String instanceId = "localhost_1234";
  private StatusUpdateMessage.Status status = StatusUpdateMessage.Status.FINALIZED;
  private ZkServerWrapper zkServerWrapper;
  private String zkAddress;
  private HelixControlMessageChannel channel;
  private HelixManager manager;
  private HelixAdmin admin;
  private HelixManager controller;

  @BeforeMethod
  public void setup()
      throws Exception {
    zkServerWrapper = ZkServerWrapper.getZkServer();
    zkAddress = zkServerWrapper.getZkAddress();
    admin = new ZKHelixAdmin(zkAddress);
    admin.addCluster(cluster);
    HelixConfigScope configScope = new HelixConfigScopeBuilder(HelixConfigScope.ConfigScopeProperty.CLUSTER).
        forCluster(cluster).build();
    Map<String, String> helixClusterProperties = new HashMap<String, String>();
    helixClusterProperties.put(ZKHelixManager.ALLOW_PARTICIPANT_AUTO_JOIN, String.valueOf(true));
    admin.setConfig(configScope, helixClusterProperties);
    admin.addStateModelDef(cluster, TestHelixRoutingDataRepository.UnitTestStateModel.UNIT_TEST_STATE_MODEL,
        TestHelixRoutingDataRepository.UnitTestStateModel.getDefinition());

    admin.addResource(cluster, kafkaTopic, 1, TestHelixRoutingDataRepository.UnitTestStateModel.UNIT_TEST_STATE_MODEL,
        IdealState.RebalanceMode.FULL_AUTO.toString());
    admin.rebalance(cluster, kafkaTopic, 1);

    controller = HelixControllerMain
        .startHelixController(zkAddress, cluster, "UnitTestController", HelixControllerMain.STANDALONE);
    controller.connect();

    manager = HelixManagerFactory.getZKHelixManager(cluster, instanceId, InstanceType.PARTICIPANT, zkAddress);
    manager.getStateMachineEngine()
        .registerStateModelFactory(TestHelixRoutingDataRepository.UnitTestStateModel.UNIT_TEST_STATE_MODEL,
            new TestHelixRoutingDataRepository.UnitTestStateModelFactory());

    manager.connect();
    channel = new HelixControlMessageChannel(manager);
  }

  @AfterMethod
  public void cleanup() {
    manager.disconnect();
    controller.disconnect();
    admin.dropCluster(cluster);
    admin.close();
    zkServerWrapper.close();
  }

  @Test
  public void testConvertBetweenVeniceMessageAndHelixMessage()
      throws ClassNotFoundException {
    StatusUpdateMessage veniceMessage = new StatusUpdateMessage(kafkaTopic, partitionId, instanceId, status);
    Message helixMessage = channel.convertVeniceMessageToHelixMessage(veniceMessage);
    Assert.assertEquals(veniceMessage.getMessageId(), helixMessage.getMsgId(),
        "Message Ids are different. Convert is failed.");
    Assert.assertEquals(StatusUpdateMessage.class.getName(),
        helixMessage.getRecord().getSimpleField(HelixControlMessageChannel.VENICE_MESSAGE_CLASS),
        "Class names are different. Convert is failed.");
    Map<String, String> fields = helixMessage.getRecord().getMapField(HelixControlMessageChannel.VENICE_MESSAGE_FIELD);
    for (Map.Entry<String, String> entry : veniceMessage.getFields().entrySet()) {
      Assert.assertEquals(entry.getValue(), fields.get(entry.getKey()),
          "Message fields are different. Convert is failed.");
    }

    StatusUpdateMessage convertedVeniceMessage = channel.convertHelixMessageToVeniceMessage(helixMessage);
    Assert.assertEquals(veniceMessage.getFields(), convertedVeniceMessage.getFields(),
        "Message fields are different. Convert it failed,");
  }

  @Test
  public void testRegisterHandler() {
    StatusUpdateMessageHandler hander = new StatusUpdateMessageHandler();

    channel.registerHandler(StatusUpdateMessage.class, hander);

    Assert.assertEquals(hander, channel.getHandler(StatusUpdateMessage.class),
        "Can not get correct handler.Register is failed.");

    channel.unRegisterHandler(StatusUpdateMessage.class, hander);
    try {
      channel.getHandler(StatusUpdateMessage.class);
      Assert.fail("Handler should be un-register before.");
    } catch (VeniceException e) {
      //Expected.
    }
  }

  @Test
  public void testSendMessage()
      throws IOException, InterruptedException {
    //Register handler for message in controler side.
    HelixControlMessageChannel controllerChannel = new HelixControlMessageChannel(controller);
    StatusUpdateMessageHandler handler = new StatusUpdateMessageHandler();
    controllerChannel.registerHandler(StatusUpdateMessage.class, handler);

    Thread.sleep(1000l);
    StatusUpdateMessage veniceMessage = new StatusUpdateMessage(kafkaTopic, partitionId, instanceId, status);
    channel.sendToController(veniceMessage);
    StatusUpdateMessage receivedMessage = handler.getStatus(veniceMessage.getKafkaTopic());
    Assert.assertNotNull(receivedMessage, "Message is not received.");
    Assert.assertEquals(veniceMessage.getMessageId(), receivedMessage.getMessageId(),
        "Message is not received correctly. Id is wrong.");

    Assert.assertEquals(veniceMessage.getFields(), receivedMessage.getFields(),
        "Message is not received correctly. Fields are wrong");
  }
}
