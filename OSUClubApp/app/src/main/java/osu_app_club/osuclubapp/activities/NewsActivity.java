package osu_app_club.osuclubapp.activities;

import android.content.Context;
import android.net.Uri;
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

import java.util.List;

import osu_app_club.osuclubapp.R;
import osu_app_club.osuclubapp.fragments.NavBar;
import osu_app_club.osuclubapp.interfaces.AppDataDelegate;
import osu_app_club.osuclubapp.models.NewsObject;
import osu_app_club.osuclubapp.utilities.AppData;

//Ben's Friedman

//todo implement the OnFragmentInteractionListener
public class NewsActivity extends ActionBarActivity implements NavBar.OnFragmentInteractionListener, AppDataDelegate{

    static class CellHolder {
        public TextView tv;
        public ImageView iv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppData.setCallbackListener(this, getApplication());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private class CustomAdapter extends ArrayAdapter<NewsObject> {

        private CustomAdapter(Context context, int resource, List<NewsObject> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            NewsObject item = getItem(position);
            CellHolder ch = null;

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate( R.layout.newstableviewcell, parent, false);
                //fetch a reference to our TextView for our cell
                TextView tv = (TextView)convertView.findViewById(R.id.mainmenu_tv);
                //fetch a reference to our ImageView for our cell
                ImageView iv = (ImageView)convertView.findViewById(R.id.mainmenu_image);

                ch = new CellHolder();
                ch.tv = tv;
                ch.iv = iv;

                //set the tag
                convertView.setTag(ch);
            }

            if(ch == null)
                ch = (CellHolder)convertView.getTag();

            ch.tv.setText(item.getTitle());
            //todo we'll need these images at some point here...
            //ch.iv.setImageResource(item.getImageRes());

            return convertView;
        }
    }

    //todo implement this
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAppDataFetched() {
        GridView gl = (GridView)findViewById(R.id.main_menu_grid);

        //todo you may set column nums here as well!
        gl.setNumColumns(1);

        AppData appData = AppData.getInstance();
        gl.setAdapter(new CustomAdapter(this, R.layout.newstableviewcell, appData.getNewsData()));
    }
}
