package com.tarnvik.atom.model.operations;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface OperationExecuter {
  Optional<List<Atom>> findChildren(AtomType type);

  default List<Atom> findChildren(AtomType at, List<Atom> subAtoms) {
    return subAtoms.stream()
      .filter(itm -> itm.getAtomType() == at)
      .toList();
  }

  default void executeOperation(List<AtomType> path, AtomOperation op) {
    if (path.isEmpty()) {
      throw new IllegalArgumentException("Unable to execute on empty path");
    }
    AtomType at = path.removeFirst();
    Optional<List<Atom>> matchingChildrenOpt = findChildren(at);
    if (matchingChildrenOpt.isEmpty() || matchingChildrenOpt.get().isEmpty()) {
      return;
    }
    List<Atom> matchingChildren = matchingChildrenOpt.get();
    if (path.isEmpty()) {
      matchingChildren.forEach(op::apply);
    } else {
      matchingChildren.forEach(atom -> atom.executeOperation(new ArrayList<>(path), op));
    }
  }
}
