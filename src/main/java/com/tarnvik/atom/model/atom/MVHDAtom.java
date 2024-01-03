package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
public class MVHDAtom extends Atom {
  private int version;
  private byte[] flags;
  private long creationTime;
  private long modificationTime;
  private long timescale;
  private long duration;
  private long preferredRate;
  private int preferredVolume;
  private short reserved1;
  private int reserved2;
  private long matrixStructure;
  private long preDefined;
  private long nextTrackId;

  public MVHDAtom(long position, ByteBuffer sizeAndType, AtomType atomType) {
    super(position, sizeAndType, atomType);
  }

  private void parserVersion0() {
    creationTime = Integer.toUnsignedLong(data.getInt());
    modificationTime = Integer.toUnsignedLong(data.getInt());
    timescale = Integer.toUnsignedLong(data.getInt());
    duration = Integer.toUnsignedLong(data.getInt());
    preferredRate = Integer.toUnsignedLong(data.getInt());
    preferredVolume = Short.toUnsignedInt(data.getShort());
    reserved1 = data.getShort();
    reserved2 = data.getInt();
    matrixStructure = Integer.toUnsignedLong(data.getInt());
    preDefined = Integer.toUnsignedLong(data.getInt());
    nextTrackId = Integer.toUnsignedLong(data.getInt());
  }

  private void parserVersion1() {
    creationTime = data.getLong();
    modificationTime = data.getLong();
    timescale = Integer.toUnsignedLong(data.getInt());
    duration = data.getLong();
    preferredRate = Integer.toUnsignedLong(data.getInt());
    preferredVolume = Short.toUnsignedInt(data.getShort());
    reserved1 = data.getShort();
    reserved2 = data.getInt();
    matrixStructure = Integer.toUnsignedLong(data.getInt());
    preDefined = Integer.toUnsignedLong(data.getInt());
    nextTrackId = Integer.toUnsignedLong(data.getInt());
  }

  @Override
  public void parseData() {
    data.rewind();
    VersionFlag versionFlag = parseVerionAndFlags(data);
    version = versionFlag.getVersion();
    flags = versionFlag.getFlags();
    if (version == 0) {
      parserVersion0();
    } else if (version == 1) {
      parserVersion1();
    } else {
      throw new IllegalArgumentException("Unknown mvhd version (0 and 1 supported) found: " + version);
    }
  }

  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Version: ");
    str.append(version);
    LocalDateTime ldt = secondsSince1904ToLDT(creationTime);
    str.append(" Creation time: ");
    str.append(ldt.format(LDT_FORMAT));
    if (creationTime != modificationTime) {
      ldt = secondsSince1904ToLDT(modificationTime);
      str.append(" Modification time: ");
      str.append(ldt.format(LDT_FORMAT));
    }
    if (timescale != 0) {
      str.append(" Timescale: ");
      str.append(timescale);
      double actualLength = (double) duration / (double) timescale;
      str.append(" (Length: ");
      str.append(formatWith2Decimals(actualLength));
      str.append("s) ");
    }
    str.append(" PreferredRate: ");
    str.append(formatWith2Decimals(convert16x16FixedPoint(preferredRate)));
    str.append(" PreferredVolume: ");
    str.append(formatWith2Decimals(convert8x8FixedPoint(preferredVolume)));
    str.append(" NextTrackId: ");
    str.append(nextTrackId);
    return str.toString();
  }
}
