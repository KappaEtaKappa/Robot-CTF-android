package org.khk.robotctf.robotctfcontroller;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.khk.robotctf.robotctfcontroller.util.JSO;
import org.khk.robotctf.robotctfcontroller.util.NodeNotFound;

/**
 * Created by Jack on 4/8/2015.
 */
public class loadout_list_view_adapter extends ArrayAdapter<JSO> implements AdapterView.OnItemClickListener {

    private final Activity context;
    private final JSO robs;

    public loadout_list_view_adapter(Activity context, JSO robs) throws NodeNotFound{
        super(context, R.layout.activity_main, robs.o("robots").children());
        this.context = context;
        this.robs = robs.o("robots");
    }

    @Override
    public View getView(int position,View view,ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.robot_loadout_listview_element, null, true);

        try {
            ((TextView) rowView.findViewById(R.id.robot_loadout_item_name)).setText((String)robs.o(position).get("name"));
            ((TextView) rowView.findViewById(R.id.robot_loadout_item_mac)).setText((String)robs.o(position).get("MAC"));
            ImageView team = (ImageView) rowView.findViewById(R.id.robot_loadout_item_icon);
            team.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            if(1 == (double)robs.o(position).get("team"))
                team.setImageResource(R.drawable.team_1);
            else
                team.setImageResource(R.drawable.team_2);

        }catch (NodeNotFound e){
            ((TextView) rowView.findViewById(R.id.robot_loadout_item_name)).setText("{unknown}");
            ((TextView) rowView.findViewById(R.id.robot_loadout_item_mac)).setText("{unknown}");
//            ((ImageView) rowView.findViewById(R.id.robot_loadout_item_icon)).setImageResource(R.drawable.team_1);
        }
        return rowView;
    };



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this.context, "" + position, Toast.LENGTH_SHORT).show();

        Intent openController = new Intent(context, ControllerActivity.class);
        context.startActivity(openController);

    }
}
