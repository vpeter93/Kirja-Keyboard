package org.vargasoft.kirjakeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;
import android.view.inputmethod.InputConnection;

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
        isCapsLockOn = true;
        capsLock();
        return keyboardView;
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


        char code;
        switch(primaryCode)
        {

            case 32 :
                ic.commitText(" ",1);
            break;
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
            case 5012 :
                keyboard = new Keyboard(this, R.xml.specific);
                keyboardView.setKeyboard(keyboard);
                keyboardView.setOnKeyboardActionListener(this);
                capsLock();
            break;
            case 5013 :
                keyboard = new Keyboard(this, R.xml.qwerty);
                keyboardView.setKeyboard(keyboard);
                keyboardView.setOnKeyboardActionListener(this);
                capsLock();
            break;
            case 4993 :
                keyboardView.switchTheme();
                keyboardView.invalidate();
                keyboardView.invalidateAllKeys();
                keyboardView.savePreferences();
            break;
            case 5050 :
                String dotcom = ".com";
                if(isCapsLockOn)
                {
                    dotcom = dotcom.toUpperCase();
                }
                ic.commitText(dotcom,4);
            break;
            case 5051 :
                ic.commitText("😘",2);
            break;
            case 5052 :
                String dothu = ".hu";
                if(isCapsLockOn)
                {
                    dothu = dothu.toUpperCase();
                }
                ic.commitText(dothu,3);
            break;
            case 5053 :
                ic.commitText("\uD83D\uDE07",2);//O:)
            break;
            case 6058 :
                ic.commitText("^",1);
            break;
            case 5054 :
                ic.commitText("\uD83D\uDE01",2);//:D
            break;
            case 5055 :
                ic.commitText(" :( ️",4);
            break;
            case 5056 :
                ic.commitText("❤️",4);
            break;
            case 5057 :
                ic.commitText("\uD83D\uDC9C️",4);
            break;
            case KEY_KOTOJEL:
                ic.commitText("-",1);
            break;
            case 4995:
                ic.commitText("\"",1);
            break;
            case 4996:
                ic.commitText("\\",1);
            break;
            case 4997:
                ic.commitText("*",1);
            break;
            case 4998:
                code = 'ä';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 4999:
                ic.commitText(";",1);
            break;
            case 5000:
                code = 'á';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 5001:
                code = 'é';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 5002:
                code = 'ű';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 5003:
                code = 'í';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 5004:
                code = 'ú';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 5005:
                code = 'ó';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 5006:
                code = 'ő';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case 5007:
                code = 'ö';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
            case KEY_Ü:
                code = 'ü';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
            break;
            case KEY_LEFT_PAREN:    ic.commitText("(",1); break;
            case KEY_RIGHT_PAREN:   ic.commitText(")",1); break;
            case 5020: 				ic.commitText("<",1); break;
            case 5021: 				ic.commitText(">",1); break;
            case 5022: 				ic.commitText("{",1); break;
            case 5023: 				ic.commitText("}",1); break;
            case 5024: 				ic.commitText("[",1); break;
            case 5025: 				ic.commitText("]",1); break;
            case 5026: 				ic.commitText("/",1); break;
            case 5027: 				ic.commitText("+",1); break;
            case 5028: 				ic.commitText("-",1); break;
            case 5029: 				ic.commitText("*",1); break;
            case 5030: 				ic.commitText("~",1); break;
            case 5031:				ic.commitText("|",1); break;
            case 5032: 				ic.commitText("%",1); break;
            case 5033: 				ic.commitText("=",1); break;
            case 5034: 				ic.commitText("$",1); break;
            case 5035: 				ic.commitText("#",1); break;
            case 5036: 				ic.commitText("&",1); break;
            case 5037: 				ic.commitText("÷",1); break;
            case 5038: 				ic.commitText("×",1); break;
            case 5039: 				ic.commitText("§",1); break;
            case 5040: 				ic.commitText("&",1); break;
            case 5041: 				ic.commitText("°",1); break;
            case 5042:
                code = 'đ';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
            break;
            case 5043:
                code = 'Đ';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
            break;
            case 5044:
                code = 'ł';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
            break;
            case 5045:
                code = 'Ł';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
            break;
            case 5046:
                code = 'ß';
                if(isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
            break;
            case 5047: 				ic.commitText("¤",1); break;
            case 5048: 				ic.commitText("€",1); break;
            case 5049: 				ic.commitText("☺",1); break;
            default:
                code = (char)primaryCode;
                if(Character.isLetter(code) && isCapsLockOn)
                {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code),1);
            break;
        }



        /*if((primaryCode >= KEY_KOTOJEL && primaryCode <= KEY_Ü)
                || (primaryCode <= 5049 && primaryCode >= 5020) ||
                primaryCode == KEY_LEFT_PAREN || primaryCode == KEY_RIGHT_PAREN)
        {
            char code = hungarianCharacters(primaryCode);
            if(isCapsLockOn)
            {
                code = Character.toUpperCase(code);
            }
            ic.commitText(String.valueOf(code),1);
        }*/

    }

    @Override public void onPress(int primaryCode) {}

    @Override public void onRelease(int primaryCode) {}

    @Override public void onText(CharSequence text) {}

    @Override public void swipeDown() {}

    @Override public void swipeLeft() {}

    @Override public void swipeRight() {}

    @Override public void swipeUp() {}
}
