package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.converter.TypeConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class METAAtom extends SubAtoms {
  private long version;
  private byte[] flags;

  public METAAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public void parseData() throws IOException {
    data.rewind();
    VersionFlag versionFlag = parseVerionAndFlags(data);
    version = versionFlag.getVersion();
    flags = versionFlag.getFlags();
    parseSubAtoms(4);
  }

  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Version: ");
    str.append(version);
    str.append(" Flags: [");
    str.append(TypeConverter.bytesToHexString(flags));
    str.append("]");
    str.append("\n");
    str.append(super.toStringChild(indentLevel));
    return str.toString();
  }
}
