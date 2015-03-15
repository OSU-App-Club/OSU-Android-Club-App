package osu_app_club.osuclubapp.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import osu_app_club.osuclubapp.R;
import osu_app_club.osuclubapp.interfaces.AppDataCallback;
import osu_app_club.osuclubapp.models.ProjectObject;
import osu_app_club.osuclubapp.utilities.AppData;
/**
 * Created by Bfriedman on 3/14/15.
 */
public class MOCK_ProjectsActivity extends Fragment implements AppDataCallback {
    static class CellHolder {
        public TextView tv;
        public ImageView iv;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //todo sets up pre-onViewCreated
        LayoutInflater li = getActivity().getLayoutInflater();
        View v = li.inflate(R.layout.news_menu_layout, null);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //todo similar to -onCreate-
        //todo this callback will fire IMMEDIATELY if data is already available, needs to be placed AFTER view creation in case that happens, otherwise it just shoots a null pointer error since our view isn't set yet!
        AppData.setCallbackListener( this, getActivity().getApplication());
    }

    private class CustomAdapter extends ArrayAdapter<ProjectObject> {

        private CustomAdapter(Context context, int resource, List<ProjectObject> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ProjectObject item = getItem(position);
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

            ch.tv.setText(item.getName());
            //todo we'll need these images at some point here...
            //ch.iv.setImageResource(item.getImageRes());

            return convertView;
        }
    }

    @Override
    public void onAppDataFetched() {
        GridView gl = (GridView)getActivity().findViewById(R.id.main_menu_grid);

        //todo you may set column nums here as well!
        if(gl != null) {
            gl.setNumColumns(1);

            AppData appData = AppData.getInstance();
            gl.setAdapter(new CustomAdapter(getActivity().getApplication(), R.layout.newstableviewcell, appData.getProjectsData()));
        }
    }
}
