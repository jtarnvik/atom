package com.tarnvik.atom.parser;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.atom.SubAtoms;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.tarnvik.atom.model.converter.TypeConverter.convertUTF8ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtomFactory {
  public static List<Atom> loadAll(Path fp, long position, Atom parent) throws IOException {
    try (FileChannel fc = FileChannelFactory.create(fp)) {
      AtomDataSource ads = new AtomDataSource(fc);
      return loadAll(ads, position, parent);
    }
  }

  public static List<Atom> loadAll(ByteBuffer byteBuffer, long position, Atom parent) throws IOException {
    AtomDataSource ads = new AtomDataSource(byteBuffer);
    return loadAll(ads, position, parent);
  }

  private static List<Atom> loadAll(AtomDataSource ads, long position, Atom parent) throws IOException {
    List<Atom> result = new ArrayList<>();
    Optional<Atom> next = AtomFactory.next(ads, position, parent);
    while (next.isPresent()) {
      Atom atom = next.get();
      result.add(atom);
      position += atom.getSize();
      next = AtomFactory.next(ads, position, parent);
    }
    return result;
  }

  public static Optional<Atom> next(AtomDataSource ads, long position, Atom parent) throws IOException {
    Optional<ByteBuffer> sizeAndTypeOpt = ads.extract(8);
    if (sizeAndTypeOpt.isEmpty()) {
      return Optional.empty();
    }
    ByteBuffer sizeAndType = sizeAndTypeOpt.get();
    sizeAndType.position(4);
    byte[] chtmp = new byte[4];
    sizeAndType.get(chtmp);
    String type = convertUTF8ToString(chtmp);

    Atom atom = AtomType.from(type).generateAtomInstance(position, sizeAndType, parent);
    atom.loadData(ads);
    if (atom instanceof SubAtoms subAtoms) {
      subAtoms.parseSubAtoms();
    }
//    atom.parseData();

    return Optional.of(atom);
  }
}
