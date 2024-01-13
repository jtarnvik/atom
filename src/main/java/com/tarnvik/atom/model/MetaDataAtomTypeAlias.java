package com.tarnvik.atom.model;

import lombok.Getter;

@Getter
public enum MetaDataAtomTypeAlias {
  TITLE(AtomType.ATNAM);

  private final AtomType atomType;

  MetaDataAtomTypeAlias(AtomType atomType) {
    this.atomType = atomType;
  }
}
