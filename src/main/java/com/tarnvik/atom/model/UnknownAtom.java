package com.tarnvik.atom.model;

public class UnknownAtom extends Atom {
  public UnknownAtom(int size, long position, AtomType at) {
    super(size, position, at);
  }

  @Override
  public void parseData() {
    // Do nothing by design
  }

  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: ---Not yet implemented---");
    return str.toString();
  }
}
