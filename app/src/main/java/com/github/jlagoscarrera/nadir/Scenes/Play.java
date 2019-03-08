package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;

import com.github.jlagoscarrera.nadir.Components.Block;
import com.github.jlagoscarrera.nadir.Components.MenuButton;
import com.github.jlagoscarrera.nadir.Components.Player;
import com.github.jlagoscarrera.nadir.Core.NadirEngine;
import com.github.jlagoscarrera.nadir.Scripts.RoomFiller;
import com.github.jlagoscarrera.nadirGame.R;

/**
 * The type Play.
 */
public class Play extends Scene implements SensorEventListener {
    /**
     * The Sensor manager.
     */
    SensorManager sensorManager;
    /**
     * The Acelerometro.
     */
    Sensor acelerometro;
    Sensor proximidad;
    /**
     * The R.
     */
    public RoomFiller r;
    /**
     * The Btn back.
     */
    MenuButton btnBack;
    /**
     * The Btn left.
     */
    MenuButton btnLeft,
    /**
     * The Btn right.
     */
    btnRight;
    /**
     * The Btn jump.
     */
    MenuButton btnJump;
    /**
     * The Block heigth.
     */
    public int blockHeigth,
    /**
     * The Block width.
     */
    blockWidth;
    /**
     * The Block x.
     */
    int blockX,
    /**
     * The Block y.
     */
    blockY;
    /**
     * The Player.
     */
    Player player;
    /**
     * The Moving left.
     */
    boolean movingLeft,
    /**
     * The Moving right.
     */
    movingRight,
    /**
     * The Player is on end.
     */
    playerIsOnEnd;
    /**
     * The Jumping.
     */
    public boolean jumping,
    /**
     * The Falling.
     */
    falling;
    /**
     * The End room.
     */
    Rect endRoom;
    /**
     * The End paint.
     */
    Paint endPaint;

    Bitmap currentBackground;
    Bitmap nightBackground;
    Bitmap dayBackground;

    Bitmap currentBlock;
    Bitmap nightBlock;
    Bitmap dayBlock;

    NadirEngine gameReference;

