package com.tarnvik.atom.parser;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AtomDataSource {
  private final ByteBuffer byteBuffer;
  private final FileChannel fileChannel;

  public AtomDataSource(ByteBuffer byteBuffer) {
    this(byteBuffer, null);
  }

  public AtomDataSource(FileChannel fileChannel) {
    this(null, fileChannel);
  }

  public boolean supportsByteBuffer() {
    return byteBuffer != null;
  }

  public Optional<ByteBuffer> extract(int size) throws IOException {
    ByteBuffer result;
    if (supportsByteBuffer()) {
      if (!byteBuffer.hasRemaining()) {
        return Optional.empty();
      }

      result = byteBuffer.slice(byteBuffer.position(), size);
      byteBuffer.position(byteBuffer.position() + size);
    } else {
      result = ByteBuffer.allocate(size);
      int read = fileChannel.read(result);
      if (read != size) {
        return Optional.empty();
      }
    }
    return Optional.of(result);
  }
}


