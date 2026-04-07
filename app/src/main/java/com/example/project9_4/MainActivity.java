package com.example.project9_4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final static int LINE = 1, CIRCLE = 2, RECTANGLE = 3;
    static int curShape = LINE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
        setTitle("간단 그림판");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "선 추가");
        menu.add(0, 2, 0, "원 추가");
        menu.add(0, 3, 0, "사각형 추가");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                curShape = LINE;
                return true;
            case 2:
                curShape = CIRCLE;
                return true;
            case 3:
                curShape = RECTANGLE;
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class MyGraphicView extends View {
        List<Shape> shapes = new ArrayList<>();

        boolean isDrawing;
        int startX = -1, startY= - 1, stopX = -1, stopY = -1;

        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    isDrawing = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    this.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    stopX = (int) event.getX();
                    stopY = (int) event.getY();
                    isDrawing = false;
                    shapes.add(createShape());
                    this.invalidate();
                    break;
            }
            return true;
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);

            // 기존에 그려진 Shape 그리기
            for (Shape shape : shapes) {
                shape.draw(canvas);
            }

            // 현재 그리고 있는 Shape 그리기
            if(isDrawing) {
                createShape().draw(canvas);
            }
        }

        private Shape createShape() {
            switch (curShape) {
                case LINE:
                    return new Shape.Line(startX, startY, stopX, stopY);
                case CIRCLE:
                    return new Shape.Circle(startX, startY, stopX, stopY);
                case RECTANGLE:
                    return new Shape.Rect(startX, startY, stopX, stopY);
            }

            // 알 수 없는 모양인 경우 라인으로 fallback
            return new Shape.Line(startX, startY, stopX, stopY);
        }
    }
}