package dev.turtywurty.copperboats.util;

import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class Quadruple<A, B, C, D> implements Comparable<Quadruple<A, B, C, D>>, Serializable {
    private final Pair<A, B> pair;
    private final Pair<C, D> pair1;

    private Quadruple(A a, B b, C c, D d) {
        this.pair = Pair.of(a, b);
        this.pair1 = Pair.of(c, d);
    }

    public static <A, B, C, D> Quadruple<A, B, C, D> of(A a, B b, C c, D d) {
        return new Quadruple<>(a, b, c, d);
    }

    @Override
    public int compareTo(@NotNull Quadruple<A, B, C, D> other) {
        return this.pair.compareTo(other.pair) + this.pair1.compareTo(other.pair1);
    }

    public A getFirst() {
        return this.pair.getLeft();
    }

    public B getSecond() {
        return this.pair.getRight();
    }

    public C getThird() {
        return this.pair1.getLeft();
    }

    public D getFourth() {
        return this.pair1.getRight();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quadruple<?, ?, ?, ?> quadruple = (Quadruple<?, ?, ?, ?>) obj;
        return this.pair.equals(quadruple.pair) && this.pair1.equals(quadruple.pair1);
    }

    @Override
    public int hashCode() {
        return this.pair.hashCode() + this.pair1.hashCode();
    }

    @Override
    public String toString() {
        return "Quadruple{" +
                "first=" + this.pair.getLeft() +
                ", second=" + this.pair.getRight() +
                ", third=" + this.pair1.getLeft() +
                ", fourth=" + this.pair1.getRight() +
                '}';
    }

    public Pair<A, B> getFirstPair() {
        return this.pair;
    }

    public Pair<C, D> getSecondPair() {
        return this.pair1;
    }
}
