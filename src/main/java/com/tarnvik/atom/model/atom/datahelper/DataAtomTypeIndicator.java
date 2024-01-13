package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;
import lombok.Getter;

public enum DataAtomTypeIndicator {
  // @formatter:off
  TYPE_IND_00( 0, TypeXX::new, "reserved",                   "Reserved for use where no type needs to be indicated" ),
  TYPE_IND_01( 1, Type01::new, "UTF-8",                      "Without any count or NULL terminator" ),
  TYPE_IND_02( 2, TypeXX::new, "UTF-16",                     "Also known as UTF-16BE" ),
  TYPE_IND_03( 3, TypeXX::new, "S/JIS",                      "Deprecated unless it is needed for special Japanese characters" ),
  TYPE_IND_04( 4, TypeXX::new, "UTF-8 sort",                 "Variant storage of a string for sorting only" ),
  TYPE_IND_05( 5, TypeXX::new, "UTF-16 sort",                "Variant storage of a string for sorting only" ),
  TYPE_IND_13(13, Type13::new, "JPEG",                       "In a JFIF wrapper" ),
  TYPE_IND_14(14, Type14::new, "PNG",                        "In a PNG wrapper" ),
  TYPE_IND_21(21, Type21::new, "BE Signed Integer",          "A big-endian signed integer in 1,2,3 or 4 bytes. Note: This data type is not supported in Timed Metadata Media. Use one of the fixed-size signed integer data types (that is, type codes 65, 66, or 67) instead." ),
  TYPE_IND_22(22, TypeXX::new, "BE Unsigned Integer",        "A big-endian unsigned integer in 1,2,3 or 4 bytes; size of value determines integer size. Note: This data type is not supported in Timed Metadata Media. Use one of the fixed-size unsigned integer data types (that is, type codes 75, 76, or 77) instead." ),
  TYPE_IND_23(23, TypeXX::new, "BE Float32",                 "A big-endian 32-bit floating point value (IEEE754)" ),
  TYPE_IND_24(24, TypeXX::new, "BE Float64",                 "A big-endian 64-bit floating point value (IEEE754)" ),
  TYPE_IND_27(27, TypeXX::new, "BMP",                        "Windows bitmap format graphics" ),
  TYPE_IND_28(28, TypeXX::new, "QuickTime Metadata atom",    "A block of data having the structure of the Metadata atom defined in this specification" ),
  TYPE_IND_65(65, TypeXX::new, "8-bit Signed Integer",       "An 8-bit signed integer" ),
  TYPE_IND_66(66, TypeXX::new, "BE 16-bit Signed Integer",   "A big-endian 16-bit signed integer" ),
  TYPE_IND_67(67, TypeXX::new, "BE 32-bit Signed Integer",   "A big-endian 32-bit signed integer" ),
  TYPE_IND_70(70, TypeXX::new, "BE PointF32",                "A block of data representing a two dimensional (2D) point with 32-bit big-endian floating point x and y coordinates. It has the structure: struct { BEFloat32 x; BEFloat32 y; }" ),
  TYPE_IND_71(71, TypeXX::new, "BE DimensionsF32",           "A block of data representing 2D dimensions with 32-bit big-endian floating point width and height. It has the structure: struct { BEFloat32 width; BEFloat32 height; }" ),
  TYPE_IND_72(72, TypeXX::new, "BE RectF32",                 "A block of data representing a 2D rectangle with 32-bit big-endian floating point x and y coordinates and a 32-bit big-endian floating point width and height size. It has the structure: struct { BEFloat32 x; BEFloat32 y; BEFloat32 width; BEFloat32 height;} or the equivalent structure: struct { PointF32 origin; DimensionsF32 size; }" ),
  TYPE_IND_74(74, TypeXX::new, "BE 64-bit Signed Integer",   "A big-endian 64-bit signed integer" ),
  TYPE_IND_75(75, TypeXX::new, "8-bit Unsigned Integer",     "An 8-bit unsigned integer" ),
  TYPE_IND_76(76, TypeXX::new, "BE 16-bit Unsigned Integer", "A big-endian 16-bit unsigned integer" ),
  TYPE_IND_77(77, TypeXX::new, "BE 32-bit Unsigned Integer", "A big-endian 32-bit unsigned integer" ),
  TYPE_IND_78(78, TypeXX::new, "BE 64-bit Unsigned Integer", "A big-endian 64-bit unsigned integer" ),
  TYPE_IND_79(79, TypeXX::new, "AffineTransformF64",         "A block of data representing a 3x3 transformation matrix. It has the structure: struct { BEFloat64 matrix[3][3]; }" );
  // @formatter:on

  @Getter
  private final int id;
  private final PayloadParserGenerator payloadParserGenerator;
  @Getter
  private final String indicatorType;
  @Getter
  private final String comment;

  DataAtomTypeIndicator(int id, PayloadParserGenerator generator, String indicatorType, String comment) {
    this.id = id;
    this.payloadParserGenerator = generator;
    this.indicatorType = indicatorType;
    this.comment = comment;
  }

  public PayloadParser generatePayloadParser(DATAAtom.Parsed parsed) {
    return payloadParserGenerator.apply(parsed);
  }

  public static DataAtomTypeIndicator from(int id) {
    for (DataAtomTypeIndicator ind : values()) {
      if (ind.getId() == id) {
        return ind;
      }
    }
    throw new IllegalArgumentException("Unknown data atom type indicator, id: " + id);
  }
}
