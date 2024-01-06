package com.tarnvik.atom.model.atom.dataitemhelper;

import com.tarnvik.atom.model.atom.DATAAtom;

public abstract class TypeBase implements DataAtomStringGenerator{
  protected final DATAAtom.Parsed parsed;

  protected TypeBase(DATAAtom.Parsed parsed) {
    this.parsed = parsed;
  }

}
