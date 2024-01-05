package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;

import java.nio.ByteBuffer;

public class UnknownAtom extends Atom {
  public UnknownAtom(long position, ByteBuffer sizeAndType, AtomType at, Atom parent) {
    super(position, sizeAndType, at, parent);
  }

  @Override
  public void parseData() {
    // Do nothing by design
  }

  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: ######### Unknown #########");
    return str.toString();
  }
}
