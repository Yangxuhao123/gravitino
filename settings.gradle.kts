/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version("0.7.0")
}

rootProject.name = "gravitino"

include("api", "common", "core", "meta", "server", "integration-test", "server-common")
include("catalogs:catalog-hive", "catalogs:catalog-lakehouse-iceberg", "catalogs:catalog-jdbc-common", "catalogs:catalog-jdbc-mysql", "catalogs:catalog-jdbc-postgresql")
include("clients:client-java", "clients:client-java-runtime")
include("trino-connector")
include("web")
include("docs")
