package com.tarnvik.atom.model.atom.parts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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

  public static PackedLanguage parsePackedLanguage(ByteBuffer data) {
    // Result is langage code from https://www.loc.gov/standards/iso639-2/php/code_list.php
    // ISO 639-2
    PackedLanguage result = new PackedLanguage();

    short packedLanguage = data.getShort();
    result.setPackedLanguage(packedLanguage);
    // Assuming high bit is padding (format: 0x1XXX XXXX XXXX XXXX)
    int maskedValue = packedLanguage & 0x7FFF; // Mask the high bit

    // Extract three groups of five bits
    int group1 = (maskedValue >> 10) & 0x1F;
    int group2 = (maskedValue >> 5) & 0x1F;
    int group3 = maskedValue & 0x1F;

    // Add 0x60 to each group
    byte char1 = (byte) (group1 + 0x60);
    byte char2 = (byte) (group2 + 0x60);
    byte char3 = (byte) (group3 + 0x60);

    result.setUnpackedLangage(new String(new byte[]{char1, char2, char3}, StandardCharsets.UTF_8));
    return result;
  }
}
