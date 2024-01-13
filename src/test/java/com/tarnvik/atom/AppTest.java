package com.tarnvik.atom;


import com.tarnvik.atom.model.mp4file.MP4File;
import com.tarnvik.atom.model.mp4file.MetaDataAccess;
import com.tarnvik.atom.parser.MP4FileFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.tarnvik.atom.model.MetaDataAtomTypeAlias.TITLE;

public class AppTest {
  @Test
  public void initialTest() throws IOException {
    Path fp = Path.of("/Users/jesper/develop/repos/personal/atom/testdata/mp4-w-sound-Hacks - 1x01.m4v");
    if (!Files.exists(fp)) {
      return;
    }
    MP4File mp4File = MP4FileFactory.create(fp);
    MetaDataAccess metaDataAccess = mp4File.getMetaDataAccess();
    String title = metaDataAccess.getMetaDataText(TITLE).orElseThrow();
    metaDataAccess.deleteMetaDataItem(TITLE); 
    mp4File.getRoots().forEach(atom -> System.out.println(atom.toString(0)));
  }
}
