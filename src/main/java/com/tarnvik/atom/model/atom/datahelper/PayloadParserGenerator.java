package com.tarnvik.atom.model.atom.datahelper;

import com.tarnvik.atom.model.atom.DATAAtom;

@FunctionalInterface
public interface PayloadParserGenerator {
  PayloadParser apply(DATAAtom.Parsed parsed);
}


