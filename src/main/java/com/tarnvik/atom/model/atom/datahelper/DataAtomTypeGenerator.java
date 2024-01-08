package com.tarnvik.atom.model.atom.datahelper;

import java.awt.image.BufferedImage;

public interface DataAtomTypeGenerator {
  String toString();

  boolean supportsLong();
  long toLong();

  boolean supportsBufferedImageRead();
  BufferedImage toBufferedImage();
}