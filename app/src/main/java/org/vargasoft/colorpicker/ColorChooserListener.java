package org.vargasoft.colorpicker;

import androidx.annotation.ColorInt;

/**
 * Created by Peti on 2018. 02. 18..
 */

public interface ColorChooserListener
{
    void onColorChosen(@ColorInt int color);
}
