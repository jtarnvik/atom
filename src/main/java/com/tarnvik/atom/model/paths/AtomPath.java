package com.tarnvik.atom.model.paths;


import com.tarnvik.atom.model.AtomType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.tarnvik.atom.model.AtomType.ATNAM;
import static com.tarnvik.atom.model.AtomType.ILST;
import static com.tarnvik.atom.model.AtomType.META;
import static com.tarnvik.atom.model.AtomType.MOOV;
import static com.tarnvik.atom.model.AtomType.UDTA;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtomPath {
  private static Map<AtomType, PositionInfo> path = new HashMap<>();

  static {
    path.put(ATNAM, new PositionInfo(List.of(MOOV, UDTA, META, ILST, ATNAM), true, 1, true));
  }

  public static Optional<PositionInfo> getPath(AtomType at) {
    PositionInfo positionInfo = path.get(at);
    if (positionInfo == null) {
      return Optional.empty();
    } else {
      return Optional.of(positionInfo);
    }
  }
}
