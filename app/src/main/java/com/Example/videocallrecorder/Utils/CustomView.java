package com.Example.videocallrecorder.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.Example.videocallrecorder.Models.Stroke;
import java.util.ArrayList;
import java.util.Iterator;

public class CustomView extends View {
    private static final float TOUCH_TOLERANCE = 4.0f;
    private Bitmap canvasBitmap;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    public Paint drawPaint;
    private Path drawPath;
    private float mX;
    private float mY;
    public int paintColor = ViewCompat.MEASURED_STATE_MASK;
    private ArrayList<Path> paths = new ArrayList();
    private ArrayList<Stroke> strokes = new ArrayList();
    private ArrayList<Path> undonePaths = new ArrayList();
    private ArrayList<Stroke> undoneStrokes = new ArrayList();

    public CustomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
        setDrawingCacheEnabled(true);
    }

    private void init() {
        this.drawPath = new Path();
        this.drawPaint = new Paint();
        this.drawPaint.setAntiAlias(true);
        this.drawPaint.setStrokeWidth(10.0f);
        this.drawPaint.setStyle(Style.STROKE);
        this.drawPaint.setStrokeJoin(Join.ROUND);
        this.drawPaint.setStrokeCap(Cap.ROUND);
        this.drawPaint.setColor(this.paintColor);
        this.canvasPaint = new Paint(4);
    }

    private void touch_move(float f, float f2) {
        float abs = Math.abs(f - this.mX);
        float abs2 = Math.abs(f2 - this.mY);
        if (abs >= TOUCH_TOLERANCE || abs2 >= TOUCH_TOLERANCE) {
            this.drawPath.quadTo(this.mX, this.mY, (this.mX + f) / 2.0f, (this.mY + f2) / 2.0f);
            this.mX = f;
            this.mY = f2;
        }
    }

    private void touch_start(float f, float f2) {
        this.undoneStrokes.clear();
        this.strokes.add(new Stroke(this.drawPath, this.paintColor));
        this.drawPath.reset();
        this.drawPath.moveTo(f, f2);
        this.mX = f;
        this.mY = f2;
    }

    private void touch_up() {
        this.drawPath.lineTo(this.mX, this.mY);
        this.drawCanvas.drawPath(this.drawPath, this.drawPaint);
        this.drawPath = new Path();
    }

    public void eraseAll() {
        this.drawCanvas.drawColor(0, Mode.CLEAR);
        this.strokes.clear();
        invalidate();
    }

    public Bitmap get() {
        return getDrawingCache();
    }

    public void onClickRedo() {
        if (this.undoneStrokes.size() > 0) {
            this.strokes.add(this.undoneStrokes.remove(this.undoneStrokes.size() - 1));
            invalidate();
        }
    }

    public void onClickUndo() {
        if (this.strokes.size() > 0) {
            this.undoneStrokes.add(this.strokes.remove(this.strokes.size() - 1));
            invalidate();
        }
    }

    protected void onDraw(Canvas canvas) {
        Iterator it = this.strokes.iterator();
        while (it.hasNext()) {
            Stroke stroke = (Stroke) it.next();
            this.drawPaint.setColor(stroke.getPaintColor());
            canvas.drawPath(stroke.getPath(), this.drawPaint);
        }
        canvas.drawPath(this.drawPath, this.drawPaint);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.canvasBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
        this.drawCanvas = new Canvas(this.canvasBitmap);
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                touch_start(x, y);
                break;
            case 1:
                touch_up();
                break;
            case 2:
                touch_move(x, y);
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }
}
