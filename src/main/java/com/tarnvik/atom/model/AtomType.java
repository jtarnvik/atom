package com.tarnvik.atom.model;

import com.tarnvik.atom.model.atom.CTTSAtom;
import com.tarnvik.atom.model.atom.DATAAtom;
import com.tarnvik.atom.model.atom.DREFAtom;
import com.tarnvik.atom.model.atom.ELSTAtom;
import com.tarnvik.atom.model.atom.FREEAtom;
import com.tarnvik.atom.model.atom.FTYPAtom;
import com.tarnvik.atom.model.atom.HDLRAtom;
import com.tarnvik.atom.model.atom.MDATAtom;
import com.tarnvik.atom.model.atom.MDHDAtom;
import com.tarnvik.atom.model.atom.METAAtom;
import com.tarnvik.atom.model.atom.MVHDAtom;
import com.tarnvik.atom.model.atom.NAMEAtom;
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
import com.tarnvik.atom.model.atom.WIDEAtom;
import lombok.Getter;

import javax.print.attribute.standard.Media;
import javax.tools.Tool;
import java.nio.ByteBuffer;

public enum AtomType {
  // @formatter:off
  ATCMT(  "©cmt", SubAtoms::new,    "Meta - ???"),
  ATNAM(  "©nam", SubAtoms::new,    "Meta - ???"),
  ATTOO(  "©too", SubAtoms::new,    "Meta - Tool"),
  COVR(   "covr", SubAtoms::new,    "Meta - Cover?"),
  CTTS(   "ctts", CTTSAtom::new,    "Composition Offset"),
  DATA(   "data", DATAAtom::new,    "Data payload"),
  DESC(   "desc", SubAtoms::new,    "Description"),
  DINF(   "dinf", SubAtoms::new,    "Data Information"),
  DREF(   "dref", DREFAtom::new,    "Data Reference"),
  EDTS(   "edts", SubAtoms::new,    "Edit"),
  ELST(   "elst", ELSTAtom::new,    "Edit list"),
  FREE(   "free", FREEAtom::new,    "Unallocated space"),
  FTYP(   "ftyp", FTYPAtom::new,    "File Type Compatibility"),
  HDLR(   "hdlr", HDLRAtom::new,    "Handler reference"),
  ILST(   "ilst", SubAtoms::new,    "Meta data container"),
  LDES(   "ldes", SubAtoms::new,    "Long Description"),
  MDAT(   "mdat", MDATAtom::new,    "Movie Sample Data"),
  MDHA(   "mdha", SubAtoms::new,    "Media header"),
  META(   "meta", METAAtom::new,    "Meta data"),
  MDHD(   "mdhd", MDHDAtom::new,    "Movie header"),
  MDIA(   "mdia", SubAtoms::new,    "Media"),
  MINF(   "minf", SubAtoms::new,    "Media Information"),
  MOOV(   "moov", SubAtoms::new,    "Movie resource metadata"),
  MNHD(   "mvhd", MVHDAtom::new,    "Movie header"),
  NAME(   "name", NAMEAtom::new,    "Name"),
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
  TVES(   "tves", SubAtoms::new,    "TV Show Episode"),
  TVSH(   "tvsh", SubAtoms::new,    "TV Show Description"),
  TVSN(   "tvsn", SubAtoms::new,    "TV Show Season"),
  UDTA(   "udta", SubAtoms::new,    "User data"),
  VMHD(   "vmhd", VMHDAtom::new,    "Videa Media Information Header"),
  WIDE(   "wide", WIDEAtom::new,    "Unallocated space"),

  UNKNOWN("UNKN", UnknownAtom::new, "Unknown - Not yet identified");
  // @formatter:on

  @Getter
  private final String hrName;
  @Getter
  private final String type;
  private final AtomGenerator atomProvider;

  AtomType(String type, AtomGenerator atomProvider, String hrName) {
    this.hrName = hrName;
    this.type = type;
    this.atomProvider = atomProvider;
  }

  public Atom generateAtomInstance(long position, ByteBuffer sizeAndType, Atom parent) {
    return atomProvider.apply(position, sizeAndType, this, parent);
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
