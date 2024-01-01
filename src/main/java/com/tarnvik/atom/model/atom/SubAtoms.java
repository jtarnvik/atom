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
  private final List<Atom> subAtoms = new ArrayList<>();

  public SubAtoms(long position, ByteBuffer sizeAndType, AtomType atomType) {
    super(position, sizeAndType, atomType);
  }

  @Override
  public void parseData() throws IOException {
    data.rewind();
    subAtoms.addAll(AtomFactory.loadAll(data, getDataStartPosition()));
    subAtoms.forEach(atom -> atom.setParent(this));
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
