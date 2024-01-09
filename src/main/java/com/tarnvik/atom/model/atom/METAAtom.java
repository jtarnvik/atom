package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.atom.parts.CommonAtomParts;
import com.tarnvik.atom.model.atom.parts.VersionFlag;
import com.tarnvik.atom.model.parsedatom.ParsedAtom;
import com.tarnvik.atom.model.parsedatom.VersionedParsedAtom;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class METAAtom extends SubAtoms {
  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class Parsed extends VersionedParsedAtom {
  }

  public METAAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public ParsedAtom parseData()  {
    data.rewind();
    Parsed result = new Parsed();
    VersionFlag versionFlag = CommonAtomParts.parseVerionAndFlags(data);
    result.setVersion(versionFlag.getVersion());
    result.setFlags(versionFlag.getFlags());
    return result;
  }

  @Override
  public void parseSubAtoms() throws IOException {
    data.position(4);   // Make sure to advance after own data
    parseSubAtoms(4);
  }

  @Override
  protected String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: ");
    str.append(toStringVersioned(parsed));
    str.append("\n");
    str.append(super.toStringChild(indentLevel));
    return str.toString();
  }
}
