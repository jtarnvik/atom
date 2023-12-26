package com.tarnvik.atom.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@AllArgsConstructor
@Data
public abstract class Atom {
  protected ByteBuffer data;

  protected final int size;
  protected final String type;
  protected final long position;

  protected boolean dataLoaded = false;

  public Atom(int size, String type, long position) {
    this.size = size;
    this.type = type;
    this.position = position;
  }

  public abstract void parseData();

  public void loadData(FileChannel fc) throws IOException {
    // TODO: Skip reading the data for mdat atoms, use seek instead.
    if (size == 0) {
      throw new IllegalStateException("Size 0. Special handling not implemented.");
    } else if (size == 1) {
      throw new IllegalStateException("Size 1. Special handling not implemented.");
    } else {
      ByteBuffer data = ByteBuffer.allocate(size - 8); // size and type already read
      fc.read(data);
      dataLoaded = true;
      this.data = data;
    }
  }

  public static String byteToHexString(byte b) {
    return String.format("0x%02X", b & 0xFF);
  }
}
