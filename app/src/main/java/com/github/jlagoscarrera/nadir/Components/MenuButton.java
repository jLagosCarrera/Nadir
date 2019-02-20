package com.github.jlagoscarrera.nadir.Components;

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
    private Paint pButton = new Paint();
    private Paint pText = new Paint();

    public MenuButton(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;

        button = new Rect(left, top, right, bottom);
        text = "";
        textX = button.centerX();
        textY = button.centerY();

        pButton.setColor(Color.RED);

        pText.setColor(Color.BLACK);
        pText.setTextAlign(Paint.Align.CENTER);
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

    public Paint getpText() {
        return pText;
    }

    public void setpText(Paint pText) {
        this.pText = pText;
    }

    public void draw(Canvas c) {
        try {
            c.drawRect(button, pButton);
            c.drawText(text, textX, textY + pText.getTextSize() / 3, pText);
        } catch (Exception e) {
            Log.i("Drawing error", e.getLocalizedMessage());
        }
    }
}
