package com.tarnvik.atom.model.operations;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.atom.SubAtoms;

public class GetSingleTextChild implements AtomOperation<String>{
  @Override
  public String apply(Atom atom) {
    if (atom instanceof SubAtoms subAtoms) {
      if (subAtoms.getSubAtoms().size() != 1) {
        throw new IllegalStateException("Unexpected number of subatoms, expcted 1 found: " + subAtoms.getSubAtoms().size());
      }
      Atom child = subAtoms.getSubAtoms().getFirst();


    } else {
      throw new IllegalStateException("Atom is not of SubAtoms type. No children present, type: " + atom.getClass());
    }
    return null;
  }
}
