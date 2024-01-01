package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FTYPAtom extends Atom {
  // 4 bytes, for major brand, to be interpreted as asci chars
  private String majorBrand;
  // 4 bytes, for quicktime, century, month, date, ZERO";
  private List<String> minorVersion;
  // "Array of 4 byte blocks with compatible brands. One should be 'qt  ' to be able to use spec.";
  private List<String> compatibleBrands;

  public FTYPAtom(long position, ByteBuffer sizeAndType, AtomType at) {
    super(position, sizeAndType, at);
  }

  @Override
  public void parseData() {
    data.rewind();
    byte[] chTmp = new byte[4];
    data.get(chTmp);
    majorBrand = new String(chTmp, StandardCharsets.UTF_8);
    minorVersion = new ArrayList<>();
    for (int i = 0; i < 4; ++i) {
      minorVersion.add(byteToHexString(data.get()));
    }
    compatibleBrands = new ArrayList<>();
    while (data.hasRemaining()) {
      data.get(chTmp);
      if (ByteBuffer.wrap(chTmp).getInt() != 0) {
        compatibleBrands.add(new String(chTmp, StandardCharsets.UTF_8));
      }
    }
  }

  @Override
  public String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: MajorBrand: ");
    str.append(majorBrand);
    str.append(" MinorVersion: [");
    str.append(String.join(", ", minorVersion));
    str.append("] CompatibleBrands: ");
    str.append(String.join(", ", compatibleBrands));
    return str.toString();
  }
}
