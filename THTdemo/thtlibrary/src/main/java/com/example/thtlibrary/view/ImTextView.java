package com.example.thtlibrary.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.example.thtlibrary.R;


/**
 * 气泡textView
 */

public class ImTextView extends android.support.v7.widget.AppCompatTextView {

    private Paint mPaint;
    private RectF mRectF;
    private Path mPath;
    private Path mRectPath;
    private int mCircleRadius;
    // 1 左边 2 右边 3 隐藏
    private int mAngleLocation;
    //三角距离自身顶部的距离
    private float mAnglePaddingTop;
    //角标的宽度
    private float mCornerMarkWidth =5;
    //角标的高度
    private float mCornerMarkHeight = 5;
    //背景颜色
    private int mBgColor;

    private int mPaddingRight = -1;
    private int mPaddingLeft = -1;

    private float[] radii;
    private float[] radii2;

    public ImTextView(Context context) {
        this(context, null);
    }

    public ImTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        initData(context, attrs);
    }

    public ImTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs);
    }

    private void initData(Context context, AttributeSet attrs) {
        LogUtils.e("11");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImTextView);
        mCircleRadius = typedArray.getDimensionPixelOffset(R.styleable.ImTextView_circleRadius, 10);
        mAngleLocation = typedArray.getInt(R.styleable.ImTextView_AngleLocation, 1);
        mAnglePaddingTop =typedArray.getDimension(R.styleable.ImTextView_anglePaddingTop, 10f);
        mCornerMarkWidth = typedArray.getDimension(R.styleable.ImTextView_Corner_Mark_Width, 5f);
        mCornerMarkHeight = typedArray.getDimension(R.styleable.ImTextView_Corner_Mark_Height, 5f);
        mBgColor = typedArray.getColor(R.styleable.ImTextView_Background_Color, Color.WHITE);
        /*radii= new float[]{0f, 0f, mCircleRadius, mCircleRadius, mCircleRadius, mCircleRadius, mCircleRadius, mCircleRadius};
        radii2= new float[]{mCircleRadius, mCircleRadius, 0f, 0f, mCircleRadius, mCircleRadius, mCircleRadius, mCircleRadius};*/
        typedArray.recycle();
        mPaint = new Paint();
        mRectF = new RectF();
        mPaint.setAntiAlias(true);
        mPath = new Path();
        //mRectPath=new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取View宽高
        if (mAngleLocation != 3) {
            //显示三角角标 重新设置 view的宽度
            int preWidth = View.MeasureSpec.getSize(widthMeasureSpec);
            preWidth += mCornerMarkWidth;
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(preWidth, View.MeasureSpec.getMode(widthMeasureSpec));
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        //将三角角标多出来的宽度 根据其位置计算到左右内边距当中
        if (mPaddingRight == -1) {
            //获取TextView原来的右内边距
            mPaddingRight = getPaddingRight();
            if (mAngleLocation == 2) {//重新设置值PaddingRight 重绘
                mPaddingRight += mCornerMarkWidth/2;
                setPadding(getPaddingLeft(), getPaddingTop(), mPaddingRight, getPaddingBottom());
                return;
            }
        }
        if (mPaddingLeft == -1) {
            //获取TextView 原来的左边界
            mPaddingLeft = getPaddingLeft();
            if (mAngleLocation == 1) {//重新设置值PaddingLeft 重绘
                mPaddingLeft += mCornerMarkWidth;
                setPadding(mPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
                return;
            }
        }
        //不计算padding 在内 绘制背景
        // 布局中的padding 可以调整文字的位置 而背景位置不受影响
        mRectF.left = 0;
        mRectF.right = getWidth();
        switch (mAngleLocation) {
            case 1://左边
                mRectF.left = mCornerMarkWidth;
                break;
            case 2://右边
                mRectF.right = getWidth() - mCornerMarkWidth;
                break;
            case 3://隐藏
                break;
        }
        mRectF.top = 0;
        mRectF.bottom = getHeight();
        mPaint.setColor(mBgColor);


        canvas.drawRoundRect(mRectF, mCircleRadius, mCircleRadius, mPaint);
        //LogUtils.e("mAngleLocation",mAngleLocation+"");
        if (mAngleLocation == 1) {

            /*mRectPath.addRoundRect(mRectF, radii, Path.Direction.CW);
            canvas.drawPath(mRectPath,mPaint);*/
            mPath.moveTo(mCornerMarkWidth, mAnglePaddingTop);
           /* mPath.lineTo(0, mAnglePaddingTop + mCornerMarkHeight / 2);
            mPath.lineTo(mCornerMarkWidth, mAnglePaddingTop + mCornerMarkHeight);
            mPath.lineTo(mCornerMarkWidth, mAnglePaddingTop);*/
            mPath.lineTo(0, mAnglePaddingTop );
            mPath.lineTo(mCornerMarkWidth*2, mAnglePaddingTop + mCornerMarkHeight*2);
            mPath.lineTo(mCornerMarkWidth*2, mAnglePaddingTop);
            canvas.drawPath(mPath, mPaint);

        } else if (mAngleLocation == 2) {

           /* mRectPath.addRoundRect(mRectF, radii2, Path.Direction.CW);
            canvas.drawPath(mRectPath,mPaint);*/
            mPath.moveTo(getWidth() - mCornerMarkWidth*2, mAnglePaddingTop);
            mPath.lineTo(getWidth(), mAnglePaddingTop);
            mPath.lineTo(getWidth() - mCornerMarkWidth*2, mAnglePaddingTop + mCornerMarkHeight*2);
            canvas.drawPath(mPath, mPaint);

        }
        super.onDraw(canvas);//调研父View绘制TextView 内容
    }
}

