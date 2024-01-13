package com.tarnvik.atom.model.operations;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface OperationExecuter {
  Optional<List<Atom>> findChildren(AtomType type);

  default List<Atom> findChildren(AtomType at, List<Atom> subAtoms) {
    return subAtoms.stream()
      .filter(itm -> itm.getAtomType() == at)
      .toList();
  }

  default <T> List<T> executeOperation(List<AtomType> path, AtomOperation<T> op) {
    if (path.isEmpty()) {
      throw new IllegalArgumentException("Unable to execute on empty path");
    }
    ArrayList<AtomType> lPath = new ArrayList<>(path);

    AtomType at = lPath.removeFirst();
    Optional<List<Atom>> matchingChildrenOpt = findChildren(at);
    if (matchingChildrenOpt.isEmpty() || matchingChildrenOpt.get().isEmpty()) {
      return List.of();
    }
    List<Atom> matchingChildren = matchingChildrenOpt.get();
    if (lPath.isEmpty()) {
      return matchingChildren.stream()
        .map(op::apply)
        .toList();
    } else {
      return matchingChildren
        .stream()
        .map(atom -> atom.executeOperation(new ArrayList<>(lPath), op))
        .flatMap(Collection::stream)
        .toList();
    }
  }
}
