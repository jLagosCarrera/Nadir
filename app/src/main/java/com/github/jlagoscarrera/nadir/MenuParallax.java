package com.github.jlagoscarrera.nadir;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;

import com.github.jlagoscarrera.nadirGame.R;

public class MenuParallax {
    public PointF position;
    public Bitmap back, mid, front1, front2;

    public MenuParallax(Context context, int screenWidth, int screenHeight) {
        back = BitmapFactory.decodeResource(context.getResources(), R.mipmap.back);
        back = Bitmap.createScaledBitmap(back, back.getWidth(), screenHeight, true);

        mid = BitmapFactory.decodeResource(context.getResources(), R.mipmap.mid);
        mid = Bitmap.createScaledBitmap(mid, mid.getWidth(), screenHeight, true);

        front1 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.front1);
        front1 = Bitmap.createScaledBitmap(front1, front1.getWidth(), screenHeight, true);

        front2 = BitmapFactory.decodeResource(context.getResources(), R.mipmap.front2);
        front2 = Bitmap.createScaledBitmap(front2, front2.getWidth(), screenHeight, true);
    }

    public void move(int velocity) {
        int vBack, vMid, vFront;
        if (velocity < 3) {
            vFront = 3;
            vMid = 2;
            vBack = 1;
        } else {
            vFront = velocity;
            vMid = (velocity - velocity / 3);
            vBack =
        }
    }
}
