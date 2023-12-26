package com.tarnvik.atom.model;

public class UnknownAtom extends Atom {
  public UnknownAtom(int size, String type, long position) {
    super(size, type, position);
  }

  @Override
  public void parseData() {
    // Do nothing by design
  }
}
