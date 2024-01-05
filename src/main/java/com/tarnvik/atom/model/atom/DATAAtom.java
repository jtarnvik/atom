package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.atom.dataitemhelper.DataAtomStringGenerator;
import com.tarnvik.atom.model.atom.dataitemhelper.DataAtomTypeIndicator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class DATAAtom extends Atom {
  private byte set;
  private int typeIndicator;
  private int countryIndicator;
  private int languageIndicator;
  private byte[] payload;

  public DATAAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public void parseData() throws IOException {
    set = data.slice().get();
    typeIndicator = data.getInt() &0xFFFFFF;
    long tmp = Integer.toUnsignedLong(data.getInt());
    countryIndicator = (int) (tmp & 0xFFFF);
    languageIndicator = (int) ((tmp >> 16) & 0xFFFF);
    payload = new byte[data.remaining()];
    data.get(payload);
//    Parse me next, det finns en implementerad parser i rust
    // Not yet implemented
  }

  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Set: ");
    str.append(set);
    if (set == 0) {
      str.append(" WellKnown");
    } else {
      str.append(" UnKnown");
    }
    str.append(" Code: ");
    str.append(typeIndicator);
    DataAtomTypeIndicator ind = DataAtomTypeIndicator.from(this);
    str.append(" Type: ");
    str.append(ind.getIndicatorType());
    str.append(" Value: ");
    str.append(ind.getStringGenerator().generate());
    return str.toString();
  }
}
