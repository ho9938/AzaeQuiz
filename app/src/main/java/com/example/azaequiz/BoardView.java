package com.example.azaequiz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class BoardView extends View {
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private Paint paint;
    private ImageButton pen;
    private ImageButton eraser;

    public BoardView(Context context, AttributeSet attrs) { // constructor
        super(context, attrs);

        path = new Path();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);
    }

    public void initBoard(ImageButton pen, ImageButton eraser) {
        this.pen = pen;
        pen.setOnClickListener(holdPen);

        this.eraser = eraser;
        eraser.setOnClickListener(holdEraser);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();;
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x, y);
                canvas.drawPath(path, paint);
                path.reset();
                break;
        }
        this.invalidate();
        return true;
    }

    public Bitmap getBitmap() {
        Log.d("BoardView", "getBitmap()");
        return bitmap;
    }

    View.OnClickListener holdPen = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(20);
        }
    };

    View.OnClickListener holdEraser = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(100);
        }
    };
}
