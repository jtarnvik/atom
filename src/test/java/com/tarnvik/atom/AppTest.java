package com.tarnvik.atom;


import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.parser.AtomFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class AppTest {
  @Test
  public void initialTest() throws IOException {
    Path fp = Path.of("/Users/jesper/develop/repos/personal/atom/testdata/mp4-w-sound-Hacks - 1x01.m4v");
    if (!Files.exists(fp)) {
      return;
    }
    List<Atom> result = AtomFactory.loadAll(fp, 0);
    result.forEach(atom -> System.out.println(atom.toString(0)));
  }
}
