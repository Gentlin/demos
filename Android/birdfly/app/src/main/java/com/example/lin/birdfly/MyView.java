package com.example.lin.birdfly;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
public class MyView extends View{
    Context context;
    Path path = new Path();
    Paint paint = new Paint();
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs); this.context = context;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(6);
        paint.setStrokeCap(Paint.Cap.ROUND);
        BlurMaskFilter blur = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(blur);
    }
    public void pathMoveTo(float x, float y) {
        path.moveTo(x, y);
    }
    public void pathLineTo(float x, float y) {
        path.lineTo(x, y);
    }
    public void pathReset() {
        path.reset();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        canvas.drawPath(path, paint);
      //  path.close();
    }
}
