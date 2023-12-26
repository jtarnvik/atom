package com.tarnvik.atom.parser;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileChannelFactory {
  public static FileChannel create(Path fp) throws IOException {
    // TODO: Verify that the fp points to a movie file with a format we can handle
    return FileChannel.open(fp, StandardOpenOption.READ);
  }

}
