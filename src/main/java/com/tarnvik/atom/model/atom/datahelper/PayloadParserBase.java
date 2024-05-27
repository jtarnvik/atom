package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public abstract class PayloadParserBase implements PayloadParser {
  protected final DATAAtom.Parsed parsed;

  public PayloadParserBase(DATAAtom.Parsed parsed) {
    this.parsed = parsed;
  }

  @Override
  public boolean supportsStringRead() {
    return false;
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
