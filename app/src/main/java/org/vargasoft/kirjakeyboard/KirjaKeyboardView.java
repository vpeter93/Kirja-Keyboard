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
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
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
    private int customFontColor;
    private int customKeyColor;
    private int customTexture;
    private int keyHeightDp;
    private boolean numbersInMainView;
    private boolean automataToUppercase;

    public KirjaKeyboardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        loadPreferences();
        paint = new Paint();
    }
    public void setCustomFontColor(int color)
    {
        customFontColor = color;
    }
    public void setCustomKeyColor(int color)
    {
        customKeyColor = color;
    }
    public int getCustomFontColor(){return customFontColor;}
    public int getCustomKeyColor(){return customKeyColor;}
    public boolean getNumbersInMainView(){return numbersInMainView;}
    public void setNumbersInMainView(boolean value){ numbersInMainView = value;}
    public void setAutomataToUppercase(boolean value){ automataToUppercase = value;}
    public boolean getAutomataToUppercase(){return automataToUppercase;}
    public void switchTheme()
    {
        switch(theme)
        {
            case 6: theme = 0; break;
            default: theme++; break;
        }
        Settings.getInstance().setTheme(theme);
    }
    public void setCustomTexture(int value)
    {
        customTexture = value;
    }

    /**
     * Load the saved variables
     */
    public void loadPreferences()
    {
        theme = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("theme",0);
        customTexture = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("customTexture",0);
        customFontColor = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("customFontColor",Color.BLACK);
        customKeyColor = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("customKeyColor",Color.argb(80,255,255,255));
        keyHeightDp = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("keyHeightDp",40);
        numbersInMainView = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("numbersInMainView",true);
        automataToUppercase = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("automataToUppercase",false);
    }

    public void loadFromSettings()
    {
        customTexture = Settings.getInstance().getCustomTexture();
        customFontColor = Settings.getInstance().getCustomFontColor();
        customKeyColor = Settings.getInstance().getCustomKeyColor();
        keyHeightDp = Settings.getInstance().getKeyHeightDp();
        numbersInMainView = Settings.getInstance().getNumbersInMainView();
        theme = Settings.getInstance().getTheme();
        automataToUppercase = Settings.getInstance().getAutomataToUppercase();
    }

    /**
     * Persist variables to Program Data
     */
    public void savePreferences()
    {
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt("theme",theme).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt("customTexture",customTexture).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt("customFontColor",customFontColor).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt("customKeyColor",customKeyColor).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt("keyHeightDp",keyHeightDp).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("numbersInMainView",numbersInMainView).apply();
        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("automataToUppercase",automataToUppercase).apply();

    }
    public int getKeyHeightDp()
    {
        return keyHeightDp;
    }
    public void setKeyHeightDp(int value)
    {
        keyHeightDp = value;
    }
    @Override public void onDraw(Canvas canvas)
    {
        loadPreferences();

        //final float scale = getContext().getResources().getDisplayMetrics().density;
        //int keyHeight = (int) (keyHeightDp * scale + 0.5f);
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
            if(theme == 6)
            {
                Drawable drawable = null;
                switch(customTexture)
                {
                    case 0 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlwood,null); break;
                    case 1 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlmarvany,null); break;
                    case 2 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlfehermarvany,null); break;

                    case 3 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_carbon,null); break;
                    case 4 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_water,null); break;
                    case 5 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_water2,null); break;
                    case 6 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_pebbles,null); break;
                    case 7 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_bubbles,null); break;
                    case 8 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_lava,null); break;
                    case 9 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_fire,null); break;
                    case 10 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_mozaik,null); break;
                    case 11 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_feather,null); break;
                    case 12 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_macskany_fur,null); break;
                    case 13 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_leopard_fur,null); break;
                    case 14 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_tiger_fur,null); break;
                    case 15 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_dragon_skin_blue,null); break;
                    case 16 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_flower2,null); break;
                    case 17 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_serys_flower,null); break;

                    case 18 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_pergamen,null); break;
                    case 19 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_fractal,null); break;
                    case 20 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_abstract,null); break;
                    case 21 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_abstractfire,null); break;
                    case 22 : drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.xml_jade,null); break;

                }
                Drawable effect = ResourcesCompat.getDrawable(getResources(), R.drawable.xmlbutton_effect,null);
                if(drawable != null)
                {
                    drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    drawable.draw(canvas);
                    paint.setColor(customKeyColor);
                    canvas.drawRect(key.x, key.y, key.x + key.width, key.y + key.height,paint);
                    effect.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    effect.draw(canvas);
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
            else if((key.codes[0] == 5050) || (key.codes[0] == 5052) || (key.codes[0] == 6001) || (key.codes[0] == 6002))
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
            else if(theme == 6)
                paint.setColor(customFontColor);
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
