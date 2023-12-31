package com.tarnvik.atom.parser;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtomFactory {
  public static List<Atom> loadAll(Path fp, long position) throws IOException {
    try (FileChannel fc = FileChannelFactory.create(fp)) {
      AtomDataSource ads = new AtomDataSource(fc);
      return loadAll(ads, position);
    }
  }

  public static List<Atom> loadAll(ByteBuffer byteBuffer, long position) throws IOException {
    AtomDataSource ads = new AtomDataSource(byteBuffer);
    return loadAll(ads, position);
  }

  private static List<Atom> loadAll(AtomDataSource ads, long position) throws IOException {
    List<Atom> result = new ArrayList<>();
    Optional<Atom> next = AtomFactory.next(ads, position);
    while (next.isPresent()) {
      Atom atom = next.get();
      result.add(atom);
      position += atom.getSize();
      next = AtomFactory.next(ads, position);
    }
    return result;
  }

  public static Optional<Atom> next(AtomDataSource ads, long position) throws IOException {
    Optional<ByteBuffer> sizeAndTypeOpt = ads.extract(8);
    if (sizeAndTypeOpt.isEmpty()) {
      return Optional.empty();
    }
    ByteBuffer sizeAndType = sizeAndTypeOpt.get();
    sizeAndType.position(4);
    String type = StandardCharsets.UTF_8.decode(sizeAndType).toString();

    Atom atom = AtomType.from(type).generateAtomInstance(position, sizeAndType);
    atom.loadData(ads);
    atom.parseData();

    return Optional.of(atom);
  }
}
