package com.github.jlagoscarrera.nadir.Scripts;

import com.github.jlagoscarrera.nadir.Components.Room;

/**
 * The level generator script.
 */
public class LevelGenerator {
    /**
     * Grid size
     */
    private int levelSize;
    /**
     * Rooms in the grid
     */
    private Room[][] rooms;
    /**
     * Starting room
     */
    private Room startRoom;
    /**
     * Ending room
     */
    private Room endRoom;

    /**
     * Gets rooms arrays.
     *
     * @return the rooms arrays.
     */
    public Room[][] getRooms() {
        return rooms;
    }

    /**
     * Gets start room.
     *
     * @return the start room
     */
    public Room getStartRoom() {
        return startRoom;
    }

    /**
     * Gets end room.
     *
     * @return the end room
     */
    public Room getEndRoom() {
        return endRoom;
    }

    /**
     * Gets level grid size.
     *
     * @return the level grid size
     */
    public int getLevelSize() {
        return levelSize;
    }

    /**
     * Sets level grid size.
     *
     * @param levelSize the level grid size
     */
    public void setLevelSize(int levelSize) {
        this.levelSize = levelSize;
    }

    /**
     * Instantiates a new level.
     *
     * @param levelSize the level size
     */
    public LevelGenerator(int levelSize) {
        this.levelSize = levelSize;
        rooms = new Room[levelSize][levelSize];
        initializeRooms();
        generateRoomType();
    }

    /**
     * Initializes all rooms empty
     */
    private void initializeRooms() {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                rooms[i][j] = new Room(0, (i * levelSize) + j, j, i);
            }
        }
    }


    /**
     * Show path for debug.
     */
    public void showPath() {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                System.out.print(rooms[i][j].getType() + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Generates solution path on the grid
     */
    private void generateRoomType() {
        int currentX, currentY, prevX, prevY;
        boolean weGoDown;

        //We generate where the spawn room will be on the top row.
        currentX = (int) (Math.random() * levelSize);
        prevX = currentX;
        currentY = 0;
        prevY = currentY;
        rooms[currentY][currentX].setType(1);
        startRoom = rooms[currentY][currentX];

        while (currentY < levelSize) {
            int random = (int) (Math.random() * 100);
            weGoDown = false;

            //showPath();
            //System.out.println("-------");

            //40% chances going left, 40% chances going right, 20% chances going down
            if (random>=0 && random<40){
                if (currentX > 0) {
                    if (rooms[currentY][currentX - 1].getType() == 0) {
                        currentX--;
                    }
                } else if (currentX < levelSize - 1) {
                    if (rooms[currentY][currentX + 1].getType() == 0) {
                        currentX++;
                    }
                } else {
                    weGoDown = true;
                }
            } else if (random>=40 && random < 80) {
                if (currentX < levelSize - 1) {
                    if (rooms[currentY][currentX + 1].getType() == 0) {
                        currentX++;
                    }
                } else if (currentX > 0) {
                    if (rooms[currentY][currentX - 1].getType() == 0) {
                        currentX--;
                    }
                } else {
                    weGoDown = true;
                }
            } else {
                weGoDown = true;
            }

            if (weGoDown) {
                currentY++;

                if (currentY < levelSize) {
                    rooms[prevY][prevX].setType(2);
                    rooms[currentY][currentX].setType(3);
                } else {
                    endRoom = rooms[currentY - 1][currentX];
                }
            } else {
                rooms[currentY][currentX].setType(1);
            }

            prevX = currentX;
            prevY = currentY;
        }
    }
}