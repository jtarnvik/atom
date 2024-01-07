package com.tarnvik.atom.model.atom.parts;

import lombok.Data;

// TODO: convert to record and external class
@Data
public class PackedLanguage {
  private short packedLanguage;
  private String unpackedLangage;
}
