package com.tarnvik.atom;


import com.tarnvik.atom.model.MP4File;
import com.tarnvik.atom.parser.MP4FileFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppTest {
  @Test
  public void initialTest() throws IOException {
    Path fp = Path.of("/Users/jesper/develop/repos/personal/atom/testdata/mp4-w-sound-Hacks - 1x01.m4v");
    if (!Files.exists(fp)) {
      return;
    }
    MP4File mp4File = MP4FileFactory.create(fp);
    mp4File.deleteEpisodeTitle();
    mp4File.getRoots().forEach(atom -> System.out.println(atom.toString(0)));
  }
}
