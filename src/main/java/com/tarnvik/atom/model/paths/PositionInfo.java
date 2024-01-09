package com.tarnvik.atom.model.paths;

import com.tarnvik.atom.model.AtomType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PositionInfo {
  private final List<AtomType> path;
  private final boolean containsDATAAtom;
  private final int typeIndicatorId;
  private final boolean single;

  public boolean multipleCopiesExist() {
    return !single;
  }
}
