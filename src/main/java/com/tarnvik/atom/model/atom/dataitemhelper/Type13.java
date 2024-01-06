package com.tarnvik.atom.model.atom.dataitemhelper;

import com.tarnvik.atom.model.atom.DATAAtom;

public class Type13 extends TypeBase {
  protected Type13(DATAAtom.Parsed parsed) {
    super(parsed);
  }

  @Override
  public String generate() {
    return "A JPEG image";
  }
}
