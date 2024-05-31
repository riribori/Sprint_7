package org.example;

public enum Color {

    BLACK("чёрный жемчуг", "BLACK"),
    GRAY("серая безысходность", "GRAY");

    private final String description;
    private final String colorName;

    Color(String description,String colorName ) {
        this.description = description;
        this.colorName = colorName;
    }

    public String getColorName () {
        return colorName;
    }

}
