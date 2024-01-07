package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;

import static com.tarnvik.atom.model.converter.TypeConverter.convertUTF8ToString;

public class Type01 extends TypeBase {
  protected Type01(DATAAtom.Parsed parsed) {
    super(parsed);
  }

  @Override
  public String toString() {
//    return new String(parsed.getPayload(), StandardCharsets.UTF_8);
    return convertUTF8ToString(parsed.getPayload());
  }
}
