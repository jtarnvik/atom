package com.tarnvik.atom.parser;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.MP4File;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MP4FileFactory {
  public static MP4File create(Path fp) throws IOException {
    MP4File result = new MP4File();
    List<Atom> atoms = AtomFactory.loadAll(fp, 0, null);
    result.setRoots(atoms);
    return result;
  }
}
