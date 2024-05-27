package com.tarnvik.atom.model.mp4file;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.MetaDataAtomTypeAlias;
import com.tarnvik.atom.model.atom.DATAAtom;
import com.tarnvik.atom.model.operations.DeleteAtom;
import com.tarnvik.atom.model.operations.GetSingleTextChild;
import com.tarnvik.atom.model.operations.OperationExecuter;
import com.tarnvik.atom.model.paths.AtomPath;
import com.tarnvik.atom.model.paths.PositionInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.tarnvik.atom.model.MetaDataAtomTypeAlias.TITLE;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MetaDataAccess {
  @Data
  private static class ExecutionHelper implements OperationExecuter {
    private final List<Atom> roots;

    @Override
    public Optional<List<Atom>> findChildren(AtomType at) {
      return Optional.of(findChildren(at, roots));
    }
  }

  private static final Set<MetaDataAtomTypeAlias> TEXT_ATOMS = Set.of(TITLE);
  private static final Set<MetaDataAtomTypeAlias> IMAGE_ATOMS = Set.of();
  private final MP4File mp4File;

  public void clearAllMetaData() {
    throw new IllegalStateException("Not implemented");
  }

  public void deleteMetaDataItem(MetaDataAtomTypeAlias alias) {
    AtomType at = alias.getAtomType();
    PositionInfo info = AtomPath.getPath(at).orElseThrow(() -> new IllegalStateException("Path to Atom not known, atom: " + at));
    new ExecutionHelper(mp4File.getRoots()).executeOperation(new ArrayList<>(info.getPath()), new DeleteAtom());
  }

  public Optional<String> getMetaDataText(MetaDataAtomTypeAlias alias) {
    if (!TEXT_ATOMS.contains(alias)) {
      throw new IllegalArgumentException("Atom does not support text extraction, atom: " + alias);
    }
    AtomType at = alias.getAtomType();
    PositionInfo info = AtomPath.getPath(at).orElseThrow(() -> new IllegalStateException("Path to Atom not known, atom: " + at));
    List<String> texts = new ExecutionHelper(mp4File.getRoots()).executeOperation(new ArrayList<>(info.getPath()), new GetSingleTextChild());
    if (texts.isEmpty()) {
        return Optional.empty();
    } else if (texts.size()>1) {
      throw new IllegalStateException("Multiple return values, should only be one (?), size: " + texts.size());
    } else {
      return Optional.of(texts.getFirst());
    }
  }

  public void setMetaDataText(MetaDataAtomTypeAlias alias, String text) {
    if (!TEXT_ATOMS.contains(alias)) {
      throw new IllegalArgumentException("Atom does not support text extraction, atom: " + alias);
    }
    AtomType at = alias.getAtomType();
    PositionInfo info = AtomPath.getPath(at).orElseThrow(() -> new IllegalStateException("Path to Atom not known, atom: " + at));
    // Search for node
    List<Atom> atoms = new ExecutionHelper(mp4File.getRoots()).findAtoms(info.getPath());
    Atom textParent = null;
    if (atoms.size() > 1) {
      throw new IllegalStateException("Unexpected number of atoms, expected 1 but found: " + atoms.size());
    } else if (atoms.isEmpty()) {
      // TODO: If node does not exist, create node
      // Search all but last of info.get path. Create alias.getAT there.

      List<AtomType> parentPath = new ArrayList<>(info.getPath());
      parentPath.removeLast();
      if (parentPath.isEmpty()) {
        throw new IllegalArgumentException("Unable to create text node for non-existing root");
      }
      List<Atom> parents = new ExecutionHelper(mp4File.getRoots()).findAtoms(parentPath);
      if (parents.size() != 1) {
        throw new IllegalStateException("Unexpected number of atoms, expected 1 but found: " + atoms.size());
      }
      Atom parent = parents.getFirst();
//      textParent = at.generateAtomInstance(-1, sizeAndTypeBytebuffer, parent);
      parent.markAsChanged();
      throw new IllegalStateException("Not implemented");

    } else {
      textParent = atoms.getFirst();
    }

    Atom dataAtom = DATAAtom.from(text, textParent);
    textParent.replaceChild(dataAtom);


    // TODO: Remove existing text node if any
    // TODO: Create text child. Check for string local use utf8 or utf 16 as appropriate

    throw new IllegalStateException("Not implemented");
  }

}
