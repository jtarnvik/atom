package com.tarnvik.atom;


import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.parser.AtomFactory;
import com.tarnvik.atom.parser.FileChannelFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AppTest {
  @Test
  public void initialTest() throws IOException {
    List<Atom> result = new ArrayList<>();
    Path fp = Path.of("/Users/jesper/develop/repos/personal/atom/testdata/mov-w-sound-Hacks - 1x01.m4v");
    if (!Files.exists(fp)) {
      return;
    }
    try (FileChannel fc = FileChannelFactory.create(fp)) {
      Assertions.assertNotNull(fc);
      AtomFactory.next(fc).ifPresent(result::add);
    }
  }
}
