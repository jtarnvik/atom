package com.tarnvik.atom.model;

import lombok.Getter;

public enum AtomType {
  FTYP("ftyp"),
  UNKNWON("UNKN");

  @Getter
  private final String type;

  AtomType(String type) {
    this.type = type;
  }

  public static AtomType from(String type) {
    for (AtomType at : values()) {
      if (at.getType().equals(type)) {
        return at;
      }
    }
    return UNKNWON;
  }
}
