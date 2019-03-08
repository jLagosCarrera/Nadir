package com.github.jlagoscarrera.nadir.Components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * The menu button component class.
 */
public class MenuButton {
    /**
     * Left/Top position on screen property
     */
    private int left, top;
    /**
     * Right/Bottom position on screen property
     */
    private int right, bottom;
    /**
     * Text of the button
     */
    private String text;
    /**
     * Button rectangle
     */
    private Rect button;
    /**
     * Text X/Y position on the screen
     */
    private int textX, textY;
    /**
     * Button style paint
     */
    private Paint pButton;
    /**
     * Button border style paint
     */
    private Paint pButtonBorder;
    /**
     * Text style paint
     */
    private Paint pText;
    /**
     * Button image
     */
    private Bitmap icon;

    /**
     * Instantiates a new menu button component.
     *
     * @param left   the left position
     * @param top    the top position
     * @param right  the right position
     * @param bottom the bottom position
     */
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

    /**
     * Gets menu button icon.
     *
     * @return the menu button icon
     */
    public Bitmap getIcon() {
        return icon;
    }

    /**
     * Sets menu button icon.
     *
     * @param icon the menu button icon
     */
    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    /**
     * Gets left menu button position.
     *
     * @return the left menu button position
     */
    public int getLeft() {
        return left;
    }

    /**
     * Sets left menu button position.
     *
     * @param left the left menu button position
     */
    public void setLeft(int left) {
        this.left = left;
        button.left = left;
    }

    /**
     * Gets top menu button position.
     *
     * @return the top menu button position
     */
    public int getTop() {
        return top;
    }

    /**
     * Sets top menu button position.
     *
     * @param top the top menu button position
     */
    public void setTop(int top) {
        this.top = top;
        button.top = top;
    }

    /**
     * Gets right menu button position.
     *
     * @return the right menu button position
     */
    public int getRight() {
        return right;
    }

    /**
     * Sets right menu button position.
     *
     * @param right the right menu button position
     */
    public void setRight(int right) {
        this.right = right;
        button.right = right;
    }

    /**
     * Gets bottom menu button position.
     *
     * @return the bottom menu button position
     */
    public int getBottom() {
        return bottom;
    }

    /**
     * Sets bottom menu button position.
     *
     * @param bottom the bottom menu button position
     */
    public void setBottom(int bottom) {
        this.bottom = bottom;
        button.bottom = bottom;
    }

    /**
     * Gets menu button text.
     *
     * @return the menu button text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets menu button text.
     *
     * @param text the menu button text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets button rectangle.
     *
     * @return the button rectangle
     */
    public Rect getButton() {
        return button;
    }

    /**
     * Gets button rectangle.
     *
     * @return the button rectangle
     */
    public Paint getpButton() {
        return pButton;
    }

    /**
     * Sets button paint style.
     *
     * @param pButton the button paint style
     */
    public void setpButton(Paint pButton) {
        this.pButton = pButton;
    }

    /**
     * Gets button border paint style.
     *
     * @return the button border paint style
     */
    public Paint getpButtonBorder() {
        return pButtonBorder;
    }

    /**
     * Sets button border paint style.
     *
     * @param pButtonBorder the button border paint style
     */
    public void setpButtonBorder(Paint pButtonBorder) {
        this.pButtonBorder = pButtonBorder;
    }

    /**
     * Gets menu button text.
     *
     * @return the menu button text
     */
    public Paint getpText() {
        return pText;
    }

    /**
     * Sets menu button text.
     *
     * @param pText the menu button text.
     */
    public void setpText(Paint pText) {
        this.pText = pText;
    }

    /**
     * Gets text x position.
     *
     * @return the text x position
     */
    public int getTextX() {
        return textX;
    }

    /**
     * Sets text x position.
     *
     * @param textX the text x position
     */
    public void setTextX(int textX) {
        this.textX = textX;
    }

    /**
     * Gets text y position.
     *
     * @return the text y position
     */
    public int getTextY() {
        return textY;
    }

    /**
     * Sets text y position.
     *
     * @param textY the text y position
     */
    public void setTextY(int textY) {
        this.textY = textY;
    }

    /**
     * Draw on canvas.
     *
     * @param c the canvas to draw at
     */
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
