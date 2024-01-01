package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class STSSAtom extends Atom {
  public STSSAtom(long position, ByteBuffer sizeAndType, AtomType atomType) {
    super(position, sizeAndType, atomType);
  }

  @Override
  public void parseData() throws IOException {
    // Not yet implemented
  }

  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: ---Not yet implemented---");
    return str.toString();
  }
}
