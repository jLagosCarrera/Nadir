package com.github.jlagoscarrera.nadir.Scenes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
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

import java.util.concurrent.TimeUnit;

/**
 * The play scene.
 */
public class Play extends Scene implements SensorEventListener {
    /**
     * The Sensor manager.
     */
    SensorManager sensorManager;
    /**
     * The Accelerometer sensor.
     */
    Sensor acelerometro;
    /**
     * The Proximity sensor.
     */
    Sensor proximidad;
    /**
     * The RoomFiller generator reference.
     */
    public RoomFiller r;
    /**
     * The back to menu button.
     */
    MenuButton btnBack;
    /**
     * The move left button.
     */
    MenuButton btnLeft,
    /**
     * The move right button.
     */
    btnRight;
    /**
     * The jump button.
     */
    MenuButton btnJump;
    /**
     * The Block heigth.
     */
    public int blockHeigth;
    /**
     * The Block width.
     */
    public int blockWidth;
    /**
     * The Block top x position.
     */
    int blockX,
    /**
     * The Block left y position.
     */
    blockY;
    /**
     * The Player object.
     */
    Player player;
    /**
     * Indicates if player is moving left.
     */
    boolean movingLeft;
    /**
     * Indicates if player is moving right.
     */
    boolean movingRight;
    /**
     * Indicated if it has to keep drawing game things on screen
     */
    boolean keepDrawing;
    /**
     * Indicates if player is on end room.
     */
    boolean playerIsOnEnd;
    /**
     * Indicates if player is jumping.
     */
    public boolean jumping;
    /**
     * Indicates if player is falling.
     */
    public boolean falling;
    /**
     * The End room.
     */
    Rect endRoom;
    /**
     * The paint to indicate we are on last room.
     */
    Paint endPaint;

    /**
     * The Current background bitmap.
     */
    Bitmap currentBackground;
    /**
     * The Night background bitmap.
     */
    Bitmap nightBackground;
    /**
     * The Day background bitmap
     */
    Bitmap dayBackground;

    /**
     * The Current block bitmap.
     */
    Bitmap currentBlock;
    /**
     * The Night block bitmap.
     */
    Bitmap nightBlock;
    /**
     * The Day block bitmap.
     */
    Bitmap dayBlock;
    /**
     * The Game engine reference.
     */
    NadirEngine gameReference;
    /**
     * Menu button that will say us that we finished
     */
    MenuButton end;
    /**
     * Ending time
     */
    MenuButton endTitle;
    /**
     * Stating milliseconds of the run
     */
    long startTime;

    /**
     * Instantiates a new play scene.
     *
     * @param gameReference the game engine reference
     * @param sceneId       the current scene id
     * @param screenWidth   the screen width
     * @param screenHeight  the screen height
     */
    public Play(NadirEngine gameReference, int sceneId, int screenWidth, int screenHeight) {
        super(gameReference, sceneId, screenWidth, screenHeight);

        this.gameReference = gameReference;

        keepDrawing = true;

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

        endTitle = new MenuButton(widthDiv * 6, heighDiv * 2, widthDiv * 18, heighDiv * 4);
        endTitle.getpButton().setColor(Color.TRANSPARENT);
        endTitle.getpButtonBorder().setColor(Color.TRANSPARENT);
        endTitle.getpText().setTextSize((int) (heighDiv * 2 * 0.75));
        endTitle.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        end = new MenuButton(widthDiv * 6, heighDiv * 6, widthDiv * 18, heighDiv * 10);
        end.getpText().setTextSize((int) (heighDiv * 4 * 0.75));
        end.getpText().setTypeface(Typeface.createFromAsset(gameReference.getContext().getAssets(), "font/Poiretone.ttf"));
        end.setText(gameReference.getContext().getString(R.string.end));

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

        startTime = System.currentTimeMillis();
        gameReference.options.vibrate();
    }

