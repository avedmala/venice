/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.linkedin.venice.controller.kafka.protocol.admin;

@SuppressWarnings("all")
public class AdminOperation extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = org.apache.avro.Schema.parse("{\"type\":\"record\",\"name\":\"AdminOperation\",\"namespace\":\"com.linkedin.venice.controller.kafka.protocol.admin\",\"fields\":[{\"name\":\"operationType\",\"type\":\"int\",\"doc\":\"0 => StoreCreation, 1 => ValueSchemaCreation, 2 => PauseStore, 3 => ResumeStore, 4 => KillOfflinePushJob, 5 => DisableStoreRead, 6 => EnableStoreRead, 7=> DeleteAllVersions, 8=> SetStoreOwner, 9=> SetStorePartitionCount, 10=> SetStoreCurrentVersion, 11=> UpdateStore, 12=> DeleteStore, 13=> DeleteOldVersion, 14=> MigrateStore, 15=> AbortMigration, 16=>AddVersion, 17=> DerivedSchemaCreation\"},{\"name\":\"executionId\",\"type\":\"long\",\"doc\":\"ID of a command execution which is used to query the status of this command.\",\"default\":0},{\"name\":\"payloadUnion\",\"type\":[{\"type\":\"record\",\"name\":\"StoreCreation\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"owner\",\"type\":\"string\"},{\"name\":\"keySchema\",\"type\":{\"type\":\"record\",\"name\":\"SchemaMeta\",\"fields\":[{\"name\":\"schemaType\",\"type\":\"int\",\"doc\":\"0 => Avro-1.4, and we can add more if necessary\"},{\"name\":\"definition\",\"type\":\"string\"}]}},{\"name\":\"valueSchema\",\"type\":\"SchemaMeta\"}]},{\"type\":\"record\",\"name\":\"ValueSchemaCreation\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"schema\",\"type\":\"SchemaMeta\"},{\"name\":\"schemaId\",\"type\":\"int\"}]},{\"type\":\"record\",\"name\":\"PauseStore\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"ResumeStore\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"KillOfflinePushJob\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"kafkaTopic\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"DisableStoreRead\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"EnableStoreRead\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"DeleteAllVersions\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"SetStoreOwner\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"owner\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"SetStorePartitionCount\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"partitionNum\",\"type\":\"int\"}]},{\"type\":\"record\",\"name\":\"SetStoreCurrentVersion\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"currentVersion\",\"type\":\"int\"}]},{\"type\":\"record\",\"name\":\"UpdateStore\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"owner\",\"type\":\"string\"},{\"name\":\"partitionNum\",\"type\":\"int\"},{\"name\":\"currentVersion\",\"type\":\"int\"},{\"name\":\"enableReads\",\"type\":\"boolean\"},{\"name\":\"enableWrites\",\"type\":\"boolean\"},{\"name\":\"storageQuotaInByte\",\"type\":\"long\",\"default\":21474836480},{\"name\":\"readQuotaInCU\",\"type\":\"long\",\"default\":1800},{\"name\":\"hybridStoreConfig\",\"type\":[\"null\",{\"type\":\"record\",\"name\":\"HybridStoreConfigRecord\",\"fields\":[{\"name\":\"rewindTimeInSeconds\",\"type\":\"long\"},{\"name\":\"offsetLagThresholdToGoOnline\",\"type\":\"long\"}]}],\"default\":null},{\"name\":\"accessControlled\",\"type\":\"boolean\",\"default\":false},{\"name\":\"compressionStrategy\",\"type\":\"int\",\"doc\":\"Using int because Avro Enums are not evolvable\",\"default\":0},{\"name\":\"chunkingEnabled\",\"type\":\"boolean\",\"default\":false},{\"name\":\"singleGetRouterCacheEnabled\",\"type\":\"boolean\",\"default\":false},{\"name\":\"batchGetRouterCacheEnabled\",\"type\":\"boolean\",\"default\":false},{\"name\":\"batchGetLimit\",\"type\":\"int\",\"doc\":\"The max key number allowed in batch get request, and Venice will use cluster-level config if the limit (not positive) is not valid\",\"default\":-1},{\"name\":\"numVersionsToPreserve\",\"type\":\"int\",\"doc\":\"The max number of versions the store should preserve. Venice will use cluster-level config if the number is 0 here.\",\"default\":0},{\"name\":\"incrementalPushEnabled\",\"type\":\"boolean\",\"doc\":\"a flag to see if the store supports incremental push or not\",\"default\":false},{\"name\":\"isMigrating\",\"type\":\"boolean\",\"doc\":\"Whether or not the store is in the process of migration\",\"default\":false},{\"name\":\"writeComputationEnabled\",\"type\":\"boolean\",\"doc\":\"Whether write-path computation feature is enabled for this store\",\"default\":false},{\"name\":\"readComputationEnabled\",\"type\":\"boolean\",\"doc\":\"Whether read-path computation feature is enabled for this store\",\"default\":false},{\"name\":\"bootstrapToOnlineTimeoutInHours\",\"type\":\"int\",\"doc\":\"Maximum number of hours allowed for the store to transition from bootstrap to online state\",\"default\":24},{\"name\":\"leaderFollowerModelEnabled\",\"type\":\"boolean\",\"doc\":\"Whether or not to use leader follower state transition model for upcoming version\",\"default\":false},{\"name\":\"backupStrategy\",\"type\":\"int\",\"doc\":\"Strategies to store backup versions.\",\"default\":0},{\"name\":\"clientDecompressionEnabled\",\"type\":\"boolean\",\"default\":true},{\"name\":\"schemaAutoRegisterFromPushJobEnabled\",\"type\":\"boolean\",\"default\":false},{\"name\":\"superSetSchemaAutoGenerationForReadComputeEnabled\",\"type\":\"boolean\",\"default\":false},{\"name\":\"hybridStoreOverheadBypass\",\"type\":\"boolean\",\"default\":false}, {\"name\":\"hybridStoreDiskQuotaEnabled\",\"type\":\"boolean\",\"default\":false}]},{\"type\":\"record\",\"name\":\"DeleteStore\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"largestUsedVersionNumber\",\"type\":\"int\"}]},{\"type\":\"record\",\"name\":\"DeleteOldVersion\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"versionNum\",\"type\":\"int\"}]},{\"type\":\"record\",\"name\":\"MigrateStore\",\"fields\":[{\"name\":\"srcClusterName\",\"type\":\"string\"},{\"name\":\"destClusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"AbortMigration\",\"fields\":[{\"name\":\"srcClusterName\",\"type\":\"string\"},{\"name\":\"destClusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"}]},{\"type\":\"record\",\"name\":\"AddVersion\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"pushJobId\",\"type\":\"string\"},{\"name\":\"versionNum\",\"type\":\"int\"},{\"name\":\"numberOfPartitions\",\"type\":\"int\"}]},{\"type\":\"record\",\"name\":\"DerivedSchemaCreation\",\"fields\":[{\"name\":\"clusterName\",\"type\":\"string\"},{\"name\":\"storeName\",\"type\":\"string\"},{\"name\":\"schema\",\"type\":\"SchemaMeta\"},{\"name\":\"valueSchemaId\",\"type\":\"int\"},{\"name\":\"derivedSchemaId\",\"type\":\"int\"}]}],\"doc\":\"This contains the main payload of the admin operation\"}]}");
  /** 0 => StoreCreation, 1 => ValueSchemaCreation, 2 => PauseStore, 3 => ResumeStore, 4 => KillOfflinePushJob, 5 => DisableStoreRead, 6 => EnableStoreRead, 7=> DeleteAllVersions, 8=> SetStoreOwner, 9=> SetStorePartitionCount, 10=> SetStoreCurrentVersion, 11=> UpdateStore, 12=> DeleteStore, 13=> DeleteOldVersion, 14=> MigrateStore, 15=> AbortMigration, 16=>AddVersion, 17=> DerivedSchemaCreation */
  public int operationType;
  /** ID of a command execution which is used to query the status of this command. */
  public long executionId;
  /** This contains the main payload of the admin operation */
  public java.lang.Object payloadUnion;
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return operationType;
    case 1: return executionId;
    case 2: return payloadUnion;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: operationType = (java.lang.Integer)value$; break;
    case 1: executionId = (java.lang.Long)value$; break;
    case 2: payloadUnion = (java.lang.Object)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
}
