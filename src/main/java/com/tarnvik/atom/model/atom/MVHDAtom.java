package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.ParsedAtom;
import com.tarnvik.atom.model.atom.parts.CommonAtomParts;
import com.tarnvik.atom.model.atom.parts.VersionFlag;
import com.tarnvik.atom.model.converter.TimeConverter;
import com.tarnvik.atom.model.converter.TypeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
public class MVHDAtom extends Atom {
  @Data
  public static class Parsed implements ParsedAtom  {
    private int version;
    private byte[] flags;
    private long creationTime;
    private long modificationTime;
    private long timescale;
    private long duration;
    private long preferredRate;
    private int preferredVolume;
    private byte[] reserved;
    private byte[] matrixStructure;
    private long previewTime;
    private long previewDuration;
    private long posterTime;
    private long selectionTime;
    private long selectionDuration;
    private long currentTime;
    private long nextTrackId;
  }

  public MVHDAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  private void parserVersion0(Parsed result) {
    result.creationTime = Integer.toUnsignedLong(data.getInt());
    result.modificationTime = Integer.toUnsignedLong(data.getInt());
    result.timescale = Integer.toUnsignedLong(data.getInt());
    result.duration = Integer.toUnsignedLong(data.getInt());
    result.preferredRate = Integer.toUnsignedLong(data.getInt());
    result.preferredVolume = Short.toUnsignedInt(data.getShort());
    result.reserved = new byte[10];
    data.get(result.reserved);
    result.matrixStructure = new byte[36];
    data.get(result.matrixStructure);
    result.previewTime = Integer.toUnsignedLong(data.getInt());
    result.previewDuration = Integer.toUnsignedLong(data.getInt());
    result.posterTime = Integer.toUnsignedLong(data.getInt());
    result.selectionTime = Integer.toUnsignedLong(data.getInt());
    result.selectionDuration = Integer.toUnsignedLong(data.getInt());
    result.currentTime = Integer.toUnsignedLong(data.getInt());
    result.nextTrackId = Integer.toUnsignedLong(data.getInt());
    /*
Bytes   Offset  Description
-------------------------------------
4       0       Version and flags (version = 0)
4       4       Creation time (seconds since 1904-01-01 00:00:00 UTC)
4       8       Modification time (seconds since 1904-01-01 00:00:00 UTC)
4       12      Time scale (number of time units per second)
4       16      Duration (duration of the movie in time scale units)
4       20      Preferred rate (typically 1.0)
2       24      Preferred volume (typically 1.0 for full volume)
10      26      Reserved
36      36      Matrix structure (a 3x3 matrix for transformation)
4       72      Preview time (time of the preview in movie timescale)
4       76      Preview duration (duration of the preview in movie timescale)
4       80      Poster time (time of the poster in movie timescale)
4       84      Selection time (time of the selection in movie timescale)
4       88      Selection duration (duration of the selection in movie timescale)
4       92      Current time (time of the current time in movie timescale)
4       96      Next track ID (unique identifier for the next track added to the movie)
     */
  }

  private void parserVersion1(Parsed result) {
    result.creationTime = data.getLong();
    result.modificationTime = data.getLong();
    result.timescale = Integer.toUnsignedLong(data.getInt());
    result.duration = data.getLong();
    result.preferredRate = Integer.toUnsignedLong(data.getInt());
    result.preferredVolume = Short.toUnsignedInt(data.getShort());
    result.reserved = new byte[10];
    data.get(result.reserved);
    result.matrixStructure = new byte[36];
    data.get(result.matrixStructure);
    result.previewTime = Integer.toUnsignedLong(data.getInt());
    result.previewDuration = Integer.toUnsignedLong(data.getInt());
    result.posterTime = Integer.toUnsignedLong(data.getInt());
    result.selectionTime = Integer.toUnsignedLong(data.getInt());
    result.selectionDuration = Integer.toUnsignedLong(data.getInt());
    result.currentTime = Integer.toUnsignedLong(data.getInt());
    result.nextTrackId = Integer.toUnsignedLong(data.getInt());
  /* 12 bytes longer, 3 items are 8 instead of 4 bytes */
  }

  @Override
  public ParsedAtom parseData() {
    data.rewind();
    Parsed result = new Parsed();
    VersionFlag versionFlag = CommonAtomParts.parseVerionAndFlags(data);
    result.version = versionFlag.getVersion();
    result.flags = versionFlag.getFlags();
    if (result.version == 0) {
      parserVersion0(result);
    } else if (result.version == 1) {
      parserVersion1(result);
    } else {
      throw new IllegalArgumentException("Unknown mvhd version (0 and 1 supported) found: " + result.version);
    }
    return result;
  }

  @Override
  protected String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();

    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Version: ");
    str.append(parsed.version);
    LocalDateTime ldt = TimeConverter.secondsSince1904ToLDT(parsed.creationTime);
    str.append(" Creation time: ");
    str.append(ldt.format(TimeConverter.LDT_FORMAT));
    if (parsed.creationTime != parsed.modificationTime) {
      ldt = TimeConverter.secondsSince1904ToLDT(parsed.modificationTime);
      str.append(" Modification time: ");
      str.append(ldt.format(TimeConverter.LDT_FORMAT));
    }
    if (parsed.timescale != 0) {
      str.append(" Timescale: ");
      str.append(parsed.timescale);
      double actualLength = (double) parsed.duration / (double) parsed.timescale;
      str.append(" (Length: ");
      str.append(TypeConverter.formatWith2Decimals(actualLength));
      str.append("s) ");
    }
    str.append(" PreferredRate: ");
    str.append(TypeConverter.formatWith2Decimals(TypeConverter.convert16x16FixedPoint(parsed.preferredRate)));
    str.append(" PreferredVolume: ");
    str.append(TypeConverter.formatWith2Decimals(TypeConverter.convert8x8FixedPoint(parsed.preferredVolume)));
    str.append(" NextTrackId: ");
    str.append(parsed.nextTrackId);
    return str.toString();
  }
}
