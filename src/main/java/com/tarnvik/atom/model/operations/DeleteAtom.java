package com.tarnvik.atom.model.operations;

import com.tarnvik.atom.model.Atom;

public class DeleteAtom implements AtomOperation<Boolean> {
  @Override
  public Boolean apply(Atom atom) {
    if (!atom.hasParent())  {
      throw new IllegalArgumentException("Unable to remove root.");
    }
    return atom.getParent().removeChild(atom);
  }
}
