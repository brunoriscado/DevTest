package com.example.model;

/**
 *
 * Created by will on 12/09/14.
 */
public enum EyeColour {
    BROWN("brown"), GREEN("green"), BLUE("blue");

    private String colour;

    private EyeColour(String colour) {
        this.setColour(colour);
    }

    public static EyeColour getEyeColourFromName(String colour) {
        EyeColour result = null;
        for (EyeColour eyeColour : EyeColour.values()) {
            if (eyeColour.getColour().equalsIgnoreCase(colour)) {
                result = eyeColour;
            }
        }
        return result;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
