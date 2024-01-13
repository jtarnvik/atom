package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;

import static com.tarnvik.atom.model.converter.TypeConverter.convertBytesToSignedLong;

public class Type21 extends PayloadParserBase {
  public Type21(DATAAtom.Parsed parsed) {
    super(parsed);
  }

  @Override
  public String toString() {
    return Long.toString(convertBytesToSignedLong(parsed.getPayload()));
  }

  @Override
  public boolean supportsLong() {
    return true;
  }

  @Override
  public long toLong() {
    return convertBytesToSignedLong(parsed.getPayload());
  }
}
