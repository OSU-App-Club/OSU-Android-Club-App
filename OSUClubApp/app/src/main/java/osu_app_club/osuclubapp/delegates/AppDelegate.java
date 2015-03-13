package osu_app_club.osuclubapp.delegates;

import android.app.Application;
import android.util.Log;

import osu_app_club.osuclubapp.utilities.AppData;

/**
 * Created by Bfriedman on 3/12/15.
 */
public class AppDelegate extends Application{
    private static String TAG = "APP-DELEGATE";

    public AppDelegate() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "App Delegate started up");

        new Thread(AppData.getInstance()).start();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "App Delegate shutting down...");
        //clear callback
        AppData.setCallbackListener(null,null);
    }
}
