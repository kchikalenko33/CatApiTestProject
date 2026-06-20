package testData;

public enum Color {
 /*   BLACK,
    WHITE,
    GRAY,
    ORANGE,
    BROWN,
    CREAM, */
    TABBY("TABBY");
    /*CALICO,
    SPOTTED,
    MIXED*/

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
