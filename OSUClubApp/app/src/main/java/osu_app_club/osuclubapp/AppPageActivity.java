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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AppPageActivity extends ActionBarActivity {

    private static class CellHolder {
        public final TextView tv;
        public final ImageView iv;

        public CellHolder(TextView tv, ImageView iv) {
            this.tv = tv;
            this.iv = iv;
        }
    }

    private GridView mGridView;
    private List<AppCell> mCells;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_page);
        mGridView = (GridView) findViewById(R.id.iconGrid);
        mGridView.setNumColumns(new Random().nextInt(5));

        mCells = new ArrayList<AppCell>();
        for (int i=0; i<256; i++) {
            mCells.add(new AppCell("" + i, R.drawable.ic_launcher));
        }
        mAdapter = new CustomAdapter(this, R.layout.app_page_grid_element, mCells);
        mGridView.setAdapter(mAdapter);
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
    private class CustomAdapter extends ArrayAdapter<AppCell> {
        private CustomAdapter(Context context, int res, List<AppCell> data) {
            super(context, res, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AppCell item = getItem(position);

            CellHolder ch;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_page_grid_element, parent, false);

                // Fetch views
                TextView tv = (TextView)convertView.findViewById(R.id.appName);
                ImageView iv = (ImageView)convertView.findViewById(R.id.appIcon);

                // Create and store holder
                ch = new CellHolder(tv, iv);
                convertView.setTag(ch);
            }
            else {
                // Retrieve holder
                ch = (CellHolder)convertView.getTag();
            }
            // Initialize views
            ch.tv.setText(item.name);
            ch.iv.setImageResource(item.image);
            return convertView;
        }
    }
}
