package com.tarnvik.atom.model.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeConverter {
  public static String byteToHexString(byte b) {
    return String.format("0x%02X", b & 0xFF);
  }
  public static String shortToHexString(short b) {
    return String.format("0x%04X", b & 0xFFFF);
  }

  public static String bytesToHexString(byte[] bts) {
    List<String> result = new ArrayList<>();
    for (byte bt : bts) {
      result.add(byteToHexString(bt));
    }
    return String.join(", ", result);
  }

  public static String formatWith2Decimals(double seconds) {
    return String.format(Locale.US, "%.2f", seconds);
  }

  public static double convert8x8FixedPoint(int preferredVolume) {
    int wholePart = (preferredVolume >> 8) & 0xFF;
    int fractionalPart = preferredVolume & 0xFF;

    return wholePart + (fractionalPart / 256.0);
  }

  public static double convert16x16FixedPoint(long preferredRate) {
    int wholePart = (int) ((preferredRate >> 16) & 0xFFFF);
    int fractionalPart = (int) (preferredRate & 0xFFFF);

    return wholePart + (fractionalPart / 65536.0);
  }

  public static String convertUTF8ToString(byte[] convs) {
    StringBuilder result = new StringBuilder();
    for (byte b : convs) {
      int unsignedByte = b & 0xFF;
      result.append((char) unsignedByte);
    }
    return result.toString();
  }

  // Used by data atom for type 21
  public static long convertBytesToSignedLong(byte[] bytes) {
    if (bytes == null || bytes.length < 1 || bytes.length > 4) {
      throw new IllegalArgumentException("Invalid byte array length");
    }

    long result = 0;
    for (int i = 0; i < bytes.length; i++) {
      long byteValue = bytes[i] & 0xFF; // Convert to unsigned
      byteValue <<= (bytes.length - 1 - i) * 8;
      result |= byteValue;
    }
    return result;
  }

  public static byte[] removeTerminatingZeroByte(byte [] chTmp) {
    if (chTmp.length > 0 && chTmp[chTmp.length - 1] == 0) {
      chTmp = Arrays.copyOfRange(chTmp, 0, chTmp.length - 1);
    } else {
      // TODO: Add log noting that the format is not correct. string not terminated by zero
    }
    return chTmp;
  }

}
