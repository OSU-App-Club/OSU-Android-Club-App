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

import osu_app_club.osuclubapp.R;
import osu_app_club.osuclubapp.fragments.NavBar;
import osu_app_club.osuclubapp.models.NewsObject;

//Ben's Friedman

//todo implement the OnFragmentInteractionListener
public class MainActivity extends ActionBarActivity implements NavBar.OnFragmentInteractionListener{

    //TODO make new packages for individual components (in your own branch of course)

    //todo GO OVER SETTING UP THE HOLDER
    static class CellHolder {
        public TextView tv;
        public ImageView iv;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gl = (GridView)findViewById(R.id.main_menu_grid);

        //todo you may set column nums here as well!
        gl.setNumColumns(3);

        //todo change to array of our custom objects
        NewsObject[] newsObjects = new NewsObject[100];
        //set our objects up
        int len = newsObjects.length;
        for(byte x = 0; x < len; x++) {
            NewsObject no = new NewsObject("item"+x, R.drawable.ic_launcher);
            newsObjects[x] = no;
        }

        gl.setAdapter(new CustomAdapter(this, R.layout.mainmenucell, newsObjects));
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

    //todo change generic type to a custom class
    private class CustomAdapter extends ArrayAdapter<NewsObject> {

        //todo change the input here as well to our 'Object'
        private CustomAdapter(Context context, int resource, NewsObject[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //todo change this to our 'Object' generic type
            NewsObject item = getItem(position);

            //todo place cell holder here
            CellHolder ch = null;

            if(convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate( R.layout.mainmenucell, parent, false);

                //todo move these up here
                //fetch a reference to our TextView for our cell
                TextView tv = (TextView)convertView.findViewById(R.id.mainmenu_tv);
                //fetch a reference to our ImageView for our cell
                ImageView iv = (ImageView)convertView.findViewById(R.id.mainmenu_image);

                //todo get a handle to our cell holder
                ch = new CellHolder();
                //todo set our values
                ch.tv = tv;
                ch.iv = iv;

                //set the tag
                convertView.setTag(ch);
            }

            //todo get our cellholder back and the lines below, don't need to keep refetching our reference to our TextViews & ImageViews
            if(ch == null)
                ch = (CellHolder)convertView.getTag();

            ch.tv.setText(item.getTitle());
            ch.iv.setImageResource(item.getImageRes());

            return convertView;
        }
    }

    //todo implement this
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
