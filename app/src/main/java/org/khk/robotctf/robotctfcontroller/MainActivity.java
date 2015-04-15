package org.khk.robotctf.robotctfcontroller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.khk.robotctf.robotctfcontroller.util.JSO;
import org.khk.robotctf.robotctfcontroller.util.NodeNotFound;
import org.khk.robotctf.robotctfcontroller.util.malformedJSON;

import java.io.InputStream;


public class MainActivity extends ActionBarActivity{

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private JSO robots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = "Select a robot";

        try {
            InputStream robotsJSON = getResources().openRawResource(R.raw.robots);
            robots = new JSO(robotsJSON);
        } catch (malformedJSON e) {
            e.printStackTrace();
        }

        ListAdapter adapter = null;
        try {
            adapter = new loadout_list_view_adapter(this, robots);
        } catch (NodeNotFound nodeNotFound) {
            nodeNotFound.printStackTrace();
        }
        ListView roboList = ((ListView)findViewById(R.id.loadout));
        roboList.setAdapter(adapter);
        roboList.setOnItemClickListener((AdapterView.OnItemClickListener) adapter);
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refresh();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void refresh(){
        Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();
    }

    public void openSettings() {
        Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_SHORT).show();
    }
}
