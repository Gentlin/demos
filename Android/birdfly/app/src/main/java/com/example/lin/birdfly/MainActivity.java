package com.example.lin.birdfly;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import java.math.*;
import java.util.ArrayList;

import android.view.Menu;
import android.view.MenuItem;
import com.example.lin.birdfly.MyView;
public class MainActivity extends AppCompatActivity {
    private ImageView imageView ;
    private MyView broder;
    private final static boolean LEFT = true;
    private final static boolean RIGHT = false;
    private boolean dir = RIGHT;
    private float toXDelta = 0;
    private float toYDelta = 0;
    private double diagonal = 0;
    private ImageView redPoint;
    private AnimationDrawable redPointAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.sprite);
        broder = (MyView) findViewById(R.id.border);
        final AnimationDrawable sprite = (AnimationDrawable)imageView.getBackground();
        sprite.start();
        redPoint = (ImageView)findViewById(R.id.redPoint);
        redPointAnim = (AnimationDrawable)redPoint.getBackground();
    }
    private final static int START_RECORD = 0;
    private final static int STOP_RECORD = 1;
    private final static int REPLAY = 2;
    private final static int STOP_REPLAY = 3;
    private final static int NORMAL= 4;
    private static Pair<Float, Float> curPos ;
    private boolean curDir;

    private ArrayList<Pair<Float, Float>> path = new ArrayList<Pair<Float, Float>>();
    int mode = NORMAL;
    public void saveCurPos() {
        curPos = new Pair<Float, Float>(imageView.getX(), imageView.getY());
        curDir = this.dir;
    }
    public void recoverCurPos() {
        imageView.setRotationY(curDir == LEFT ? 180f : 0f);
        imageView.setX(curPos.first);
        imageView.setY(curPos.second);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(1, START_RECORD, 1, "开始录像");
        menu.add(1, STOP_RECORD, 2, "结束录像");
        menu.add(1,REPLAY,3, "录像重放");
        menu.add( 1,STOP_REPLAY,4,"结束回放");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case START_RECORD:
                startRecord();
                break;
            case STOP_RECORD:
                stopRecord();
                break;
            case REPLAY:
                replay();
                break;
            case STOP_REPLAY:
                stopReplay();
                break;
        }
        //return true;
        return super.onOptionsItemSelected(item);
    }
    public void startRecord() {
        if(mode != NORMAL)return;
        mode = START_RECORD;
        path.clear();
        path.add(new Pair<Float, Float>(imageView.getX(), imageView.getY()));
        redPointAnim.start();
    }
    public void stopRecord() {
        if(mode != START_RECORD)return;
        mode = NORMAL;
        redPointAnim.stop();
        redPointAnim.selectDrawable(0);
    }
    public void replay() {
        if(mode != NORMAL)return;
        mode = REPLAY;
        index = 1;
        if(index >= path.size())return;
        saveCurPos();
        final Pair<Float, Float> from = path.get(0);
        final Pair<Float, Float> to = path.get(index);
        broder.pathMoveTo(from.first + imageView.getWidth()/2, from.second +imageView.getHeight()/2);
        fly(from, to);
    }

    public void stopReplay() {
        if(mode != REPLAY)return;
        mode = NORMAL;
        broder.path.reset();
        broder.invalidate();
        a.cancel();
        recoverCurPos();
    }
    private AnimatorSet a;
    private int index = 0;
   // final AnimatorSet animSet = new AnimatorSet();
    public void fly(final Pair<Float, Float> from, final Pair<Float, Float> to) {
        imageView.setRotationY(from.first - to.first < 0 ? 0f : 180f);
        broder.pathLineTo(to.first+ imageView.getWidth()/2, to.second + imageView.getHeight()/2);
        broder.invalidate();
        final ObjectAnimator xAnim = ObjectAnimator.ofFloat(imageView, "x", from.first, to.first);
        final ObjectAnimator yAnim = ObjectAnimator.ofFloat(imageView, "y", from.second, to.second);
        final AnimatorSet animSet = new AnimatorSet();
        xAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) xAnim.getAnimatedValue();
                imageView.setX(x);
            }
        });

        yAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y = (float) yAnim.getAnimatedValue();
                imageView.setY(y);
            }
        });
        long time = (long) (5000 * dist(from.first -to.first, from.second - to.second) / dist(broder.getWidth(), broder.getHeight()));
        xAnim.setInterpolator(new LinearInterpolator());
        yAnim.setInterpolator(new LinearInterpolator());
        xAnim.setDuration(time);
        yAnim.setDuration(time);

        animSet.play(yAnim).with(xAnim);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                index++;
                if(index >= path.size() || mode == NORMAL) {
                    stopReplay();
                    recoverCurPos();
                    return;
                }
                final Pair<Float, Float> from = to;
                final Pair<Float, Float> to = path.get(index);
                fly(from, to);
            }
        });
        animSet.start();
    }
    boolean isFlying = false;


    private double dist(float xDelata, float yDelta) {
        return Math.sqrt(xDelata *  xDelata + yDelta * yDelta);
    }
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN && this.mode != REPLAY && !isFlying) {

            float headX = this.dir == RIGHT ? imageView.getX() + imageView.getWidth() : imageView.getX();

            this.dir  = event.getX() - headX > 0 ? this.RIGHT : this.LEFT;
            imageView.setRotationY(this.dir == this.RIGHT ? 0f : 180f);

            toXDelta = this.dir == this.RIGHT ? event.getX() - imageView.getX() - imageView.getWidth() / 2 : event.getX() - imageView.getX() - imageView.getWidth() / 2;
            toYDelta = event.getY()  - imageView.getY() - imageView.getHeight() ;
            if(imageView.getX() + toXDelta + imageView.getWidth()> broder.getWidth()) {
                toXDelta = broder.getWidth() - imageView.getWidth() - imageView.getX();
            }
            if(imageView.getY() + toYDelta + imageView.getHeight()> broder.getHeight()) {
                toYDelta = broder.getHeight() - imageView.getHeight() - imageView.getY();
            }
            if(imageView.getX() + toXDelta  < 0) {
                toXDelta =  - imageView.getX();
            }
            if(imageView.getY() + toYDelta  < 0 ) {
                toYDelta =  - imageView.getY();
            }
            final ObjectAnimator xAnim = ObjectAnimator.ofFloat(imageView, "x", imageView.getX(), imageView.getX() + toXDelta);
            final ObjectAnimator yAnim = ObjectAnimator.ofFloat(imageView, "y", imageView.getY(), imageView.getY() + toYDelta);
            xAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float x = (float)xAnim.getAnimatedValue();
                    imageView.setX(x);
                }
            });
            yAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float y = (float)yAnim.getAnimatedValue();
                    imageView.setY(y);
                }
            });
            if(this.mode == START_RECORD) {
                this.path.add(new Pair<Float, Float>(imageView.getX() + toXDelta, imageView.getY() + toYDelta));
            }
            long time = (long)(5000 * dist(toXDelta, toYDelta) / dist(broder.getWidth(), broder.getHeight()));
            xAnim.setInterpolator(new LinearInterpolator());
            yAnim.setInterpolator(new LinearInterpolator());
            xAnim.setDuration(time);
            yAnim.setDuration(time);
            AnimatorSet animSet = new AnimatorSet();
            a = animSet;
            animSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isFlying = true;
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    isFlying = false;
                }
            });
            animSet.play(xAnim).with(yAnim);
            animSet.start();
        }
        return true;
    }
}