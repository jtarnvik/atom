package com.tarnvik.atom.model.operations;

import com.tarnvik.atom.model.Atom;
import com.tarnvik.atom.model.atom.DATAAtom;
import com.tarnvik.atom.model.atom.SubAtoms;
import com.tarnvik.atom.model.atom.datahelper.DataAtomTypeIndicator;
import com.tarnvik.atom.model.atom.datahelper.PayloadParser;

import java.util.EnumSet;
import java.util.Set;

public class GetSingleTextChild implements AtomOperation<String> {
  private final static EnumSet<DataAtomTypeIndicator> supportedTypes = EnumSet.of(DataAtomTypeIndicator.TYPE_IND_01);

  @Override
  public String apply(Atom atom) {
    if (atom instanceof SubAtoms subAtoms) {
      if (subAtoms.getSubAtoms().size() != 1) {
        throw new IllegalStateException("Unexpected number of subatoms, expcted 1 found: " + subAtoms.getSubAtoms().size());
      }
      Atom child = subAtoms.getSubAtoms().getFirst();
      if ((child instanceof DATAAtom dataAtomChild)) {
        DATAAtom.Parsed parsed = dataAtomChild.getParsed();
        DataAtomTypeIndicator ind = DataAtomTypeIndicator.from(parsed.getTypeIndicator());
        if (!supportedTypes.contains(ind)) {
          throw new IllegalStateException("Unsupported data type, type: " + ind.getIndicatorType());
        }
        PayloadParser payloadParser = ind.generatePayloadParser(parsed);
        if (!payloadParser.supportsStringRead()) {
          throw new IllegalStateException("DataAtom does not support string extraction, type: " + ind.getIndicatorType());
        }
        return payloadParser.toString();
      } else {
        throw new IllegalStateException("Not a data atom child, existing child type: " + child.getClass());
      }
    } else {
      throw new IllegalStateException("Atom is not of SubAtoms type. No children present, type: " + atom.getClass());
    }
  }
}
