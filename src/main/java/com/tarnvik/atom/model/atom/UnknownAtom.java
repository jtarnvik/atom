package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;

import java.nio.ByteBuffer;

public class UnknownAtom extends Atom {
  public UnknownAtom(long position, ByteBuffer sizeAndType, AtomType at) {
    super(position, sizeAndType, at);
  }

  @Override
  public void parseData() {
    // Do nothing by design
  }

  @Override
  public String toString(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.append(super.toString(indentLevel));
    str.repeat(" ", indentLevel + TO_STRING_EXTRA_INDENT);
    str.append("Parsed: ---Not yet implemented---");
    return str.toString();
  }
}
