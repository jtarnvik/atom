package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.parser.AtomFactory;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Getter
public class SubAtoms extends Atom {
  protected final List<Atom> subAtoms = new ArrayList<>();

  public SubAtoms(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public void parseData() throws IOException {
    data.rewind();
    parseSubAtoms(0);
  }

  protected void parseSubAtoms(int adjustedPosition) throws IOException {
    subAtoms.addAll(AtomFactory.loadAll(data, getDataStartPosition() + adjustedPosition, this));
  }

  @Override
  protected String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.append(
      subAtoms.stream()
        .map(atom -> atom.toString(indentLevel))
        .collect(Collectors.joining("\n"))
    );
    return str.toString();
  }
}
