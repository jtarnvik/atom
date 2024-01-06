package com.tarnvik.atom.model.atom.parts;

import lombok.Data;

// TODO: convert to record and external class
@Data
public class VersionFlag {
  private int version;
  private byte[] flags = new byte[3];
}
