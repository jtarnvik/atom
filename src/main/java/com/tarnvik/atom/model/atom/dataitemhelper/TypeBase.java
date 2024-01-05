package com.tarnvik.atom.model.atom.dataitemhelper;

import com.tarnvik.atom.model.atom.DATAAtom;

public abstract class TypeBase implements DataAtomStringGenerator{
  protected final DATAAtom atom;

  protected TypeBase(DATAAtom atom) {
    this.atom = atom;
  }

}
