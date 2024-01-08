package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TypeImageSupport extends TypeBase {
  protected TypeImageSupport(DATAAtom.Parsed parsed) {
    super(parsed);
  }

  @Override
  public String toString() {
//    toBufferedImage();
    return "A image";
  }

  @Override
  public boolean supportsBufferedImageRead() {
    return true;
  }

  @Override
  public BufferedImage toBufferedImage() {
    ByteArrayInputStream bais = new ByteArrayInputStream(parsed.getPayload());
    try {
      BufferedImage read = ImageIO.read(bais);
//      ImageIO.write(read, "jpg", new File("/Users/jesper/tmp/" + UUID.randomUUID().toString() + ".jpg"));
//      ImageIO.write(read, "png", new File("/Users/jesper/tmp/" + UUID.randomUUID().toString() + ".png"));
      return read;
    } catch (IOException e) {
      // Better error handlingwould be nice
      throw new RuntimeException(e);
    }
  }
}
