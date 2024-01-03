package com.tarnvik.atom.model;

import com.tarnvik.atom.parser.AtomDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Data
public abstract class Atom {
  public static final int TO_STRING_EXTRA_INDENT = 4;
  protected static final LocalDateTime EPOCH_1904 = LocalDateTime.of(1904, 1, 1, 0, 0, 0);
  protected static final DateTimeFormatter LDT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  protected final int size;
  protected final long position;
  protected final AtomType atomType;
  protected final ByteBuffer sizeAndType;

  protected ByteBuffer data;
  protected Atom parent;

  public Atom(long position, ByteBuffer sizeAndType, AtomType atomType) {
    sizeAndType.rewind();
    this.size = sizeAndType.asIntBuffer().get(0);
    this.position = position;
    this.atomType = atomType;
    this.sizeAndType = sizeAndType;
  }

  public boolean isDataLoaded() {
    return data != null;
  }

  public boolean hasParent() {
    return parent != null;
  }

  protected long getDataStartPosition() {
    return position + sizeAndType.capacity();
  }

  protected long size() {
    if (data == null) {
      throw new IllegalStateException("Data not yet parsed.");
    }
    return sizeAndType.capacity() + data.capacity();
  }

  public abstract void parseData() throws IOException;

  public void loadData(AtomDataSource ads) throws IOException {
    // TODO: Skip reading the data for mdat atoms, use seek instead.
    if (size == 0) {
      throw new IllegalStateException("Size 0. Special handling not implemented.");
    } else if (size == 1) {
      throw new IllegalStateException("Size 1. Special handling not implemented.");
    } else {
      data = ads.extract(size - 8)
        .orElseThrow(() -> new IllegalStateException("Unable to read specified data size"));
    }
  }

  protected abstract String toStringChild(int indentLevel);

  public String toString(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Type: ");
    str.append(atomType.getType());
    if (atomType == AtomType.UNKNOWN) {
      sizeAndType.position(4);
      str.append("/").append(StandardCharsets.UTF_8.decode(sizeAndType)).append("/");
    }
    str.append(" (");
    str.append(atomType.getHrName());
    str.append("), Size: ");
    str.append(size);
    str.append(", Pos: ");
    str.append(position);
    str.append("\n");
    str.append(toStringChild(indentLevel + TO_STRING_EXTRA_INDENT));
    return str.toString();
  }

  public static String byteToHexString(byte b) {
    return String.format("0x%02X", b & 0xFF);
  }

  public static String bytesToHexString(byte[] bts) {
    List<String> result = new ArrayList<>();
    for (int i = 0; i < bts.length; ++i) {
      result.add(byteToHexString(bts[i]));
    }
    return String.join(", ", result);
  }

  public static LocalDateTime secondsSince1904ToLDT(long secs) {
    return EPOCH_1904.plusSeconds(secs);
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

  // TODO: convert to record and external class
  @Data
  public static class VersionFlag {
    private int version;
    private byte[] flags = new byte[3];
  }

  protected static VersionFlag parseVerionAndFlags(ByteBuffer data) {
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
