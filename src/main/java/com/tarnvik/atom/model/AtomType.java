package com.tarnvik.atom.model;

import com.tarnvik.atom.model.atom.CTTSAtom;
import com.tarnvik.atom.model.atom.DATAAtom;
import com.tarnvik.atom.model.atom.DREFAtom;
import com.tarnvik.atom.model.atom.ELSTAtom;
import com.tarnvik.atom.model.atom.FREEAtom;
import com.tarnvik.atom.model.atom.FTYPAtom;
import com.tarnvik.atom.model.atom.HDLRAtom;
import com.tarnvik.atom.model.atom.MDHDAtom;
import com.tarnvik.atom.model.atom.MVHDAtom;
import com.tarnvik.atom.model.atom.SBGPAtom;
import com.tarnvik.atom.model.atom.SDTPAtom;
import com.tarnvik.atom.model.atom.SGPDAtom;
import com.tarnvik.atom.model.atom.SMHDAtom;
import com.tarnvik.atom.model.atom.STCOAtom;
import com.tarnvik.atom.model.atom.STSCAtom;
import com.tarnvik.atom.model.atom.STSDAtom;
import com.tarnvik.atom.model.atom.STSSAtom;
import com.tarnvik.atom.model.atom.STSZAtom;
import com.tarnvik.atom.model.atom.STTSAtom;
import com.tarnvik.atom.model.atom.SubAtoms;
import com.tarnvik.atom.model.atom.TKHDAtom;
import com.tarnvik.atom.model.atom.TREFAtom;
import com.tarnvik.atom.model.atom.UnknownAtom;
import com.tarnvik.atom.model.atom.VMHDAtom;
import lombok.Getter;

import java.nio.ByteBuffer;

public enum AtomType {
  // @formatter:off
  CTTS(   "ctts", CTTSAtom::new,    "Composition Offset"),
  DATA(   "data", DATAAtom::new,    "Data payload"),
  DINF(   "dinf", SubAtoms::new,    "Data Information"),
  DREF(   "dref", DREFAtom::new,    "Data Reference"),
  EDTS(   "edts", SubAtoms::new,    "Edit"),
  ELST(   "elst", ELSTAtom::new,    "Edit list"),
  FREE(   "free", FREEAtom::new,    "Unallocated space"),
  FTYP(   "ftyp", FTYPAtom::new,    "File Type Compatibility"),
  HDLR(   "hdlr", HDLRAtom::new,    "Handler reference"),
  MDIA(   "mdia", SubAtoms::new,    "Media header"),
  MDHD(   "mdhd", MDHDAtom::new,    "Movie header"),
  MINF(   "minf", SubAtoms::new,    "Media Information"),
  MOOV(   "moov", SubAtoms::new,    "Movie resource metadata"),
  MNHD(   "mvhd", MVHDAtom::new,    "Movie header"),
  SBGP(   "sbgp", SBGPAtom::new,    "Sample-to-Group"),
  SGPD(   "sgpd", SGPDAtom::new,    "Sample Group Description"),
  SMHD(   "smhd", SMHDAtom::new,    "Sound Media Information Header"),
  STBL(   "stbl", SubAtoms::new,    "Sample Table"),
  SDTP(   "sdtp", SDTPAtom::new,    "Sample Dependency Flags"),
  STCO(   "stco", STCOAtom::new,    "Chunk Offset"),
  STSC(   "stsc", STSCAtom::new,    "Sample-to-Chunk"),
  STSD(   "stsd", STSDAtom::new,    "Sample Description"),
  STSS(   "stss", STSSAtom::new,    "Sync Sample"),
  STSZ(   "stsz", STSZAtom::new,    "Sample Size"),
  STTS(   "stts", STTSAtom::new,    "Time-to-Sample"),
  TKHD(   "tkhd", TKHDAtom::new,    "Track header"),
  TRAK(   "trak", SubAtoms::new,    "Trak"),
  TREF(   "tref", TREFAtom::new,    "Track reference"),
  UDTA(   "udta", SubAtoms::new,    "User data"),
  VMHD(   "vmhd", VMHDAtom::new,    "Videa Media Information Header"),

  UNKNOWN("UNKN", UnknownAtom::new, "Unknown - Not yet identified");
  // @formatter:on

  @Getter
  private final String hrName;
  @Getter
  private final String type;
  private final AtomGenerator<Long, ByteBuffer, Atom> atomProvider;

  AtomType(String type, AtomGenerator<Long, ByteBuffer, Atom> atomProvider, String hrName) {
    this.hrName = hrName;
    this.type = type;
    this.atomProvider = atomProvider;
  }

  public Atom generateAtomInstance(long position, ByteBuffer sizeAndType) {
    return atomProvider.apply(position, sizeAndType, this);
  }

  public static AtomType from(String type) {
    for (AtomType at : values()) {
      if (at.getType().equals(type)) {
        return at;
      }
    }
    return UNKNOWN;
  }
}
