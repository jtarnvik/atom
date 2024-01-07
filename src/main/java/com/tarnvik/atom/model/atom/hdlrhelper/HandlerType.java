package com.tarnvik.atom.model.atom.hdlrhelper;

import lombok.Getter;

public enum HandlerType {
  CHAP("chap", "Chapter Track", "MP4"),
  HINT("hint", "Hint Track", "MP4"),
  IPMC("ipmc", "IPMP Control Track", "MP4"),
  MDIR("mdir", "Media directory", "Quicktime"),
  META("meta", "Metadata Track", "MP4"),
  MDTA("mdta", "Metadata", "Quicktime"),
  SOUN("soun", "Audio Track", "MP4"),
  MP7B("mp7b", "MPEG-7 Metadata Track", "MP4"),
  ODSB("odsb", "Descriptor Track Base", "MP4"),
  ODSM("odsm", "Descriptor Track Stream", "MP4"),
  TEXT("text", "Subtitle Track", "MP4"),
  TMCD("tmcd", "Timecode Track", "MP4"),
  VIDE("vide", "Video Track", "MP4"),
  UNKNOWN("unkn", "Unknwn", "");

  @Getter
  private String type;
  @Getter
  private String description;
  @Getter
  private String usedInFormat;

  HandlerType(String type, String description, String usedInFormat) {
    this.type = type;
    this.description = description;
    this.usedInFormat = usedInFormat;
  }

  public static HandlerType from(String type) {
    for (HandlerType ht : values()) {
      if (ht.getType().equalsIgnoreCase(type)) {
        return ht;
      }
    }
    return UNKNOWN;
  }

}
