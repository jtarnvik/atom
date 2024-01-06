package com.tarnvik.atom.model.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeConverter {
  public static String byteToHexString(byte b) {
    return String.format("0x%02X", b & 0xFF);
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
}
