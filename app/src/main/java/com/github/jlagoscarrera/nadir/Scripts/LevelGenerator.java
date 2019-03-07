package com.github.jlagoscarrera.nadir.Scripts;

import com.github.jlagoscarrera.nadir.Components.Room;

public class LevelGenerator {
    private int levelSize;
    private Room[][] rooms;
    private Room startRoom;
    private Room endRoom;

    public Room[][] getRooms() {
        return rooms;
    }

    public Room getStartRoom() {
        return startRoom;
    }

    public Room getEndRoom() {
        return endRoom;
    }

    public int getLevelSize() {
        return levelSize;
    }

    public void setLevelSize(int levelSize) {
        this.levelSize = levelSize;
    }

    public LevelGenerator(int levelSize) {
        this.levelSize = levelSize;
        rooms = new Room[levelSize][levelSize];
        initializeRooms();
        generateRoomType();
    }

    /**
     * Initializes all rooms empty.
     */
    private void initializeRooms() {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                rooms[i][j] = new Room(0, (i * levelSize) + j, j, i);
            }
        }
    }


    public void showPath() {
        for (int i = 0; i < rooms.length; i++) {
            for (int j = 0; j < rooms[i].length; j++) {
                System.out.print(rooms[i][j].getType() + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Generates the solution path.
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
            switch (random) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
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
                    break;
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                case 50:
                case 51:
                case 52:
                case 53:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 59:
                case 60:
                case 61:
                case 62:
                case 63:
                case 64:
                case 65:
                case 66:
                case 67:
                case 68:
                case 69:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 75:
                case 76:
                case 77:
                case 78:
                case 79:
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
                    break;
                default:
                    weGoDown = true;
                    break;
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