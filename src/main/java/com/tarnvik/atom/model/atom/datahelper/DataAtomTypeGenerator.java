package com.tarnvik.atom.model.atom.datahelper;

public interface DataAtomTypeGenerator {
  String toString();

  boolean supportsLong();

  long toLong();
}