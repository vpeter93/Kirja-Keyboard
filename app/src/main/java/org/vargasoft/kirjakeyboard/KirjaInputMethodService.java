package org.vargasoft.kirjakeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
    private boolean multiLineText;
    private boolean isFacebookMessenger;

    @Override public View onCreateInputView()
    {
        keyboardView = (KirjaKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboardView.loadPreferences();
        keyboardView.setPreviewEnabled(false);
        switchQwerty();

        isCapsLockOn = true;
        capsLock();
        return keyboardView;
    }

    @Override
    public void onStartInput(EditorInfo info, boolean restarting) {
        super.onStartCandidatesView(info, restarting);

        int inputType = info.inputType & InputType.TYPE_MASK_VARIATION;
       // int inputflags = info.inputType | InputType.TYPE_MASK_FLAGS;

        if(inputType == EditorInfo.TYPE_TEXT_VARIATION_URI)
        {
            multiLineText = false;
        }
        /*if(inputType == EditorInfo.TYPE_TEXT_VARIATION_NORMAL)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_VARIATION_NORMAL");
        }
        if(inputType == EditorInfo.TYPE_TEXT_VARIATION_LONG_MESSAGE)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_VARIATION_LONG_MESSAGE");
        }
        if(inputType == EditorInfo.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT");
        }
        if(inputType == EditorInfo.TYPE_NULL)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_NULL");
        }
        if((inputflags & EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE)!=0)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_FLAG_IME_MULTI_LINE");
        }
        if((inputflags & EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE)!=0)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_FLAG_MULTI_LINE");
        }
        if((inputType & EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE)!=0)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_FLAG_AUTO_COMPLETE");
        }
        if((inputType & EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES)!=0)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_FLAG_CAP_SENTENCES");
        }
        if(inputType == EditorInfo.TYPE_TEXT_VARIATION_SHORT_MESSAGE)
        {
            Log.wtf("..............","-----------------"+"EditorInfo.TYPE_TEXT_VARIATION_SHORT_MESSAGE");
        }*/
        else
        {
            multiLineText = true;
        }
        if(info.packageName.equals("com.facebook.orca") || inputType == EditorInfo.TYPE_TEXT_VARIATION_LONG_MESSAGE || inputType == EditorInfo.TYPE_TEXT_VARIATION_SHORT_MESSAGE)
        {
            isFacebookMessenger = true;
        }
        else
        {
            isFacebookMessenger = false;

        }

    }

    public void switchQwerty()
    {
        if(keyboardView.getKeyHeightDp() == 40)
        {
            if(!keyboardView.getNumbersInMainView())
                keyboard = new Keyboard(this, R.xml.qwerty);
            else keyboard = new Keyboard(this, R.xml.qwertywithnumbers);
        }
        else if(keyboardView.getKeyHeightDp() == 32)
        {
            if(!keyboardView.getNumbersInMainView())
                keyboard = new Keyboard(this, R.xml.qwertysmall);
            else keyboard = new Keyboard(this, R.xml.qwertywithnumberssmall);
        }
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
    }
    public void switchSpecific()
    {
        if(keyboardView.getKeyHeightDp() == 40)
            keyboard = new Keyboard(this, R.xml.specific);
        else if(keyboardView.getKeyHeightDp() == 32)
            keyboard = new Keyboard(this, R.xml.specificsmall);

        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
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
    @Override public boolean onKeyDown(int keyCode, KeyEvent event) {



        if(keyCode == KeyEvent.KEYCODE_ENTER)
            return false;


        return super.onKeyDown(keyCode, event);
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
                /*if(multiLineText && !isFacebookMessenger)
                   ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));*/
                if(/*!multiLineText &&*/ !isFacebookMessenger)
                    sendDefaultEditorAction(true);
                else if(isFacebookMessenger)
                {
                    ic.commitText("\n",1);
                }
            break;
            case 5012 :
                switchSpecific();
                capsLock();
            break;
            case 5013 :
                switchQwerty();
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
            case 6001 :
                String dotorg = ".org";
                if(isCapsLockOn)
                {
                    dotorg = dotorg.toUpperCase();
                }
                ic.commitText(dotorg,3);
                break;
            case 6002 :
                String doteu = ".eu";
                if(isCapsLockOn)
                {
                    doteu = doteu.toUpperCase();
                }
                ic.commitText(doteu,3);
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
            case 5028: 				ic.commitText("_",1); break;
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
            case 6000: 				ic.commitText("'",1); break;
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
    }

    @Override public void onPress(int primaryCode)
    {
        if (primaryCode == 5012 || primaryCode == 5013 || primaryCode == 4993 || primaryCode == -5 || primaryCode == -4 || primaryCode== 32)
        {

        }
        else
        {
            keyboardView.setPreviewEnabled(true);
        }
    }

    @Override public void onRelease(int primaryCode)
    {
        keyboardView.setPreviewEnabled(false);
    }

    @Override public void onText(CharSequence text) {}

    @Override public void swipeDown() {}

    @Override public void swipeLeft() {}

    @Override public void swipeRight() {}

    @Override public void swipeUp() {}
}
