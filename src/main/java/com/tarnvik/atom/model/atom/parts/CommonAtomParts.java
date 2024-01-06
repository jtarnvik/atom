package com.tarnvik.atom.model.atom.parts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonAtomParts {
  public static VersionFlag parseVerionAndFlags(ByteBuffer data) {
    VersionFlag result = new VersionFlag();
    byte[] chTmp = new byte[4];
    data.get(chTmp);
    result.setVersion(Byte.toUnsignedInt(chTmp[0]));
    result.getFlags()[0] = chTmp[1];
    result.getFlags()[1] = chTmp[2];
    result.getFlags()[2] = chTmp[3];
    return result;
  }
}
