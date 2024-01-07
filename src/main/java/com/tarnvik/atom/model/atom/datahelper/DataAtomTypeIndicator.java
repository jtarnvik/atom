package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DataAtomTypeIndicator {
  private int id;
  private DataAtomTypeGenerator typeGenerator;
  private String indicatorType;
  private String comment;

  // @formatter:off
  public static DataAtomTypeIndicator from(DATAAtom.Parsed atom) {
    int id = atom.getTypeIndicator();
    return switch (id) {
      case 0  -> new DataAtomTypeIndicator(id, new TypeXX(atom), "reserved",                   "Reserved for use where no type needs to be indicated" );
      case 1  -> new DataAtomTypeIndicator(id, new Type01(atom), "UTF-8",                      "Without any count or NULL terminator" );
      case 2  -> new DataAtomTypeIndicator(id, new TypeXX(atom), "UTF-16",                     "Also known as UTF-16BE" );
      case 3  -> new DataAtomTypeIndicator(id, new TypeXX(atom), "S/JIS",                      "Deprecated unless it is needed for special Japanese characters" );
      case 4  -> new DataAtomTypeIndicator(id, new TypeXX(atom), "UTF-8 sort",                 "Variant storage of a string for sorting only" );
      case 5  -> new DataAtomTypeIndicator(id, new TypeXX(atom), "UTF-16 sort",                "Variant storage of a string for sorting only" );
      case 13 -> new DataAtomTypeIndicator(id, new Type13(atom), "JPEG",                       "In a JFIF wrapper" );
      case 14 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "PNG",                        "In a PNG wrapper" );
      case 21 -> new DataAtomTypeIndicator(id, new Type21(atom), "BE Signed Integer",          "A big-endian signed integer in 1,2,3 or 4 bytes. Note: This data type is not supported in Timed Metadata Media. Use one of the fixed-size signed integer data types (that is, type codes 65, 66, or 67) instead." );
      case 22 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE Unsigned Integer",        "A big-endian unsigned integer in 1,2,3 or 4 bytes; size of value determines integer size. Note: This data type is not supported in Timed Metadata Media. Use one of the fixed-size unsigned integer data types (that is, type codes 75, 76, or 77) instead." );
      case 23 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE Float32",                 "A big-endian 32-bit floating point value (IEEE754)" );
      case 24 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE Float64",                 "A big-endian 64-bit floating point value (IEEE754)" );
      case 27 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BMP",                        "Windows bitmap format graphics" );
      case 28 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "QuickTime Metadata atom",    "A block of data having the structure of the Metadata atom defined in this specification" );
      case 65 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "8-bit Signed Integer",       "An 8-bit signed integer" );
      case 66 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE 16-bit Signed Integer",   "A big-endian 16-bit signed integer" );
      case 67 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE 32-bit Signed Integer",   "A big-endian 32-bit signed integer" );
      case 70 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE PointF32",                "A block of data representing a two dimensional (2D) point with 32-bit big-endian floating point x and y coordinates. It has the structure: struct { BEFloat32 x; BEFloat32 y; }" );
      case 71 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE DimensionsF32",           "A block of data representing 2D dimensions with 32-bit big-endian floating point width and height. It has the structure: struct { BEFloat32 width; BEFloat32 height; }" );
      case 72 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE RectF32",                 "A block of data representing a 2D rectangle with 32-bit big-endian floating point x and y coordinates and a 32-bit big-endian floating point width and height size. It has the structure: struct { BEFloat32 x; BEFloat32 y; BEFloat32 width; BEFloat32 height;} or the equivalent structure: struct { PointF32 origin; DimensionsF32 size; }" );
      case 74 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE 64-bit Signed Integer",   "A big-endian 64-bit signed integer" );
      case 75 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "8-bit Unsigned Integer",     "An 8-bit unsigned integer" );
      case 76 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE 16-bit Unsigned Integer", "A big-endian 16-bit unsigned integer" );
      case 77 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE 32-bit Unsigned Integer", "A big-endian 32-bit unsigned integer" );
      case 78 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "BE 64-bit Unsigned Integer", "A big-endian 64-bit unsigned integer" );
      case 79 -> new DataAtomTypeIndicator(id, new TypeXX(atom), "AffineTransformF64",         "A block of data representing a 3x3 transformation matrix. It has the structure: struct { BEFloat64 matrix[3][3]; }" );
      default -> throw new IllegalArgumentException();
    };
  }
  // @formatter:on
}
