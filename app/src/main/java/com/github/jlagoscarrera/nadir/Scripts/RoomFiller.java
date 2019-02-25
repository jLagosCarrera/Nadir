package com.github.jlagoscarrera.nadir.Scripts;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.github.jlagoscarrera.nadir.Components.Block;
import com.github.jlagoscarrera.nadir.Components.Room;

import java.util.ArrayList;

public class RoomFiller {
    ArrayList<String[]> roomType1 = new ArrayList<>();
    ArrayList<String[]> roomType2 = new ArrayList<>();
    ArrayList<String[]> roomType3 = new ArrayList<>();
    ArrayList<String[]> roomType0 = new ArrayList<>();

    public LevelGenerator level;

    public RoomFiller() {
        initializeRooms();
        level = new LevelGenerator(4);
        generateRooms();
    }

    public void initializeRooms() {
        //Type 1 rooms.
        roomType1.add(new String[]{
                "1111111111",
                "1000000001",
                "1111110001",
                "0000000000",
                "0000000000",
                "1000000001",
                "1001111001",
                "1111111111"
        });
        roomType1.add(new String[]{
                "1111111111",
                "1001111101",
                "0000000000",
                "0000000000",
                "1110000001",
                "1111000011",
                "1111000111",
                "1111111111"
        });
        roomType1.add(new String[]{
                "1111111111",
                "1000000001",
                "1011111101",
                "0000000000",
                "0000000000",
                "1111111111",
                "1111111111",
                "1111111111"
        });
        roomType1.add(new String[]{
                "1111111111",
                "1111110001",
                "1000000001",
                "0000110000",
                "0001111000",
                "1001111101",
                "1100000001",
                "1111111111"
        });
        roomType1.add(new String[]{
                "1111111111",
                "1110011111",
                "1100000111",
                "0000000000",
                "0000000000",
                "1110000011",
                "1111111111",
                "1111111111"
        });

        //Type 2 rooms
        roomType2.add(new String[]{
                "0000000000",
                "0000000000",
                "0000000000",
                "0011000000",
                "1110001110",
                "1110000001",
                "1111111001",
                "1111100011"
        });
        roomType2.add(new String[]{
                "0000111100",
                "0000000000",
                "1110001000",
                "0000001100",
                "0110001110",
                "1110000111",
                "1110000111",
                "1111100111"
        });
        roomType2.add(new String[]{
                "1111111111",
                "1000000001",
                "1000000111",
                "0011110000",
                "0000000000",
                "1000001111",
                "1110000111",
                "1111001111"
        });
        roomType2.add(new String[]{
                "1111111111",
                "1000000001",
                "1000000001",
                "0001111000",
                "0000000000",
                "1110000111",
                "1000000001",
                "1001111001"
        });
        roomType2.add(new String[]{
                "1111111111",
                "1111000001",
                "1100000001",
                "0000001000",
                "0000011100",
                "1001111111",
                "1000000001",
                "1110000111"
        });

        //Type 3 rooms
        roomType3.add(new String[]{
                "0000000000",
                "0000000111",
                "1100000011",
                "0000000000",
                "0110001110",
                "1100011111",
                "1111000001",
                "1111111111"
        });
        roomType3.add(new String[]{
                "0000000000",
                "1001111001",
                "1100000011",
                "0000000000",
                "0001111000",
                "1100000011",
                "1110000111",
                "1111111111"
        });
        roomType3.add(new String[]{
                "0000000000",
                "1110000001",
                "1111100011",
                "0000000000",
                "0000011000",
                "1100000011",
                "1111100111",
                "1111111111"
        });
        roomType3.add(new String[]{
                "1110000000",
                "1000001100",
                "1100011111",
                "0000111000",
                "0001111000",
                "1100000011",
                "1110000111",
                "1111111111"
        });
        roomType3.add(new String[]{
                "0000000000",
                "1111101111",
                "1100000011",
                "000001100",
                "0000111000",
                "1100000011",
                "1111100111",
                "1111111111"
        });

        //Type 0 rooms
        roomType0.add(new String[]{
                "1111111111",
                "1111111111",
                "1111111111",
                "1111111111",
                "1111111111",
                "1111111111",
                "1111111111",
                "1111111111"
        });
        roomType0.add(new String[]{
                "1111111111",
                "1111111111",
                "1110000111",
                "1110000111",
                "1110000111",
                "1110000111",
                "1111111111",
                "1111111111"
        });
    }

    public void generateRooms() {
        for (int a = 0; a < level.getRooms().length; a++) {
            for (int b = 0; b < level.getRooms()[a].length; b++) {
                Room r = level.getRooms()[a][b];
                String[] template;
                int x, y;
                x = r.getX();
                y = r.getY();

                if (r.getType() == 1) {
                    template = roomType1.get((int) (Math.random() * roomType1.size()));
                } else if (r.getType() == 2) {
                    template = roomType2.get((int) (Math.random() * roomType2.size()));
                } else if (r.getType() == 3) {
                    template = roomType3.get((int) (Math.random() * roomType3.size()));
                } else {
                    template = roomType0.get((int) (Math.random() * roomType0.size()));
                }

                r.setBlocks(new Block[template.length][template[0].length()]);
                for (int i = 0; i < template.length; i++) {
                    for (int j = 0; j < template[i].length(); j++) {
                        Block block= null ;
                        char blockChar = template[i].charAt(j);
                        switch (blockChar) {
                            case '0':
                                block = new Block(null, false);
                                break;
                            case '1':
                                block = new Block(null, true);
                                break;
                        }
                        Block[][] aux = r.getBlocks();
                        aux[i][j] = block;
                        r.setBlocks(aux);
                    }
                }
            }
        }
    }
}