package club.javafamily.assembly.text;

import club.javafamily.assembly.AbstractAssembly;
import club.javafamily.assembly.text.lens.TextTableLens;
import club.javafamily.assembly.text.style.TextStyleLayout;

public class TextAssembly extends AbstractAssembly <TextStyleLayout> {

    public TextAssembly() {
        setLens(new TextTableLens());
        setStyleLayout(new TextStyleLayout());
    }

    public TextAssembly(String text) {
        setLens(new TextTableLens(text));
        setStyleLayout(new TextStyleLayout());
    }

    public TextTableLens getTextTableLens() {
        return (TextTableLens) super.getTableLens();
    }
}
