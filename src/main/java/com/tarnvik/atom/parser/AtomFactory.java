package com.tarnvik.atom.parser;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.FTYPAtom;
import com.tarnvik.atom.model.UnknownAtom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtomFactory {
  public static Optional<Atom> next(FileChannel fc) {
    try {
      long position = fc.position();
      ByteBuffer sizeAndType = ByteBuffer.allocate(8);
      int read = fc.read(sizeAndType);
      if (read != 8) {
        return Optional.empty();
      }
      sizeAndType.rewind();
      int size = sizeAndType.asIntBuffer().get(0);
      sizeAndType.position(4);
      String type = StandardCharsets.UTF_8.decode(sizeAndType).toString();

      Atom atom = switch (AtomType.from(type)) {
        case FTYP:
          yield new FTYPAtom(size, type, position);
        case UNKNWON:
          yield new UnknownAtom(size, type, position);
      };
      atom.loadData(fc);
      atom.parseData();

      return Optional.of(atom);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}