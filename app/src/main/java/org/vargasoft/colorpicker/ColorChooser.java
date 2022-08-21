package org.vargasoft.colorpicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.vargasoft.kirjakeyboard.R;

import static org.vargasoft.colorpicker.ColorFormat.formatColorValues;

/**
 * Created by Peti on 2018. 02. 18..
 */

public class ColorChooser extends Dialog implements SeekBar.OnSeekBarChangeListener
{
    private final Activity activity;

    private View colorView;
    private SeekBar alphaSeekBar, redSeekBar, greenSeekBar, blueSeekBar;
    private EditText hexCode;
    private TextView alphaTextView;
    private int alpha, red, green, blue;
    private ColorChooserListener listener;

    private boolean withAlpha;

    public ColorChooser(Activity activity)
    {
        super(activity);

        this.activity = activity;

        if (activity instanceof ColorChooserListener)
        {
            listener = (ColorChooserListener) activity;
        }

        this.alpha = 255;
        this.red = 0;
        this.green = 0;
        this.blue = 0;

        this.withAlpha = false;
    }

    public ColorChooser(Activity activity,
                       @IntRange(from = 0, to = 255) int red,
                       @IntRange(from = 0, to = 255) int green,
                       @IntRange(from = 0, to = 255) int blue)
    {
        this(activity);

        this.red = ColorFormat.assertColorValueInRange(red);
        this.green = ColorFormat.assertColorValueInRange(green);
        this.blue = ColorFormat.assertColorValueInRange(blue);

    }

    public ColorChooser(Activity activity,
                       @IntRange(from = 0, to = 255) int alpha,
                       @IntRange(from = 0, to = 255) int red,
                       @IntRange(from = 0, to = 255) int green,
                       @IntRange(from = 0, to = 255) int blue)
    {
        this(activity);

        this.alpha = ColorFormat.assertColorValueInRange(alpha);
        this.red = ColorFormat.assertColorValueInRange(red);
        this.green = ColorFormat.assertColorValueInRange(green);
        this.blue = ColorFormat.assertColorValueInRange(blue);

        this.withAlpha = true;

    }

    public void setListener(ColorChooserListener listener)
    {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        setContentView(R.layout.color_chooser_layout);

        colorView = findViewById(R.id.colorView);

        hexCode = (EditText) findViewById(R.id.hexCode);

        alphaSeekBar = (SeekBar) findViewById(R.id.alphaSeekBar);
        redSeekBar = (SeekBar) findViewById(R.id.redSeekBar);
        greenSeekBar = (SeekBar) findViewById(R.id.greenSeekBar);
        blueSeekBar = (SeekBar) findViewById(R.id.blueSeekBar);
        alphaTextView = (TextView) findViewById(R.id.colorChooserTextAlpha);
        alphaSeekBar.setOnSeekBarChangeListener(this);
        redSeekBar.setOnSeekBarChangeListener(this);
        greenSeekBar.setOnSeekBarChangeListener(this);
        blueSeekBar.setOnSeekBarChangeListener(this);

        hexCode.setFilters(new InputFilter[]{new InputFilter.LengthFilter(withAlpha ? 8 : 6)});

        hexCode.setOnEditorActionListener
                (
                new EditText.OnEditorActionListener()
                {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            updateColorView(v.getText().toString());
                            InputMethodManager imm = (InputMethodManager) activity
                                    .getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(hexCode.getWindowToken(), 0);

                            return true;
                        }
                        return false;
                    }
                });

        final Button okColor = (Button) findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendColor();
            }
        });
    }

    private void initUi()
    {
        colorView.setBackgroundColor(getColor());

        alphaSeekBar.setProgress(alpha);
        redSeekBar.setProgress(red);
        greenSeekBar.setProgress(green);
        blueSeekBar.setProgress(blue);

        if (!withAlpha)
        {
            alphaSeekBar.setVisibility(View.GONE);
            alphaTextView.setVisibility(View.GONE);
        }

        hexCode.setText(withAlpha
                ? formatColorValues(alpha, red, green, blue)
                : formatColorValues(red, green, blue)
        );
    }
    private void sendColor()
    {
        if (listener != null)
            listener.onColorChosen(getColor());
    }

    public void setColor(@ColorInt int color)
    {
        alpha = Color.alpha(color);
        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);
    }

    private void updateColorView(String input)
    {
        try
        {
            final int color = Color.parseColor('#' + input);
            alpha = Color.alpha(color);
            red = Color.red(color);
            green = Color.green(color);
            blue = Color.blue(color);

            colorView.setBackgroundColor(getColor());

            alphaSeekBar.setProgress(alpha);
            redSeekBar.setProgress(red);
            greenSeekBar.setProgress(green);
            blueSeekBar.setProgress(blue);
        }
        catch (IllegalArgumentException ignored)
        {
            hexCode.setError(activity.getResources().getText(R.string.ColorChooser_errorhex));
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {

        if (seekBar.getId() == R.id.alphaSeekBar)
        {

            alpha = progress;

        }
        else if (seekBar.getId() == R.id.redSeekBar)
        {

            red = progress;

        }
        else if (seekBar.getId() == R.id.greenSeekBar)
        {

            green = progress;

        }
        else if (seekBar.getId() == R.id.blueSeekBar)
        {

            blue = progress;

        }

        colorView.setBackgroundColor(getColor());

        //Setting the inputText hex color
        hexCode.setText(withAlpha
                ? formatColorValues(alpha, red, green, blue)
                : formatColorValues(red, green, blue)
        );

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }
    public int getColor() {
        return withAlpha ? Color.argb(alpha, red, green, blue) : Color.rgb(red, green, blue);
    }

    @Override
    public void show() {
        super.show();
        initUi();
    }
    public int getBlue() {
        return blue;
    }
    public int getGreen() {
        return green;
    }
    public int getRed() {
        return red;
    }
    public int getAlpha() {
        return alpha;
    }
}
