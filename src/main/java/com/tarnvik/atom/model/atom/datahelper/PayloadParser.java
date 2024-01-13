package com.tarnvik.atom.model.atom.datahelper;

import java.awt.image.BufferedImage;

public interface PayloadParser {
  boolean supportsStringRead();
  String toString();

  boolean supportsLong();
  long toLong();

  boolean supportsBufferedImageRead();
  BufferedImage toBufferedImage();
}