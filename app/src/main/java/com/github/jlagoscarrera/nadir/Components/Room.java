package com.github.jlagoscarrera.nadir.Components;

public class Room {

    private int type;
    private int id;
    private int x;
    private int y;

    public Room(int type, int id, int x, int y) {
        this.type = type;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
