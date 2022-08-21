package org.vargasoft.colorpicker;

import androidx.annotation.IntRange;

/**
 * Created by Peti on 2018. 02. 18..
 */

public class ColorFormat
{
    static int assertColorValueInRange(@IntRange(from = 0, to = 255) int colorValue)
    {
        return ((0 <= colorValue) && (colorValue <= 255)) ? colorValue : 0;
    }
    static String formatColorValues(@IntRange(from = 0, to = 255) int alpha, @IntRange(from = 0, to = 255) int red,
                                    @IntRange(from = 0, to = 255) int green, @IntRange(from = 0, to = 255) int blue)
    {
        return String.format
        (
                "%02X%02X%02X%02X",
                assertColorValueInRange(alpha),
                assertColorValueInRange(red),
                assertColorValueInRange(green),
                assertColorValueInRange(blue)
        );
    }
    static String formatColorValues(@IntRange(from = 0, to = 255) int red, @IntRange(from = 0, to = 255) int green,
            @IntRange(from = 0, to = 255) int blue)
    {

        return String.format
        (
                "%02X%02X%02X",
                assertColorValueInRange(red),
                assertColorValueInRange(green),
                assertColorValueInRange(blue)
        );
    }
}
