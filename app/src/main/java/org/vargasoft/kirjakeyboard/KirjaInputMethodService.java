package org.vargasoft.kirjakeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.view.View;
import android.view.inputmethod.InputConnection;

import org.vargasoft.kirjakeyboard.R;

/**
 * My own InputMethodService.
 * Created by Varga Péter on 2017. 04. 21..
 */

public class KirjaInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener
{
    private KirjaKeyboardView keyboardView;
    private Keyboard keyboard;

    //Hungarian character codes
    private final int KEY_KOTOJEL 		  = 4994;
    private final int KEY_Ü 			  = 5008;
    public static final int KEY_CAPS_LOCK = 5009;
    private final int KEY_LEFT_PAREN 	  = 5010;
    private final int KEY_RIGHT_PAREN 	  = 5011;

    private boolean isCapsLockOn 		  = false;

    @Override public View onCreateInputView()
    {
        keyboardView = (KirjaKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        capsLock();
        return keyboardView;
    }
	/**Play keyClick sound**/
    private void playClick(int keyCode)
    {
        AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
        switch(keyCode)
        {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }
    private char hungarianCharacters(int primaryCode)
    {
        switch (primaryCode)
        {
            case KEY_KOTOJEL: 		return '-';
            case 4995: 		        return '"';
            case 4996: 	            return '\\';
            case 4997: 			    return '*';
            case 4998: 			    return 'ä';
            case 4999: 	            return ';';
            case 5000: 			    return 'á';
            case 5001: 			    return 'é';
            case 5002: 			    return 'ű';
            case 5003: 			    return 'í';
            case 5004: 			    return 'ú';
            case 5005: 			    return 'ó';
            case 5006: 			    return 'ő';
            case 5007: 			    return 'ö';
            case KEY_Ü: 			return 'ü';
            case KEY_LEFT_PAREN:    return '(';
            case KEY_RIGHT_PAREN:   return ')';
            case 5020: 				return '<';
            case 5021: 				return '>';
            case 5022: 				return '{';
            case 5023: 				return '}';
            case 5024: 				return '[';
            case 5025: 				return ']';
            case 5026: 				return '/';
            case 5027: 				return '+';
            case 5028: 				return '-';
            case 5029: 				return '*';
            case 5030: 				return '~';
            case 5031:				return '|';
            case 5032: 				return '%';
            case 5033: 				return '=';
            case 5034: 				return '$';
            case 5035: 				return '#';
            case 5036: 				return '&';
            case 5037: 				return '÷';
            case 5038: 				return '×';
            case 5039: 				return '§';
            case 5040: 				return '&';
            case 5041: 				return '°';
            case 5042: 				return 'đ';
            case 5043: 				return 'Đ';
            case 5044: 				return 'ł';
            case 5045: 				return 'Ł';
            case 5046: 				return 'ß';
            case 5047: 				return '¤';
            case 5048: 				return '€';
            case 5049: 				return '☺';
            default: 				return ' ';
        }
    }
    private void capsLock()
    {
        if(isCapsLockOn)
        {
            for (Keyboard.Key key : keyboard.getKeys())
            {
                if(!(key.codes[0] == 5054) && !(key.codes[0] == 5053) && !(key.codes[0] == 4993) &&
                        ( !(key.codes[0] == -4) && !(key.codes[0] == -5) &&
                                !(key.codes[0] == KEY_CAPS_LOCK) && !(key.codes[0] == 5012) &&
                                !(key.codes[0] == 5013)))
                {
                    if(key.label != null)
                        key.label = key.label.toString().toUpperCase();
                }
            }
            keyboardView.invalidateAllKeys();
        }
        else
        {
            for (Keyboard.Key key : keyboard.getKeys())
            {
                if(!(key.codes[0] == 5054) && !(key.codes[0] == 5053)
                        && !(key.codes[0] == 4993) && !(key.codes[0] == -4) &&
                        !(key.codes[0] == -5) && !(key.codes[0] == KEY_CAPS_LOCK) &&
                        !(key.codes[0] == 5012) && !(key.codes[0] == 5013))
                {
                    if(key.label != null)
                        key.label = key.label.toString().toLowerCase();
                }
            }
            keyboardView.invalidateAllKeys();
        }
    }
    @Override public void onKey(int primaryCode, int[] keyCodes)
    {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        if(primaryCode == 5012)//switch view
        {
            keyboard = new Keyboard(this, R.xml.specific);
            keyboardView.setKeyboard(keyboard);
            keyboardView.setOnKeyboardActionListener(this);
            capsLock();
        }
        else if(primaryCode == 5013)//switch view
        {
            keyboard = new Keyboard(this, R.xml.qwerty);
            keyboardView.setKeyboard(keyboard);
            keyboardView.setOnKeyboardActionListener(this);
            capsLock();
        }
        else if(primaryCode == 4993)//switch theme
        {
            keyboardView.switchTheme();
            keyboardView.invalidate();
            keyboardView.invalidateAllKeys();
            keyboardView.savePreferences();
        }
        else if((primaryCode >= KEY_KOTOJEL && primaryCode <= KEY_Ü)
                || (primaryCode <= 5049 && primaryCode >= 5020) ||
                primaryCode == KEY_LEFT_PAREN || primaryCode == KEY_RIGHT_PAREN)
        {
            char code = hungarianCharacters(primaryCode);
            if(isCapsLockOn)
            {
                code = Character.toUpperCase(code);
            }
            ic.commitText(String.valueOf(code),1);
        }
        else if(primaryCode == 5050)
        {
            String dotcom = ".com";
            if(isCapsLockOn)
            {
                dotcom = dotcom.toUpperCase();
            }
            ic.commitText(dotcom,4);
        }
        else if(primaryCode == 5052)
        {
            String dothu = ".hu";
            if(isCapsLockOn)
            {
                dothu = dothu.toUpperCase();
            }
            ic.commitText(dothu,3);
        }
        else if(primaryCode == 5051)
        {
            ic.commitText("😘",2);
        }
        else if(primaryCode == 5053)
        {
            ic.commitText("\uD83D\uDE07",2);//O:)
        }
        else if(primaryCode == 5054)
        {
            ic.commitText("\uD83D\uDE01",2);//:D
        }
        else if(primaryCode == 5055)
        {
            ic.commitText(" :( ️",4);
        }
        else
        {
            switch(primaryCode)
            {
                case KEY_CAPS_LOCK:
                    isCapsLockOn = !isCapsLockOn;
                    capsLock();
                break;
                case Keyboard.KEYCODE_DELETE :
                    ic.deleteSurroundingText(1, 0);
                break;
                case -4://ENTER KEY
                    sendDefaultEditorAction(true);
                break;
                default:
                    char code = (char)primaryCode;
                    if(Character.isLetter(code) && isCapsLockOn)
                    {
                        code = Character.toUpperCase(code);
                    }
                    ic.commitText(String.valueOf(code),1);
                break;
            }
        }
    }

    @Override public void onPress(int primaryCode) {}

    @Override public void onRelease(int primaryCode) {}

    @Override public void onText(CharSequence text) {}

    @Override public void swipeDown() {}

    @Override public void swipeLeft() {}

    @Override public void swipeRight() {}

    @Override public void swipeUp() {}
}
