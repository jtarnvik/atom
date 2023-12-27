package com.tarnvik.atom.model;

@FunctionalInterface
interface AtomGenerator<A, B, R> {
  R apply(A a, B b,  AtomType at);
}