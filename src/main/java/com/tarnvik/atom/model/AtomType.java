package com.tarnvik.atom.model;

import com.tarnvik.atom.model.atom.FREEAtom;
import com.tarnvik.atom.model.atom.FTYPAtom;
import com.tarnvik.atom.model.atom.SubAtoms;
import com.tarnvik.atom.model.atom.UnknownAtom;
import lombok.Getter;

import java.nio.ByteBuffer;

public enum AtomType {
  // @formatter:off
  FREE(   "free", FREEAtom::new,    "Unallocated space"),
  FTYP(   "ftyp", FTYPAtom::new,    "File Type Compatibility"),
  MDIA(   "mdia", SubAtoms::new,    "Media header"),
  MOOV(   "moov", SubAtoms::new,    "Movie resource metadata"),
  TRAK(   "trak", SubAtoms::new,    "Trak"),
  UNKNOWN("UNKN", UnknownAtom::new, "Unknown - Not yet identified");
  // @formatter:on

  @Getter
  private final String hrName;
  @Getter
  private final String type;
  private final AtomGenerator<Long, ByteBuffer, Atom> atomProvider;

  AtomType(String type, AtomGenerator<Long, ByteBuffer, Atom> atomProvider, String hrName) {
    this.hrName = hrName;
    this.type = type;
    this.atomProvider = atomProvider;
  }

  public Atom generateAtomInstance(long position, ByteBuffer sizeAndType) {
    return atomProvider.apply(position, sizeAndType, this);
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
