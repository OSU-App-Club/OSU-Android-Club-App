package osu_app_club.osuclubapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Random;


public class AppPageActivity extends ActionBarActivity {

    GridView mGridView;
    String[] foo = {"bbb", "aaa", "rrr"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_page);
        mGridView = (GridView) findViewById(R.id.iconGrid);
        mGridView.setNumColumns(new Random().nextInt(5));
        mGridView.setAdapter(new CustomAdapter(this, R.layout.app_page_grid_element, foo));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class CustomAdapter extends ArrayAdapter<String> {
        private CustomAdapter(Context context, int res, String[] data) {
            super(context, res, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_page_grid_element, parent, false);
            }
            TextView tv = (TextView)convertView.findViewById(R.id.appName);
            tv.setText(item);
            return convertView;
        }
    }
}
