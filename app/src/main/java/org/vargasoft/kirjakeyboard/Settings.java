package org.vargasoft.kirjakeyboard;

import android.graphics.Color;

/**
 * Created by Peti on 2018. 02. 11..
 */

public class Settings
{
    private static Settings instance;
    private int customFontColor = Color.BLACK;
    private int customKeyColor = Color.WHITE;
    private int customTexture = 0;
    private int keyHeightDp = 40;
    private boolean numbersInMainView = true;
    private int theme = 0;

    public static Settings getInstance()
    {
        if(instance == null)
        {
            instance = new Settings();
        }
        return instance;
    }
    public void setCustomFontColor(int color)
    {
        customFontColor = color;
    }
    public int getCustomFontColor(){return customFontColor;}
    public int getCustomKeyColor(){return customKeyColor;}
    public void setCustomKeyColor(int color)
    {
        customKeyColor = color;
    }
    public boolean getNumbersInMainView(){return numbersInMainView;}
    public void setNumbersInMainView(boolean value){ numbersInMainView = value;}
    public void setCustomTexture(int value){customTexture = value;}
    public int getCustomTexture(){return customTexture;}
    public int getKeyHeightDp(){return keyHeightDp;}
    public void setKeyHeightDp(int value){keyHeightDp = value;}
    public void setTheme(int value){theme = value;}
    public int getTheme(){return theme;}
}
