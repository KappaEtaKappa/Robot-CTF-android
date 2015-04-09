package org.khk.robotctf.robotctfcontroller;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jack on 4/8/2015.
 */
public class loadout_list_view_adapter extends ArrayAdapter<robot> {

    private final Activity context;
    private final robot[] robs;

    public loadout_list_view_adapter(Activity context, robot[] robs) {
        super(context, R.layout.activity_main, robs);

        this.context = context;
        this.robs = robs;
    }

    @Override
    public View getView(int position,View view,ViewGroup parent) {



        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.robot_loadout_listview_element, null,true);

        ((TextView) rowView.findViewById(R.id.robot_loadout_item_name)).setText(robs[position].name);
        ((TextView) rowView.findViewById(R.id.robot_loadout_item_mac)).setText(robs[position].MAC);
        ((Button) rowView.findViewById(R.id.robot_loadout_item_icon)).setBackgroundColor(Color.MAGENTA);

        return rowView;

    };

}
