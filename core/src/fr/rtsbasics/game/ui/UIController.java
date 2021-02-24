package fr.rtsbasics.game.ui;

import com.badlogic.gdx.math.Vector2;

public class UIController {
    private static int id = 0;
    private int width;
    private int height;
    private Vector2 position;
    private String name;

    public UIController(String pName, Vector2 pPosition, int pWidth, int pHeight)
    {
        UIController.id++;
        this.name = pName;
        this.width = pWidth;
        this.height = pHeight;
        this.position = pPosition;
    }

    public boolean isHover(Vector2 e)
    {
        float unitX = this.getPosition().x;
        float unitY = this.getPosition().y;

        return e.x >= unitX && e.x <= (unitX + this.width)
                && e.y >= unitY && e.y <= (unitY + this.height);
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UIController.id = id;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
