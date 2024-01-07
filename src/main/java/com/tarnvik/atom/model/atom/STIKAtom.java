package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.parsedatom.ParsedAtom;
import com.tarnvik.atom.model.atom.datahelper.DataAtomTypeGenerator;
import com.tarnvik.atom.model.atom.datahelper.DataAtomTypeIndicator;
import com.tarnvik.atom.model.atom.stikhelper.MediaType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class STIKAtom extends SubAtoms {

  @Data
  public static class Parsed implements ParsedAtom {
    private MediaType type;
  }

  public STIKAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public ParsedAtom parseData()  {
    if (subAtoms.isEmpty()) {
      throw new IllegalStateException("No subatoms, has super.parseSubAtoms been called. If so, this atom has an unexpected format.");
    }
    if (subAtoms.size() > 1) {
      throw new IllegalStateException("More than 1 subatom, this atom has an unexpected format.");
    }
    Atom child = subAtoms.getFirst();
    if (!(child instanceof DATAAtom dta)) {
      throw new IllegalStateException("Child atom not of type DataAtom, this atom has an unexpected format, type: " + child.getClass().toString());
    } else {
      DataAtomTypeIndicator ind = DataAtomTypeIndicator.from(dta.getParsed());
      DataAtomTypeGenerator typeGenerator = ind.getTypeGenerator();
      if (!typeGenerator.supportsLong()) {
        throw new IllegalStateException("DataAtom does not support long, unable to convert to Media Type type :" + ind.getIndicatorType());
      }
      Parsed result = new Parsed();
      result.type = MediaType.from(typeGenerator.toLong());
      return result;
    }
  }
  @Override
  protected String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Media Type: ");
    str.append(parsed.type);
    str.append(" - ");
    str.append(parsed.type.getType());
    str.append("\n");
    str.append(super.toStringChild(indentLevel));
    return str.toString();
  }
}
