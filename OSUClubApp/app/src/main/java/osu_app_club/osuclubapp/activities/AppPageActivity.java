package osu_app_club.osuclubapp.activities;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import osu_app_club.osuclubapp.AppCell;
import osu_app_club.osuclubapp.R;
import osu_app_club.osuclubapp.interfaces.AppDataCallback;
import osu_app_club.osuclubapp.models.ProjectObject;
import osu_app_club.osuclubapp.utilities.AppData;


public class AppPageActivity extends Fragment implements AppDataCallback {

    private GridView mGridView;
    private List<AppCell> mCells;
    private CustomAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //LayoutInflater li = getActivity().getLayoutInflater();
        //View v = li.inflate(R.layout.activity_app_page, null);

        return inflater.inflate(R.layout.activity_app_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppData.setCallbackListener(this, getActivity().getApplication());
    }

    @Override
    public void onAppDataFetched() {
        AppData data = AppData.getInstance();

        mGridView = (GridView)getActivity().findViewById(R.id.iconGrid);
        mCells = new ArrayList<>();
        mGridView.setNumColumns(2);
        for (ProjectObject project : data.getProjectsData()) {
            mCells.add(new AppCell(project.getName(), R.drawable.ic_launcher));
        }
        mAdapter = new CustomAdapter(getActivity().getApplication(), R.layout.app_page_grid_element, mCells);
        mGridView.setAdapter(mAdapter);
    }
/*
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
    */
    private class CustomAdapter extends ArrayAdapter<AppCell> {

        private CustomAdapter(Context context, int res, List<AppCell> data) {
            super(context, res, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) { // I guess the parent is mGridView?
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_page_grid_element, parent, false);

                TextView tv = (TextView)convertView.findViewById(R.id.appName);
                ImageView iv = (ImageView)convertView.findViewById(R.id.appIcon);
                holder = new ViewHolder(tv, iv);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }

            // Initialize views
            AppCell item = mCells.get(position);
            holder.tv.setText(item.name);
            holder.iv.setImageResource(item.image);

            return convertView;
        }
    }
    private static class ViewHolder {
        public final TextView tv;
        public final ImageView iv;

        public ViewHolder(TextView tv, ImageView iv) {
            this.tv = tv;
            this.iv = iv;
        }
    }
}
