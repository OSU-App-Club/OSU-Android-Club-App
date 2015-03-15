package osu_app_club.osuclubapp.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import osu_app_club.osuclubapp.R;
import osu_app_club.osuclubapp.models.TabListener;

/**
 * Created by Bfriedman on 3/14/15.
 */
public class NavBarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar_layout);

        //construct action bar components
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(true);

        //set news tab
        ActionBar.Tab tab = actionBar.newTab()
                .setText("News")
                .setTabListener(new TabListener<NewsActivity>(
                        this, "News", NewsActivity.class));
        actionBar.addTab(tab);

        //set projects tab
        tab = actionBar.newTab()
                .setText("Projects")
                .setTabListener(new TabListener<MOCK_ProjectsActivity>(
                        this, "Projects", MOCK_ProjectsActivity.class));
        actionBar.addTab(tab);

        //set members tab
        tab = actionBar.newTab()
                .setText("Members")
                .setTabListener(new TabListener<MOCK_MembersActivity>(
                        this, "Members", MOCK_MembersActivity.class));
        actionBar.addTab(tab);


        //show backwards compat action bar
        getSupportActionBar().show();
    }
}
