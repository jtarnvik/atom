package com.tarnvik.atom.model.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeConverter {
  public static final LocalDateTime EPOCH_1904 = LocalDateTime.of(1904, 1, 1, 0, 0, 0);
  public static final DateTimeFormatter LDT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public static LocalDateTime secondsSince1904ToLDT(long secs) {
    return EPOCH_1904.plusSeconds(secs);
  }
}
