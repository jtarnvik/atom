package com.tarnvik.atom.model.atom;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.AtomType;
import com.tarnvik.atom.model.atom.hdlrhelper.HandlerType;
import com.tarnvik.atom.model.atom.parts.VersionFlag;
import com.tarnvik.atom.model.parsedatom.ParsedAtom;
import com.tarnvik.atom.model.parsedatom.VersionedParsedAtom;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.nio.ByteBuffer;

import static com.tarnvik.atom.model.atom.parts.CommonAtomParts.parseVerionAndFlags;
import static com.tarnvik.atom.model.converter.TypeConverter.convertUTF8ToString;
import static com.tarnvik.atom.model.converter.TypeConverter.removeTerminatingZeroByte;

@EqualsAndHashCode(callSuper = true)
@Getter
public class HDLRAtom extends Atom {
  @Data
  @EqualsAndHashCode(callSuper = true)
  public static class Parsed extends VersionedParsedAtom {
    private String handlerType;
    private HandlerType type;
    private String name;
  }

  public HDLRAtom(long position, ByteBuffer sizeAndType, AtomType atomType, Atom parent) {
    super(position, sizeAndType, atomType, parent);
  }

  @Override
  public ParsedAtom parseData() {
    data.rewind();
    Parsed result = new Parsed();
    VersionFlag versionFlag = parseVerionAndFlags(data);
    result.setVersion(versionFlag.getVersion());
    result.setFlags(versionFlag.getFlags());
    // Skip the 4 predefined bytes
    data.position(data.position() + 4) ;
    byte[] chTmp = new byte[4];
    data.get(chTmp);
    String handlerType = convertUTF8ToString(chTmp);
    result.setHandlerType(handlerType);
    result.setType(HandlerType.from(handlerType));

    // Skip the 3 reserved ints
    data.position(data.position() + 3*4) ;
    chTmp = new byte[data.remaining()];
    data.get(chTmp);
    chTmp = removeTerminatingZeroByte(chTmp);
    result.setName(convertUTF8ToString(chTmp));

    return result;
  }

  @Override
  protected String toStringChild(int indentLevel) {
    Parsed parsed = (Parsed) parseData();

    StringBuilder str = new StringBuilder();
    str.repeat(" ", indentLevel);
    str.append("Parsed: ");
    str.append(toStringVersioned(parsed));
    if (parsed.getType() == HandlerType.UNKNOWN) {
      // TODO: log and add new type
      str.append(" Handler Type: ");
      str.append(parsed.handlerType);
      str.append(" Handler Name: ");
      str.append(parsed.name);
    } else {
      str.append(" Handler Type: ");
      str.append(parsed.getType().getType());
      str.append(" Handler Descriprion: ");
      str.append(parsed.getType().getDescription());
      str.append(" Handler Name: ");
      str.append(parsed.name);
    }
    return str.toString();
  }
}
