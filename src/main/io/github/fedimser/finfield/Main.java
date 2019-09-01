package io.github.fedimser.finfield;

public class Main {

    public static void main(String[] args) {
	    FiniteField field = FiniteField.create(27);
        System.out.println(field.describe());
    }
}
