package com.tarnvik.atom.model.mp4file;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.MetaDataAtomTypeAlias;
import com.tarnvik.atom.model.operations.DeleteAtom;
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

  public Optional<String> getEpisodeTitle(MetaDataAtomTypeAlias alias) {
    if (!TEXT_ATOMS.contains(alias)) {
      throw new IllegalArgumentException("Atom does not support text extraction, atom: " + alias);
    }
    throw new IllegalStateException("Not implemented");
  }

  public void setEpisodeTitle(String title) {
    throw new IllegalStateException("Not implemented");
  }

}
