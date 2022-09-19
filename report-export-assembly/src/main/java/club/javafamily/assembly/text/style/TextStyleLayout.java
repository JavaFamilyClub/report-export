package club.javafamily.assembly.text.style;

import club.javafamily.style.AbstractStyleLayout;

import java.awt.*;

public class TextStyleLayout extends AbstractStyleLayout {

    private static final int INDEX = 0;

    public void setTextColor(Color color) {
        setFontColor(INDEX, INDEX, color);
    }

    public Color getTextColor() {
        return getFontColor(INDEX, INDEX);
    }

    public Font getTextFont() {
        return getFont(INDEX, INDEX);
    }

    public void setTextFont(Font font) {
        setFont(INDEX, INDEX, font);
    }
}
