package com.example.project9_4;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class Shape {
    int startX;
    int startY;
    int stopX;
    int stopY;

    public Shape(int _startX, int _startY, int _stopX, int _stopY) {
        this.startX = _startX;
        this.startY = _startY;
        this.stopX = _stopX;
        this.stopY = _stopY;
    }

    void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);

        this.drawImpl(canvas, paint);
    }

    abstract protected void drawImpl(Canvas canvas, Paint paint);

    public static class Line extends Shape {
        public Line(int _startX, int _startY, int _stopX, int _stopY) {
            super(_startX, _startY, _stopX, _stopY);
        }

        @Override
        protected void drawImpl(Canvas canvas, Paint paint) {
            canvas.drawLine(startX, startY, stopX, stopY, paint);
        }
    }

    public static class Circle extends Shape {
        public Circle(int _startX, int _startY, int _stopX, int _stopY) {
            super(_startX, _startY, _stopX, _stopY);
        }

        @Override
        protected void drawImpl(Canvas canvas, Paint paint) {
            int radius = (int) Math.sqrt(Math.pow(stopX - startX, 2) + Math.pow(stopY - startY, 2));
            canvas.drawCircle(startX, startY, radius, paint);
        }
    }

    public static class Rect extends Shape {
        public Rect(int _startX, int _startY, int _stopX, int _stopY) {
            super(_startX, _startY, _stopX, _stopY);
        }

        @Override
        protected void drawImpl(Canvas canvas, Paint paint) {
            canvas.drawRect(startX, startY, stopX, stopY, paint);
        }
    }
}
