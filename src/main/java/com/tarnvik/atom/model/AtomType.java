package com.tarnvik.atom.model;

import lombok.Getter;

public enum AtomType {
  // @formatter:off
  FTYP(   "ftyp", FTYPAtom::new,    "File Type Compatibility"),
  UNKNOWN("UNKN", UnknownAtom::new, "Unknown - Not yet identified");
  // @formatter:on

  @Getter
  private final String hrName;
  @Getter
  private final String type;
  private final AtomGenerator<Integer, Long, Atom> atomProvider;

  AtomType(String type, AtomGenerator<Integer, Long, Atom> atomProvider, String hrName) {
    this.hrName = hrName;
    this.type = type;
    this.atomProvider = atomProvider;
  }

  public Atom generateAtomInstance(int size, long position) {
    return atomProvider.apply(size, position, this);
  }

  public static AtomType from(String type) {
    for (AtomType at : values()) {
      if (at.getType().equals(type)) {
        return at;
      }
    }
    return UNKNOWN;
  }
}
