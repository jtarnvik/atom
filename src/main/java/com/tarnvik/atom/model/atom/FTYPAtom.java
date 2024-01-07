package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.parsedatom.ParsedAtom;
import com.tarnvik.atom.model.converter.TypeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FTYPAtom extends Atom {
  @Data
  public static class Parsed implements ParsedAtom {
    // 4 bytes, for major brand, to be interpreted as asci chars
    private String majorBrand;
    // 4 bytes, for quicktime, century, month, date, ZERO";
    private byte[] minorVersion;
    // "Array of 4 byte blocks with compatible brands. One should be 'qt  ' to be able to use spec.";
    private List<String> compatibleBrands;
  }

  public FTYPAtom(long position, ByteBuffer sizeAndType, AtomType at, Atom parent) {
    super(position, sizeAndType, at, parent);
  }

  @Override
  public ParsedAtom parseData() {
    data.rewind();
    Parsed result = new Parsed();
    byte[] chTmp = new byte[4];
    data.get(chTmp);
    result.majorBrand = new String(chTmp, StandardCharsets.UTF_8);
    result.minorVersion = new byte[4];
    for (int i = 0; i < 4; ++i) {
      result.minorVersion[i] = data.get();
    }
    result.compatibleBrands = new ArrayList<>();
    while (data.hasRemaining()) {
      data.get(chTmp);
      if (ByteBuffer.wrap(chTmp).getInt() != 0) {
        result.compatibleBrands.add(new String(chTmp, StandardCharsets.UTF_8));
      }
    }
    return result;
  }

  @Override
  public String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: MajorBrand: ");
    str.append(parsed.majorBrand);
    str.append(" MinorVersion: [");
    str.append(TypeConverter.bytesToHexString(parsed.minorVersion));
    str.append("] CompatibleBrands: ");
    str.append(String.join(", ", parsed.compatibleBrands));
    return str.toString();
  }
}
