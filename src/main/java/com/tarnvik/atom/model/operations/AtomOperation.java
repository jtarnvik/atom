package com.tarnvik.atom.model.operations;

import com.tarnvik.atom.model.Atom;

@FunctionalInterface
public interface AtomOperation {
  void apply(Atom atom);
}
