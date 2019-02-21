package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class MenuButton {
    private int left, top;
    private int right, bottom;
    private String text;
    private Rect button;
    private int textX, textY;
    private Paint pButton;
    private Paint pButtonBorder;
    private Paint pText;
    private Bitmap icon;

    public MenuButton(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        button = new Rect(left, top, right, bottom);
        text = "";
        textX = button.centerX();
        textY = button.centerY();

        pButton = new Paint();
        pButton.setColor(Color.DKGRAY);
        pButton.setAlpha(220);

        pButtonBorder = new Paint();
        pButtonBorder.setColor(Color.BLACK);
        pButtonBorder.setStyle(Paint.Style.STROKE);
        pButtonBorder.setStrokeWidth(2);

        pText = new Paint();
        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);

        icon = null;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
        button.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
        button.top = top;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
        button.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
        button.bottom = bottom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Rect getButton() {
        return button;
    }

    public Paint getpButton() {
        return pButton;
    }

    public void setpButton(Paint pButton) {
        this.pButton = pButton;
    }

    public Paint getpButtonBorder() {
        return pButtonBorder;
    }

    public void setpButtonBorder(Paint pButtonBorder) {
        this.pButtonBorder = pButtonBorder;
    }

    public Paint getpText() {
        return pText;
    }

    public void setpText(Paint pText) {
        this.pText = pText;
    }

    public void draw(Canvas c) {
        try {
            if (icon == null) {
                c.drawRect(button, pButton);
                c.drawRect(button, pButtonBorder);
            } else {
                c.drawBitmap(icon, null, button, null);
            }
            c.drawText(text, textX, textY + pText.getTextSize() / 3, pText);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
