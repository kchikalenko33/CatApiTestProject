package testData;

public enum Color {
    BLACK("BLACK"),
    WHITE("WHITE"),
    GRAY("GRAY"),
    ORANGE("ORANGE"),
    BROWN("BROWN"),
    CREAM("CREAM"),
    TABBY("TABBY"),
    CALICO("CALICO"),
    SPOTTED("SPOTTED"),
    MIXED("MIXED");

    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
