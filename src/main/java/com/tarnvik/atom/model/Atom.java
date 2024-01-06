package com.tarnvik.atom.model;

import com.tarnvik.atom.parser.AtomDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@Data
public abstract class Atom {
  public static final int TO_STRING_EXTRA_INDENT = 4;

  protected final int size;
  protected final long position;
  protected final AtomType atomType;
  protected final ByteBuffer sizeAndType;
  protected final Atom parent;

  protected ByteBuffer data;

  public Atom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    sizeAndType.rewind();
    // TODO: Should be stored as long, and unsigned converted to that
    this.size = sizeAndType.asIntBuffer().get(0);
    this.position = position;
    this.atomType = atomType;
    this.sizeAndType = sizeAndType;
    this.parent = parent;
  }

  public boolean isDataLoaded() {
    return data != null;
  }

  public boolean hasParent() {
    return parent != null;
  }

  protected long getDataStartPosition() {
    return position + sizeAndType.capacity();
  }

  protected long size() {
    if (data == null) {
      throw new IllegalStateException("Data not yet parsed.");
    }
    return sizeAndType.capacity() + data.capacity();
  }

  public abstract ParsedAtom parseData() throws IOException;

  public void loadData(AtomDataSource ads) throws IOException {
    // TODO: Skip reading the data for mdat atoms, use seek instead.
    if (size == 0) {
      throw new IllegalStateException("Size 0. Special handling not implemented.");
    } else if (size == 1) {
      throw new IllegalStateException("Size 1. Special handling not implemented.");
    } else {
      data = ads.extract(size - 8)
        .orElseThrow(() -> new IllegalStateException("Unable to read specified data size"));
    }
  }

  protected abstract String toStringChild(int indentLevel);

  public String toString(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Type: ");
    str.append(atomType.getType());
    if (atomType == AtomType.UNKNOWN) {
      sizeAndType.position(4);
      str.append("/").append(StandardCharsets.UTF_8.decode(sizeAndType)).append("/");
    }
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
