package com.tarnvik.atom.model;

import com.tarnvik.atom.model.operations.DeleteAtom;
import com.tarnvik.atom.model.operations.OperationExecuter;
import com.tarnvik.atom.model.paths.AtomPath;
import com.tarnvik.atom.model.paths.PositionInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MP4File {

  @Data
  private static class ExecutionHelper implements OperationExecuter {

    private final List<Atom> roots;

    @Override
    public Optional<List<Atom>> findChildren(AtomType at) {
      return Optional.of(findChildren(at, roots));
    }
  }

  List<Atom> roots = new ArrayList<>();

  public void clearAllMetaData() {
    throw new IllegalStateException("Not implemented");
  }

  public Optional<String> getEpisodeTitle() {
    throw new IllegalStateException("Not implemented");
  }

  public void setEpisodeTitle(String title) {
    throw new IllegalStateException("Not implemented");
  }

  public void deleteEpisodeTitle() {
    PositionInfo info = AtomPath.getPath(AtomType.ATNAM).orElseThrow(() -> new IllegalStateException("Path to Atom title not known."));
    new ExecutionHelper(roots).executeOperation(new ArrayList<>(info.getPath()), new DeleteAtom());
  }
}
