package org.vargasoft.colorpicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.Dimension;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.TypedValue;

import org.vargasoft.kirjakeyboard.R;

/**
 * Created by Peti on 2018. 02. 18..
 */

public class ColorChooserSeekBar extends AppCompatSeekBar
{
    private Paint textPaint;
    private Rect textRect;


    private int textColor;

    @Dimension(unit = 2)
    private float textSize;

    private String text;

    public ColorChooserSeekBar(Context context) {
        super(context);
        init(null);
    }

    public ColorChooserSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ColorChooserSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs)
    {
        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        textRect = new Rect();

        if (attrs != null)
        {
            final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ColorChooserSeekBar);
            try
            {
                textColor = typedArray.getColor
                (
                    R.styleable.ColorChooserSeekBar_android_text,
                    0xff000000
                );

                textSize = typedArray.getDimension
                (
                    R.styleable.ColorChooserSeekBar_android_textSize,
                    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics())
                );
                text = typedArray.getString(R.styleable.ColorChooserSeekBar_android_text);
            }
            finally
            {
                typedArray.recycle();
            }
        }

        textPaint.setColor(textColor);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textPaint.getTextBounds("255", 0, 3, textRect);

        setPadding(getPaddingLeft(), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) (0.6 * textRect.height()), getResources().getDisplayMetrics()),
                getPaddingRight(), getPaddingBottom());
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawText
        (
            (text == null) ? String.valueOf(getProgress()) : text,
            getThumb().getBounds().left + getPaddingLeft(),
            textRect.height() + (getPaddingTop() >> 2),
            textPaint
        );
    }
}
