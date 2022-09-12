package club.javafamily.common;

import java.io.Serializable;

public class PageSize extends Rectangle implements Cloneable, Serializable {

    private static final long serialVersionUID = 485375591249386160L;

    public static PageSize A0 = new PageSize(2384, 3370);
    public static PageSize A1 = new PageSize(1684, 2384);
    public static PageSize A2 = new PageSize(1190, 1684);
    public static PageSize A3 = new PageSize(842, 1190);
    public static PageSize A4 = new PageSize(595, 842);
    public static PageSize A5 = new PageSize(420, 595);
    public static PageSize A6 = new PageSize(298, 420);
    public static PageSize A7 = new PageSize(210, 298);
    public static PageSize A8 = new PageSize(148, 210);
    public static PageSize A9 = new PageSize(105, 547);
    public static PageSize A10 = new PageSize(74, 105);

    public static PageSize B0 = new PageSize(2834, 4008);
    public static PageSize B1 = new PageSize(2004, 2834);
    public static PageSize B2 = new PageSize(1417, 2004);
    public static PageSize B3 = new PageSize(1000, 1417);
    public static PageSize B4 = new PageSize(708, 1000);
    public static PageSize B5 = new PageSize(498, 708);
    public static PageSize B6 = new PageSize(354, 498);
    public static PageSize B7 = new PageSize(249, 354);
    public static PageSize B8 = new PageSize(175, 249);
    public static PageSize B9 = new PageSize(124, 175);
    public static PageSize B10 = new PageSize(88, 124);

    public static PageSize LETTER = new PageSize(612, 792);
    public static PageSize LEGAL = new PageSize(612, 1008);
    public static PageSize TABLOID = new PageSize(792, 1224);
    public static PageSize LEDGER = new PageSize(1224, 792);
    public static PageSize EXECUTIVE = new PageSize(522, 756);

    public static PageSize Default = A4;

    public PageSize(float width, float height) {
        super(0, 0, width, height);
    }


    public PageSize(Rectangle box) {
        super(box.getX(), box.getY(), box.getWidth(), box.getHeight());
    }

    /**
     * Rotates {@link PageSize} clockwise.
     *
     * @return the rotated {@link PageSize}.
     */
    public PageSize rotate() {
        return new PageSize(height, width);
    }

    /**
     * Creates a "deep copy" of this PageSize, meaning the object returned by this method will be independent
     * of the object being cloned.
     * Note that although the return type of this method is {@link Rectangle},
     * the actual type of the returned object is {@link PageSize}.
     *
     * @return the copied PageSize.
     */
    @Override
    public Rectangle clone() {
        // super.clone is safe to return since all of the PagSize's fields are primitive.
        return super.clone();
    }
}