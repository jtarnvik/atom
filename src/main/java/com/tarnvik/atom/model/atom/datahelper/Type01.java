package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;

import static com.tarnvik.atom.model.converter.TypeConverter.convertUTF8ToString;

public class Type01 extends PayloadParserBase {
  public Type01(DATAAtom.Parsed parsed) {
    super(parsed);
  }

  public static byte[] createPayload(String text) {
    throw new IllegalStateException("Not implemented");
  }

  @Override
  public boolean supportsStringRead() {
    return true;
  }

  @Override
  public String toString() {
//    return new String(parsed.getPayload(), StandardCharsets.UTF_8);
    return convertUTF8ToString(parsed.getPayload());
  }
}
