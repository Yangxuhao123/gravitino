/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino.proto;

import com.datastrato.gravitino.EntitySerDe;
import com.datastrato.gravitino.EntitySerDeFactory;
import com.datastrato.gravitino.meta.SchemaVersion;
import com.google.common.collect.ImmutableMap;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEntityProtoSerDe {

  private final EntitySerDe entitySerDe = EntitySerDeFactory.createEntitySerDe("proto");

  @Test
  public void testAuditInfoSerDe() throws IOException {
    Instant now = Instant.now();
    String creator = "creator";
    String modifier = "modifier";

    com.datastrato.gravitino.meta.AuditInfo auditInfo =
        new com.datastrato.gravitino.meta.AuditInfo.Builder()
            .withCreator(creator)
            .withCreateTime(now)
            .withLastModifier(modifier)
            .withLastModifiedTime(now)
            .build();

    ProtoEntitySerDe protoEntitySerDe = (ProtoEntitySerDe) entitySerDe;

    byte[] bytes = protoEntitySerDe.serialize(auditInfo);
    com.datastrato.gravitino.meta.AuditInfo auditInfoFromBytes =
        protoEntitySerDe.deserialize(bytes, com.datastrato.gravitino.meta.AuditInfo.class);
    Assertions.assertEquals(auditInfo, auditInfoFromBytes);

    // Test with optional fields
    com.datastrato.gravitino.meta.AuditInfo auditInfo1 =
        new com.datastrato.gravitino.meta.AuditInfo.Builder()
            .withCreator(creator)
            .withCreateTime(now)
            .build();

    // Test from/to bytes
    bytes = protoEntitySerDe.serialize(auditInfo1);
    auditInfoFromBytes =
        protoEntitySerDe.deserialize(bytes, com.datastrato.gravitino.meta.AuditInfo.class);
    Assertions.assertEquals(auditInfo1, auditInfoFromBytes);

    // Test with empty field
    com.datastrato.gravitino.meta.AuditInfo auditInfo2 =
        new com.datastrato.gravitino.meta.AuditInfo.Builder().build();

    byte[] bytes1 = protoEntitySerDe.serialize(auditInfo2);
    com.datastrato.gravitino.meta.AuditInfo auditInfoFromBytes1 =
        protoEntitySerDe.deserialize(bytes1, com.datastrato.gravitino.meta.AuditInfo.class);
    Assertions.assertEquals(auditInfo2, auditInfoFromBytes1);
  }

  @Test
  public void testEntitiesSerDe() throws IOException {
    Instant now = Instant.now();
    String creator = "creator";
    SchemaVersion version = SchemaVersion.V_0_1;
    Long metalakeId = 1L;
    String metalakeName = "metalake";
    Map<String, String> props = ImmutableMap.of("k1", "v1", "k2", "v2");

    com.datastrato.gravitino.meta.AuditInfo auditInfo =
        new com.datastrato.gravitino.meta.AuditInfo.Builder()
            .withCreator(creator)
            .withCreateTime(now)
            .build();

    // Test Metalake
    com.datastrato.gravitino.meta.BaseMetalake metalake =
        new com.datastrato.gravitino.meta.BaseMetalake.Builder()
            .withId(metalakeId)
            .withName(metalakeName)
            .withProperties(props)
            .withAuditInfo(auditInfo)
            .withVersion(version)
            .build();

    ProtoEntitySerDe protoEntitySerDe = (ProtoEntitySerDe) entitySerDe;

    byte[] metalakeBytes = protoEntitySerDe.serialize(metalake);
    com.datastrato.gravitino.meta.BaseMetalake metalakeFromBytes =
        protoEntitySerDe.deserialize(
            metalakeBytes, com.datastrato.gravitino.meta.BaseMetalake.class);
    Assertions.assertEquals(metalake, metalakeFromBytes);

    // Test metalake without props map
    com.datastrato.gravitino.meta.BaseMetalake metalake1 =
        new com.datastrato.gravitino.meta.BaseMetalake.Builder()
            .withId(metalakeId)
            .withName(metalakeName)
            .withAuditInfo(auditInfo)
            .withVersion(version)
            .build();

    byte[] metalakeBytes1 = protoEntitySerDe.serialize(metalake1);
    com.datastrato.gravitino.meta.BaseMetalake metalakeFromBytes1 =
        protoEntitySerDe.deserialize(
            metalakeBytes1, com.datastrato.gravitino.meta.BaseMetalake.class);
    Assertions.assertEquals(metalake1, metalakeFromBytes1);

    // Test CatalogEntity
    Long catalogId = 1L;
    String catalogName = "catalog";
    String comment = "comment";
    String provider = "test";

    com.datastrato.gravitino.meta.CatalogEntity catalogEntity =
        new com.datastrato.gravitino.meta.CatalogEntity.Builder()
            .withId(catalogId)
            .withName(catalogName)
            .withComment(comment)
            .withType(com.datastrato.gravitino.Catalog.Type.RELATIONAL)
            .withProvider(provider)
            .withAuditInfo(auditInfo)
            .build();

    byte[] catalogBytes = protoEntitySerDe.serialize(catalogEntity);
    com.datastrato.gravitino.meta.CatalogEntity catalogEntityFromBytes =
        protoEntitySerDe.deserialize(
            catalogBytes, com.datastrato.gravitino.meta.CatalogEntity.class);
    Assertions.assertEquals(catalogEntity, catalogEntityFromBytes);

    // Test SchemaEntity
    Long schemaId = 1L;
    String schemaName = "schema";
    com.datastrato.gravitino.meta.SchemaEntity schemaEntity =
        new com.datastrato.gravitino.meta.SchemaEntity.Builder()
            .withId(schemaId)
            .withName(schemaName)
            .withAuditInfo(auditInfo)
            .build();

    byte[] schemaBytes = protoEntitySerDe.serialize(schemaEntity);
    com.datastrato.gravitino.meta.SchemaEntity schemaEntityFromBytes =
        protoEntitySerDe.deserialize(schemaBytes, com.datastrato.gravitino.meta.SchemaEntity.class);
    Assertions.assertEquals(schemaEntity, schemaEntityFromBytes);

    // Test TableEntity
    Long tableId = 1L;
    String tableName = "table";
    com.datastrato.gravitino.meta.TableEntity tableEntity =
        new com.datastrato.gravitino.meta.TableEntity.Builder()
            .withId(tableId)
            .withName(tableName)
            .withAuditInfo(auditInfo)
            .build();

    byte[] tableBytes = protoEntitySerDe.serialize(tableEntity);
    com.datastrato.gravitino.meta.TableEntity tableEntityFromBytes =
        protoEntitySerDe.deserialize(tableBytes, com.datastrato.gravitino.meta.TableEntity.class);
    Assertions.assertEquals(tableEntity, tableEntityFromBytes);
  }
}
