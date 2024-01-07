package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.parsedatom.ParsedAtom;
import com.tarnvik.atom.model.atom.datahelper.DataAtomTypeIndicator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class DATAAtom extends Atom {
  @Data
  public static class Parsed implements ParsedAtom {
    private byte set;
    private int typeIndicator;
    private int countryIndicator;
    private int languageIndicator;
    private byte[] payload;
  }

  public DATAAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  public Parsed getParsed() {
    return (Parsed) parseData();
  }

  @Override
  public ParsedAtom parseData() {
    data.rewind();
    Parsed result = new Parsed();
    result.set = data.slice().get();
    result.typeIndicator = data.getInt() & 0xFFFFFF;
    long tmp = Integer.toUnsignedLong(data.getInt());
    result.countryIndicator = (int) (tmp & 0xFFFF);
    result.languageIndicator = (int) ((tmp >> 16) & 0xFFFF);
    result.payload = new byte[data.remaining()];
    data.get(result.payload);
    return result;
  }

  @Override
  protected String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Set: ");
    str.append(parsed.set);
    if (parsed.set == 0) {
      str.append(" WellKnown");
    } else {
      str.append(" UnKnown");
    }
    str.append(" Code: ");
    str.append(parsed.typeIndicator);
    str.append(" Type: ");
    DataAtomTypeIndicator ind = DataAtomTypeIndicator.from(parsed);
    str.append(ind.getIndicatorType());
    str.append(" Value: ");
    str.append(ind.getTypeGenerator());
    return str.toString();
  }
}
