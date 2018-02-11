package org.vargasoft.kirjakeyboard;

import android.app.ListActivity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peti on 2018. 02. 09..
 */

public class ListDialog extends ListActivity {

    public String[] names;
    private TypedArray imgs;
    private int[] numbers;
    private List<DialogHelper> list;
    private KirjaKeyboardView keyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        keyboardView = (KirjaKeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);

        populateList();

        ArrayAdapter<DialogHelper> adapter = new ListDialogAdapter(this, list);
        setListAdapter(adapter);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Settings.getInstance().setCustomTexture(list.get(position).getNumber());
                keyboardView.setCustomTexture(list.get(position).getNumber());
                keyboardView.loadFromSettings();
                keyboardView.savePreferences();
                keyboardView.invalidate();
                keyboardView.invalidateAllKeys();
                finish();
            }
        });
    }
    private void populateList()
    {
        list = new ArrayList<>();
        names = getResources().getStringArray(R.array.names);
        imgs = getResources().obtainTypedArray(R.array.flags);
        numbers = getResources().getIntArray(R.array.numbers);
        for(int i = 0; i < names.length; i++)
        {
            list.add(new DialogHelper(names[i], imgs.getDrawable(i),numbers[i]));
        }
    }
}