package org.vargasoft.kirjakeyboard;

import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class SettingsActivity extends AppCompatActivity
{

    private ColorPicker fontColorPicker;
    private ColorPicker keyColorPicker;
    private KirjaKeyboardView keyboardView;
    private Intent listActivityIntent;
    private Switch switch1;
    private Switch switch2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        keyboardView = (KirjaKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        listActivityIntent = new Intent(this, ListDialog.class);
        keyboardView.loadPreferences();

        switch1.setChecked(keyboardView.getKeyHeightDp() == 40);
        switch2.setChecked(keyboardView.getNumbersInMainView());
        int fontColor = keyboardView.getCustomFontColor();
        int keyColor = keyboardView.getCustomKeyColor();

        fontColorPicker = new ColorPicker(this, Color.red(fontColor),Color.green(fontColor),Color.blue(fontColor));
        keyColorPicker = new ColorPicker(this,Color.alpha(keyColor),Color.red(keyColor),Color.green(keyColor),Color.blue(keyColor));
        fontColorPicker.setCallback(new ColorPickerCallback()
        {
            @Override
            public void onColorChosen(@ColorInt int color)
            {
                Settings.getInstance().setCustomFontColor(color);
                keyboardView.setCustomFontColor(color);
                save();
                fontColorPicker.dismiss();
            }
        });
        keyColorPicker.setCallback(new ColorPickerCallback()
        {
            @Override
            public void onColorChosen(@ColorInt int color)
            {
                Settings.getInstance().setCustomKeyColor(color);
                keyboardView.setCustomKeyColor(color);
                save();
                keyColorPicker.dismiss();
            }
        });
    }
    public void save()
    {
        keyboardView.loadFromSettings();
        keyboardView.savePreferences();
        keyboardView.invalidate();
        keyboardView.invalidateAllKeys();
    }


    public void setFontColor(View view)
    {
        fontColorPicker.show();
    }
    public void setKeyColor(View view)
    {
        keyColorPicker.show();
    }
    public void switchTexture(View view)
    {
        startActivity(listActivityIntent);
    }
    public void switchKeyHeight(View view)
    {

        if(switch1.isChecked())
        {
            Settings.getInstance().setKeyHeightDp(40);
            keyboardView.setKeyHeightDp(40);
        }
        else
        {
            Settings.getInstance().setKeyHeightDp(32);
            keyboardView.setKeyHeightDp(32);
        }
        save();
    }
    public void switchNumbersInMain(View view)
    {
        Settings.getInstance().setNumbersInMainView(switch2.isChecked());
        keyboardView.setNumbersInMainView(switch2.isChecked());
        save();
    }

}
