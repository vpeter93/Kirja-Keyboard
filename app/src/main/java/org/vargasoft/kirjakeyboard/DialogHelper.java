package org.vargasoft.kirjakeyboard;

import android.graphics.drawable.Drawable;

/**
 * Created by Peti on 2018. 02. 09..
 */

public class DialogHelper
{
    private String name;
    private Drawable flag;
    private int number;

    public DialogHelper(String name, Drawable flag,int number)
    {
        this.name = name;
        this.flag = flag;
        this.number = number;
    }
    public String getName() {
        return name;
    }

    public Drawable getFlag() {
        return flag;
    }

    public int getNumber()
    {
        return number;
    }
}
