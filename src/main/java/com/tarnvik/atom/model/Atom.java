package com.tarnvik.atom.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@AllArgsConstructor
@Data
public abstract class Atom {
  public static final int TO_STRING_EXTRA_INDENT = 4;

  protected final int size;
  protected final long position;
  protected final AtomType atomType;

  protected ByteBuffer data;
  protected boolean dataLoaded = false;
  protected Atom parent;

  public Atom(int size, long position, AtomType atomType) {
    this.size = size;
    this.position = position;
    this.atomType = atomType;
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

  protected abstract String toStringChild(int indentLevel);

  public String toString(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Type: ");
    str.append(atomType.getType());
    str.append(" (");
    str.append(atomType.getHrName());
    str.append("), Size: ");
    str.append(size);
    str.append(", Pos: ");
    str.append(position);
    str.append("\n");
    str.append(toStringChild(indentLevel + TO_STRING_EXTRA_INDENT));
    return str.toString();
  }
}
