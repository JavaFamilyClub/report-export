package club.javafamily.assembly;

import club.javafamily.lens.TableLens;
import club.javafamily.style.StyleLayout;

import java.awt.*;

public interface Assembly {

    Point getPosition();

    StyleLayout getStyleLayout();

    TableLens getTableLens();

    String getId();

    String getTitle();

}
