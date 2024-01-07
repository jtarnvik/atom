package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.parsedatom.ParsedAtom;

import java.io.IOException;
import java.nio.ByteBuffer;

public class UnknownAtom extends Atom {
  public UnknownAtom(long position, ByteBuffer sizeAndType, AtomType at, Atom parent) {
    super(position, sizeAndType, at, parent);
  }

  @Override
  public ParsedAtom parseData() throws IOException {
    return new MVHDAtom.Parsed(){};
  }


  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: ######### Unknown #########");
    return str.toString();
  }
}
