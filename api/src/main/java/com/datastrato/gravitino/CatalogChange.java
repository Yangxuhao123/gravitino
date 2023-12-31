/*
 * Copyright 2023 Datastrato Pvt Ltd.
 * This software is licensed under the Apache License version 2.
 */
package com.datastrato.gravitino;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A catalog change is a change to a catalog. It can be used to rename a catalog, update the comment
 * of a catalog, set a property and value pair for a catalog, or remove a property from a catalog.
 */
public interface CatalogChange {

  /**
   * Creates a new catalog change to rename the catalog.
   *
   * @param newName The new name of the catalog.
   * @return The catalog change.
   */
  static CatalogChange rename(String newName) {
    return new RenameCatalog(newName);
  }

  /**
   * Creates a new catalog change to update the catalog comment.
   *
   * @param newComment The new comment for the catalog.
   * @return The catalog change.
   */
  static CatalogChange updateComment(String newComment) {
    return new UpdateCatalogComment(newComment);
  }

  /**
   * Creates a new catalog change to set the property and value for the catalog.
   *
   * @param property The property name to set.
   * @param value The value to set the property to.
   * @return The catalog change.
   */
  static CatalogChange setProperty(String property, String value) {
    return new SetProperty(property, value);
  }

  /**
   * Creates a new catalog change to remove a property from the catalog.
   *
   * @param property The property name to remove.
   * @return The catalog change.
   */
  static CatalogChange removeProperty(String property) {
    return new RemoveProperty(property);
  }

  /** A catalog change to rename the catalog. */
  @Getter
  @EqualsAndHashCode
  final class RenameCatalog implements CatalogChange {
    private final String newName;

    private RenameCatalog(String newName) {
      this.newName = newName;
    }
  }

  /** A catalog change to update the catalog comment. */
  @Getter
  @EqualsAndHashCode
  final class UpdateCatalogComment implements CatalogChange {
    private final String newComment;

    private UpdateCatalogComment(String newComment) {
      this.newComment = newComment;
    }
  }

  /** A catalog change to set the property and value for the catalog. */
  @Getter
  @EqualsAndHashCode
  final class SetProperty implements CatalogChange {
    private final String property;
    private final String value;

    private SetProperty(String property, String value) {
      this.property = property;
      this.value = value;
    }
  }

  /** A catalog change to remove a property from the catalog. */
  @Getter
  @EqualsAndHashCode
  final class RemoveProperty implements CatalogChange {
    private final String property;

    private RemoveProperty(String property) {
      this.property = property;
    }
  }
}
