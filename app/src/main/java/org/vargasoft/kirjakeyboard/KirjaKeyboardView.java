package org.vargasoft.kirjakeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.TypedValue;

import java.util.List;

/**
 * My own KeyboardView with themes and persistent preferences.
 * Created by Varga Péter on 2017. 04. 23..
 */

public class KirjaKeyboardView extends KeyboardView
{
    private int theme;
    private final Paint paint;

    public KirjaKeyboardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        loadPreferences();
        paint = new Paint();
    }
    public void switchTheme()
    {
        switch(theme)
        {
            case 5: theme = 0; break;
            default: theme++; break;
        }
    }

    /**
     * Load the saved variables
     */
    private void loadPreferences()
    {
        theme = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("theme",0);
    }

    /**
     * Persist variables to Program Data
     */
    public void savePreferences()
    {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt("theme",theme).apply();
    }
    @Override public void onDraw(Canvas canvas)
    {
        //super.onDraw(canvas);

        List<Keyboard.Key> keys = getKeyboard().getKeys();

        for (Keyboard.Key key : keys)
        {
            if(theme == 1 || theme == 2 )
            {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlkey_background,null);
                if(drawable != null)
                {
                    drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    drawable.draw(canvas);
                }
            }
            if(theme == 3)
            {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlkey_purplebackground,null);
                if(drawable != null)
                {
                    drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    drawable.draw(canvas);
                }
            }
            if(theme == 4)
            {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlkey_backgroundred,null);
                if(drawable != null)
                {
                    drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    drawable.draw(canvas);
                }
            }
            if(theme == 5)
            {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlsmurfbluegomb,null);
                if(drawable != null)
                {
                    drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    drawable.draw(canvas);
                }
            }
            else if(theme == 0)
            {
                Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.xmlkey_background2,null);
                if(drawable != null)
                {
                    drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    drawable.draw(canvas);
                }

            }

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            if((key.codes[0] == 5053) || (key.codes[0] == 4993) || (key.codes[0] == 5051) || (key.codes[0] == -4)
                    || (key.codes[0] == -5) || (key.codes[0] == KirjaInputMethodService.KEY_CAPS_LOCK)
                    || (key.codes[0] == 5012) || (key.codes[0] == 5013))
            {
                paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));//28
            }
            else if((key.codes[0] == 5050) || (key.codes[0] == 5052))
            {
                paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));//28
            }
            else
            {
                paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 22, getResources().getDisplayMetrics()));//48
            }
            if(theme == 1)
                paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGold));
            else if(theme == 4)
                paint.setColor(ContextCompat.getColor(getContext(), R.color.colorCream));
            else if(theme == 2)
                paint.setColor(ContextCompat.getColor(getContext(), R.color.colorGreen));
            else if(theme == 0)
                paint.setColor(Color.BLACK);
            else if(theme == 3)
                paint.setColor(Color.WHITE);
            else if(theme == 5)
            {
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD_ITALIC));
                paint.setColor(ContextCompat.getColor(getContext(), R.color.colorPurple));
            }
            if (key.label != null)
            {
                canvas.drawText(key.label.toString(), key.x + (key.width / 2), key.y + (key.height / 2 + key.height/8), paint);
            }
            else if(key.icon != null)
            {
                key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                key.icon.draw(canvas);
            }
        }

    }
}
