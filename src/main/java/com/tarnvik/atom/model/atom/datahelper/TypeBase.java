package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;

import java.awt.image.BufferedImage;

public abstract class TypeBase implements DataAtomTypeGenerator {
  protected final DATAAtom.Parsed parsed;

  protected TypeBase(DATAAtom.Parsed parsed) {
    this.parsed = parsed;
  }

  @Override
  public String toString() {
    return "Not implemented";
  }

  @Override
  public boolean supportsLong() {
    return false;
  }

  @Override
  public long toLong() {
    throw new IllegalStateException(this.getClass().toString() +  " does not support toLong");
  }

  @Override
  public boolean supportsBufferedImageRead() {
    return false;
  }

  @Override
  public BufferedImage toBufferedImage() {
    throw new IllegalStateException(this.getClass().toString() +  " does not support toLong");
  }
}
