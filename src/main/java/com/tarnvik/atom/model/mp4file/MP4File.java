package com.tarnvik.atom.model.mp4file;

import com.tarnvik.atom.model.Atom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MP4File {

  private List<Atom> roots = new ArrayList<>();

  public MetaDataAccess getMetaDataAccess() {
    return new MetaDataAccess(this);
  }
}
