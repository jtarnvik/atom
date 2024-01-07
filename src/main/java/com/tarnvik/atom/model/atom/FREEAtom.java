package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.parsedatom.ParsedAtom;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.IOException;
import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FREEAtom extends Atom {
  public FREEAtom(long position, ByteBuffer sizeAndType, AtomType at, Atom parent) {
    super(position, sizeAndType, at, parent);
  }

  @Override
  public ParsedAtom parseData() throws IOException {
    return null;
    // Not yet implemented
  }


  @Override
  public String toStringChild(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: Unallocated space");
    return str.toString();
  }

}
