package org.vargasoft.kirjakeyboard;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Peti on 2018. 02. 09..
 */

public class ListDialogAdapter extends ArrayAdapter<DialogHelper> {

    private final List<DialogHelper> list;
    private final Activity context;

    static class ViewHolder
    {
        protected TextView name;
        protected ImageView flag;
        protected int number;
    }

    public ListDialogAdapter(Activity context, List<DialogHelper> list)
    {
        super(context, R.layout.dialog_layout, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = null;

        if (convertView == null)
        {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.dialog_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.name = view.findViewById(R.id.name);
            viewHolder.flag = view.findViewById(R.id.flag);
            view.setTag(viewHolder);
        }
        else
        {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.name.setText(list.get(position).getName());
        holder.flag.setImageDrawable(list.get(position).getFlag());
        holder.number = list.get(position).getNumber();
        return view;
    }
}
