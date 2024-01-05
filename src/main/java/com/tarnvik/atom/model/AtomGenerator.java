package com.tarnvik.atom.model;

import java.nio.ByteBuffer;

@FunctionalInterface
interface AtomGenerator {
  Atom apply(Long a, ByteBuffer b, AtomType at, Atom parent);
}