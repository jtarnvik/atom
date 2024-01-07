package com.tarnvik.atom.model.parsedatom;

import lombok.Data;

@Data
public abstract class VersionedParsedAtom implements ParsedAtom {
  protected int version;
  protected byte[] flags;
}
