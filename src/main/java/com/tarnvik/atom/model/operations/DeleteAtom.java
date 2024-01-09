package com.tarnvik.atom.model.operations;

import com.tarnvik.atom.model.Atom;

public class DeleteAtom implements AtomOperation {
  @Override
  public void apply(Atom atom) {
    if (!atom.hasParent())  {
      throw new IllegalArgumentException("Unable to remove root.");
    }
    atom.getParent().removeChild(atom);
  }
}