    /**
     * Instantiates a new Play.
     *
     * @param gameReference the game reference
     * @param sceneId       the scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Play(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        this.gameReference = gameReference;

        Bitmap aux;
        aux = BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.underwaternight);
        aux = Bitmap.createScaledBitmap(aux, screenWidth, screenHeight, true);
        nightBackground = aux;
        aux = BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.underwaterday);
        aux = Bitmap.createScaledBitmap(aux, screenWidth, screenHeight, true);
        dayBackground = aux;
        currentBackground = dayBackground;

        aux = BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.blocknight);
        nightBlock = aux;
        aux = BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.block);
        dayBlock = aux;
        currentBlock = dayBlock;

        blockHeigth = screenHeight / 8;
        blockWidth = blockHeigth;
        blockX = 0;
        blockY = 0;

        //Btn Back
        btnBack = new MenuButton((screenWidth / 24) * 23, 0, screenWidth, (screenHeight / 12));
        btnBack.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.backarrow));

        //Control Buttons
        btnLeft = new MenuButton((screenWidth / 24), (screenHeight / 12) * 9,
                (screenWidth / 24) * 3, (screenHeight / 12) * 11);
        btnLeft.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.leftarrow));
        btnRight = new MenuButton((screenWidth / 24) * 4, (screenHeight / 12) * 9,
                (screenWidth / 24) * 6, (screenHeight / 12) * 11);
        btnRight.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.rightarrow));
        btnJump = new MenuButton((screenWidth / 24) * 21, (screenHeight / 12) * 9,
                (screenWidth / 24) * 23, (screenHeight / 12) * 11);
        btnJump.setIcon(BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.jumparrow));

        Rect cameraHandler = new Rect((screenWidth / 12) * 4, (screenHeight / 6) * 2,
                (screenWidth / 12) * 8, (screenHeight / 6) * 4);

        Rect auxRect;
        Object[] intersect;
        do {
            r = new RoomFiller(gameReference);
            if (r.isGenerated) {
                Rect blockRect;
                for (int i = 0; i < r.level.getRooms().length; i++) {
                    for (int j = 0; j < r.level.getRooms()[i].length; j++) {
                        blockY = i * blockHeigth * 8;
                        for (int k = 0; k < r.level.getRooms()[i][j].blocks.length; k++) {
                            blockX = j * blockWidth * 10;
                            for (int l = 0; l < r.level.getRooms()[i][j].blocks[k].length; l++) {
                                blockRect = new Rect(blockX, blockY, blockX + blockWidth, blockY + blockHeigth);

                                if (r.level.getRooms()[i][j].blocks[k][l] == null) {
                                    r.level.getRooms()[i][j].blocks[k][l] = new Block(null, false, '0');
                                } else {
                                    if (r.level.getRooms()[i][j].blocks[k][l].isCollisionable()) {
                                        r.level.getRooms()[i][j].blocks[k][l].setTile(currentBlock);
                                    }
                                }

                                r.level.getRooms()[i][j].blocks[k][l].setBlockRect(blockRect);
                                blockX += blockWidth;
                            }
                            blockY += blockHeigth;
                        }
                    }
                }
            }


            player = new Player(6, blockHeigth / 8,
                    (r.level.getStartRoom().getX() * blockWidth * 10) + ((screenWidth / 12) * 4) + ((cameraHandler.width() / 2) - (blockWidth / 2)) + 5,
                    ((screenHeight / 6) * 2) + (cameraHandler.height() - blockHeigth) + 5,
                    BitmapFactory.decodeResource(gameReference.getResources(), R.mipmap.player_run_1),
                    new Rect(cameraHandler.centerX() - (blockWidth / 2) + 5,
                            cameraHandler.bottom - blockHeigth + 5,
                            cameraHandler.centerX() + (blockWidth / 2) - 5,
                            cameraHandler.bottom - 5),
                    cameraHandler, this);

            auxRect = new Rect(player.getX(), player.getY(),
                    player.getX() + blockWidth - 5, player.getY() + blockHeigth - 5);
            intersect = player.checkIntersect(auxRect);
        } while ((boolean) intersect[0]);

        jumping = false;
        falling = true;

        endRoom = new Rect(r.level.getEndRoom().getX() * blockWidth * 10, r.level.getEndRoom().getY() * blockHeigth * 8,
                r.level.getEndRoom().getX() * blockWidth * 10 + (blockWidth * 10), r.level.getEndRoom().getY() * blockHeigth * 8 + (blockHeigth * 8));
        endPaint = new Paint();
        endPaint.setColor(Color.GREEN);
        endPaint.setStyle(Paint.Style.STROKE);
        endPaint.setStrokeWidth(20);
        sensorManager = (SensorManager) NadirEngine.activity.getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
        proximidad = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, proximidad, SensorManager.SENSOR_DELAY_NORMAL);

    }

    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();        //Obtain action index.
        int pointerID = event.getPointerId(pointerIndex); //Obtain id of the pointer asociated to the action.
        int action = event.getActionMasked();             //Obtain type of pulsation.
        switch (action) {
            case MotionEvent.ACTION_DOWN:           //First finger touches.
            case MotionEvent.ACTION_POINTER_DOWN:   //Second and next touches.
                if (isTouched(btnJump.getButton(), event) && !jumping && !falling) {
                    jumping = true;
                    falling = false;
                }
                if (isTouched(btnLeft.getButton(), event)) {
                    movingLeft = true;
                    movingRight = false;
                }
                if (isTouched(btnRight.getButton(), event)) {
                    movingRight = true;
                    movingLeft = false;
                }
                break;
            case MotionEvent.ACTION_UP:             //Last finger up.
            case MotionEvent.ACTION_POINTER_UP:     //Any finger that isnt the last up.
                if (isTouched(btnBack.getButton(), event) && sceneId != 0) return 0;
                if (isTouched(btnLeft.getButton(), event)) {
                    movingLeft = false;
                }
                if (isTouched(btnRight.getButton(), event)) {
                    movingRight = false;
                }
                break;
            case MotionEvent.ACTION_MOVE: //Any finger is moved.
                break;
            default:
                Log.i("Any other action", "Action is not defined: " + action);
        }

        return sceneId;
    }

    int soundID;
    boolean alreadyPlaying = false;

    //We refresh game physics on screen.
    public void refreshPhysics() {
        player.aplicarGravedad();

        if (jumping) {
            gameReference.effects.play(gameReference.jumpSound,
                    gameReference.volume, gameReference.volume,
                    1, 0, 1);
            player.jump();
        }
        if (movingLeft) {
            player.moveLeft();
        }
        if (movingRight) {
            player.moveRight();
        }
        if (movingLeft || movingRight) {
            if (!alreadyPlaying) {
                soundID = gameReference.effects.play(gameReference.moveSound,
                        gameReference.volume, gameReference.volume,
                        1, -1, 1);
                alreadyPlaying = true;
            }
        } else {
            gameReference.effects.stop(soundID);
            alreadyPlaying = false;
        }

        if (endRoom.contains(player.getX(), player.getY())) {
            playerIsOnEnd = true;
        } else {
            playerIsOnEnd = false;
        }
    }

    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            c.drawBitmap(currentBackground, 0, 0, null);

            c.save();
            c.translate((-r.level.getStartRoom().getX() * blockWidth * 10) + player.offSetX, (-r.level.getStartRoom().getY() * blockHeigth * 8) + player.offSetY);
            if (r.isGenerated) {
                Rect blockRect;
                for (int i = 0; i < r.level.getRooms().length; i++) {
                    for (int j = 0; j < r.level.getRooms()[i].length; j++) {
                        blockY = i * blockHeigth * 8;
                        for (int k = 0; k < r.level.getRooms()[i][j].blocks.length; k++) {
                            blockX = j * blockWidth * 10;
                            for (int l = 0; l < r.level.getRooms()[i][j].blocks[k].length; l++) {
                                blockRect = new Rect(blockX, blockY, blockX + blockWidth, blockY + blockHeigth);

                                if (r.level.getRooms()[i][j].blocks[k][l] == null) {
                                    r.level.getRooms()[i][j].blocks[k][l] = new Block(null, false, '0');
                                } else {
                                    if (r.level.getRooms()[i][j].blocks[k][l].isCollisionable()) {
                                        r.level.getRooms()[i][j].blocks[k][l].setTile(currentBlock);
                                        c.drawBitmap(r.level.getRooms()[i][j].blocks[k][l].getTile(), null, blockRect, null);
                                    }
                                }
                                r.level.getRooms()[i][j].blocks[k][l].setBlockRect(blockRect);
                                blockX += blockWidth;
                            }
                            blockY += blockHeigth;
                        }
                    }
                }
            }
            c.restore();

            if (playerIsOnEnd) {
                c.drawRect(0, 0, screenWidth, screenHeight, endPaint);
            }

            btnLeft.draw(c);
            btnRight.draw(c);
            btnJump.draw(c);
            btnBack.draw(c);
            player.draw(c);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private long lastMilisUpdate = 0;
    private float last_x, last_y, last_z;
    private static final int SENSIBILITY = 300;

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this) {
            Sensor sensor = event.sensor;

            if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                long curMilis = System.currentTimeMillis();

                if ((curMilis - lastMilisUpdate) > 100) {
                    long milisDiff = (curMilis - lastMilisUpdate);
                    lastMilisUpdate = curMilis;

                    float speed = Math.abs(x + y + z - last_x - last_y - last_z) / milisDiff * 10000;

                    if (speed > SENSIBILITY && playerIsOnEnd) {
                        Log.i("ACABASTE LOCO", "proban2");
                    }

                    last_x = x;
                    last_y = y;
                    last_z = z;
                }
            }

            if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] == 0) {
                    currentBackground = nightBackground;
                    currentBlock = nightBlock;
                } else {
                    currentBackground = dayBackground;
                    currentBlock = dayBlock;
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
