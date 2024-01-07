package com.tarnvik.atom.model.atom.stikhelper;

import lombok.Getter;

public enum MediaType {
  // AtomicParsley Types
  HOME_VIDEO(0x00, "Home Video"),
  NORMALVIDEO(0x01, "Normal"),
  AUDIOBOOK(0x02, "Audiobook"),
  BOOKMARK(0x05, "Whacked Bookmark"),
  MUSIC_VIDEO(0x06, "Music Video"),
  MOVIE(0x09, "Movie"),
  TV_SHOW(0x0A, "TV Show"),
  BOOKLET(0x0B, "Booklet"),
  UNKNOWN(0xFF, "Unknown");

  @Getter
  private int type;
  @Getter
  private String description;

  MediaType(int type, String description) {
    this.type = type;
    this.description = description;
  }

  public static MediaType from(long type) {
    for (MediaType mdty : values()) {
      if ((long) mdty.getType() == type) {
        return mdty;
      }
    }
    return UNKNOWN;
  }
}

// Chat GPT provided types
// match value {
//     1 => "Movie",
//     2 => "Music",
//     3 => "Audiobook",
//     4 => "Whacked Bookmark",
//     5 => "Podcast",
//     6 => "TV Show",
//     7 => "Booklet",
//     9 => "Ringtone",
//     10 => "Podcast Episode",
//     11 => "iTunes U Course",
//     12 => "Alert Tone",
//     14 => "iTunes U Course Metadata",
//     _ => "Unknown",
// }
