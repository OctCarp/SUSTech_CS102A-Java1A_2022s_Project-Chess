package chess;

import java.awt.*;

public enum BackColor {
    DEEP("Board Deep Color", new Color(130, 90, 70)),
    LIGHT("Board Light Color", new Color(210, 180, 155)),
    ATTACKED("Chess be Attacked",new Color(245,235,180)),
    ENTERED("Mouse Entered",new Color(200, 200, 15));
    private final String name;
    private final Color color;

    BackColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
