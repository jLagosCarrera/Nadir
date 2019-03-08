package com.github.jlagoscarrera.nadir.Components;

/**
 * The Room class.
 */
public class Room {

    /**
     * Blocks.
     */
    public Block[][] blocks;
    /**
     * Type of room
     */
    private int type;
    /**
     * Id of the room
     */
    private int id;
    /**
     * X position on the grid
     */
    private int x;
    /**
     * Y position on the grid
     */
    private int y;

    /**
     * Instantiates a new Room.
     *
     * @param type the type of room
     * @param id   the id of the room
     * @param x    the x on the grid
     * @param y    the y on the grid
     */
    public Room(int type, int id, int x, int y) {
        this.type = type;
        this.id = id;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets room type.
     *
     * @return the type of the room
     */
    public int getType() {
        return type;
    }

    /**
     * Sets room type.
     *
     * @param type the type of the room
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Gets room id.
     *
     * @return the room id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets room id.
     *
     * @param id the room id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets room x on the grid.
     *
     * @return the room x position on the grid
     */
    public int getX() {
        return x;
    }

    /**
     * Sets room x on the grid.
     *
     * @param x the room x on the grid
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets room y on the grid.
     *
     * @return the room y on the grid
     */
    public int getY() {
        return y;
    }

    /**
     * Sets room y on the grid.
     *
     * @param y the room y on the grid
     */
    public void setY(int y) {
        this.y = y;
    }
}
