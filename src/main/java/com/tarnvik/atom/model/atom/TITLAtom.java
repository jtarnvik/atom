package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.ParsedAtom;
import com.tarnvik.atom.model.atom.parts.PackedLanguage;
import com.tarnvik.atom.model.atom.parts.VersionFlag;
import com.tarnvik.atom.model.converter.ISO639;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.tarnvik.atom.model.atom.parts.CommonAtomParts.parsePackedLanguage;
import static com.tarnvik.atom.model.atom.parts.CommonAtomParts.parseVerionAndFlags;
import static com.tarnvik.atom.model.converter.TypeConverter.bytesToHexString;
import static com.tarnvik.atom.model.converter.TypeConverter.convertUTF8ToString;
import static com.tarnvik.atom.model.converter.TypeConverter.shortToHexString;

@EqualsAndHashCode(callSuper = true)
@Getter
public class TITLAtom extends Atom {
  @Data
  public static class Parsed implements ParsedAtom {
    private int version;
    private byte[] flags;
    private short packedLanguage;
    private String unpackedLangage;
    private String title;
  }

  public TITLAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public ParsedAtom parseData() {
    data.rewind();
    Parsed result = new Parsed();
    VersionFlag versionFlag = parseVerionAndFlags(data);
    result.version = versionFlag.getVersion();
    result.flags = versionFlag.getFlags();
    PackedLanguage pack = parsePackedLanguage(data);
    result.packedLanguage = pack.getPackedLanguage();
    result.unpackedLangage = pack.getUnpackedLangage();
    byte[] chTmp = new byte[data.remaining()];
    data.get(chTmp);
    if (chTmp.length > 0 && chTmp[chTmp.length - 1] == 0) {
      chTmp = Arrays.copyOfRange(chTmp, 0, chTmp.length - 1);
    } else {
      // TODO: Add log noting that the format is not correct. string not terminated by zero
    }
    result.title = convertUTF8ToString(chTmp);
    return result;
  }

  @Override
  protected String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Version: ");
    str.append(parsed.version);
    str.append(" Flags: [");
    str.append(bytesToHexString(parsed.flags));
    str.append("] LanguageCode: ");
    str.append(shortToHexString(parsed.packedLanguage));
    str.append(" Unpacked LanguageCode: ");
    str.append(parsed.unpackedLangage);
    str.append(" (");
    str.append(ISO639.fromISO639_2(parsed.unpackedLangage).getDisplayName());
    str.append(") ");
    str.append(" Title: ");
    str.append(parsed.title);
    return str.toString();
  }
}
