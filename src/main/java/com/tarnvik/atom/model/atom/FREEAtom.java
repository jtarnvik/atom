package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@Getter
public class FREEAtom extends Atom {
  public FREEAtom(long position, ByteBuffer sizeAndType, AtomType at) {
    super(position, sizeAndType, at);
  }

  @Override
  public void parseData() {
    // Empty by design, padding and unallocated space
  }

  @Override
  public String toString(int indentLevel) {
    StringBuilder str = new StringBuilder();
    str.append(super.toString(indentLevel));
    str.repeat(" ", indentLevel + TO_STRING_EXTRA_INDENT);
    str.append("Parsed: Unallocated space");
    return str.toString();
  }

}