    /**
     * Handles touches on the screen.
     *
     * @param event asociated event to the touch
     * @return the new scene ID
     */
    public int onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();        //Obtain action index.
        int pointerID = event.getPointerId(pointerIndex); //Obtain id of the pointer asociated to the action.
        int action = event.getActionMasked();             //Obtain type of pulsation.
        switch (action) {
            case MotionEvent.ACTION_DOWN:           //First finger touches.
            case MotionEvent.ACTION_POINTER_DOWN:   //Second and next touches.
                if (keepDrawing) {
                    if (isTouched(btnJump.getButton(), event) && !jumping && !falling) {
                        jumping = true;
                        falling = false;
                    }
                    if (isTouched(btnLeft.getButton(), event)) {
                        movingLeft = true;
                        movingRight = false;
                    } else if (isTouched(btnRight.getButton(), event)) {
                        movingRight = true;
                        movingLeft = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:             //Last finger up.
            case MotionEvent.ACTION_POINTER_UP:     //Any finger that isnt the last up.
                if (keepDrawing) {
                    if (isTouched(btnBack.getButton(), event) && sceneId != 0) return 0;
                    if (isTouched(btnLeft.getButton(), event)) {
                        movingLeft = false;
                    } else if (isTouched(btnRight.getButton(), event)) {
                        movingRight = false;
                    }
                }
                if (isTouched(end.getButton(), event) && !keepDrawing) {
                    return 93;
                }
                break;
            case MotionEvent.ACTION_MOVE: //Any finger is moved.
                break;
            default:
                Log.i("Any other action", "Action is not defined: " + action);
        }

        return sceneId;
    }

    /**
     * The movement sound playing id.
     */
    int soundMoveID;
    /**
     * Indicates if the movement sound is already playing.
     */
    boolean alreadyPlayingMoveSoung = false;

    /**
     * Applies gravity and moves on X axis, also, it plays sounds for jump and movement.
     */
    //We refresh game physics on screen.
    public void refreshPhysics() {
        player.aplicarGravedad();

        if (jumping) {
            if (gameReference.options.isPlaySounds()) {
                gameReference.effects.play(gameReference.jumpSound,
                        gameReference.volume, gameReference.volume,
                        1, 0, 1);
            }
            player.jump();
        }
        if (movingLeft) {
            player.moveLeft();
        }
        if (movingRight) {
            player.moveRight();
        }
        if (gameReference.options.isPlaySounds()) {
            if (movingLeft || movingRight) {
                if (!alreadyPlayingMoveSoung) {
                    soundMoveID = gameReference.effects.play(gameReference.moveSound,
                            gameReference.volume, gameReference.volume,
                            1, -1, 1);
                    alreadyPlayingMoveSoung = true;
                }
            } else {
                gameReference.effects.stop(soundMoveID);
                alreadyPlayingMoveSoung = false;
            }
        }

        if (endRoom.contains(player.getX(), player.getY())) {
            playerIsOnEnd = true;
        } else {
            playerIsOnEnd = false;
        }
    }

    /**
     * Draw on canvas.
     *
     * @param c the canvas to draw at
     */
    //Drawing routine, called from the game thread.
    public void draw(Canvas c) {
        try {
            if (keepDrawing) {
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
                btnLeft.draw(c);
                btnRight.draw(c);
                btnJump.draw(c);
                player.draw(c);

                if (playerIsOnEnd) {
                    c.drawRect(0, 0, screenWidth, screenHeight, endPaint);
                }
                btnBack.draw(c);
            } else {
                drawParallax(c);
                endTitle.draw(c);
                end.draw(c);
            }
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /**
     * Last update time for accelerometer.
     */
    private long lastMilisUpdate = 0;
    /**
     * Last position x/y/z for accelerometer.
     */
    private float last_x, last_y, last_z;
    /**
     * Accelerometer sensibility for shaking.
     */
    private static final int SENSIBILITY = 400;

    /**
     * Changes on any sensor.
     *
     * @param event event of the sensor that contains all info
     */
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
                        gameReference.options.vibrate();
                        long endTime = System.currentTimeMillis() - startTime;
                        gameReference.endTime = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(endTime),
                                TimeUnit.MILLISECONDS.toMinutes(endTime) % TimeUnit.HOURS.toMinutes(1),
                                TimeUnit.MILLISECONDS.toSeconds(endTime) % TimeUnit.MINUTES.toSeconds(1));
                        endTitle.setText(gameReference.getContext().getString(R.string.ended) + " " + gameReference.endTime);
                        keepDrawing = false;
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

    /**
     * Accuracy changed on a sensor.
     *
     * @param sensor   the sensor that changed accuracy
     * @param accuracy accuracy change
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
