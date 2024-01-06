package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.ParsedAtom;
import com.tarnvik.atom.model.atom.parts.CommonAtomParts;
import com.tarnvik.atom.model.atom.parts.VersionFlag;
import com.tarnvik.atom.model.converter.TypeConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class METAAtom extends SubAtoms {
  @Data
  public static class Parsed implements ParsedAtom {
    private int version;
    private byte[] flags;
  }

  public METAAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public ParsedAtom parseData()  {
    data.rewind();
    Parsed result = new Parsed();
    VersionFlag versionFlag = CommonAtomParts.parseVerionAndFlags(data);
    result.version = versionFlag.getVersion();
    result.flags = versionFlag.getFlags();
    return result;
  }

  @Override
  public void parseSubAtoms() throws IOException {
//    data.rewind();
    data.position(4);   // Make sure to advance after own data
    parseSubAtoms(4);
  }

  @Override
  protected String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Version: ");
    str.append(parsed.version);
    str.append(" Flags: [");
    str.append(TypeConverter.bytesToHexString(parsed.flags));
    str.append("]");
    str.append("\n");
    str.append(super.toStringChild(indentLevel));
    return str.toString();
  }
}
