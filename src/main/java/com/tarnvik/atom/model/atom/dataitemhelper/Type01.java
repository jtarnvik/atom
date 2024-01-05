package com.tarnvik.atom.model.atom.dataitemhelper;

import com.tarnvik.atom.model.atom.DATAAtom;

import java.nio.charset.StandardCharsets;

public class Type01 extends TypeBase {

  protected Type01(DATAAtom atom) {
    super(atom);
  }

  @Override
  public String generate() {
    return new String(atom.getPayload(), StandardCharsets.UTF_8);
  }
}
