package osu_app_club.osuclubapp.utilities;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import osu_app_club.osuclubapp.interfaces.AppDataDelegate;
import osu_app_club.osuclubapp.models.NewsObject;
import osu_app_club.osuclubapp.models.ProjectObject;

/**
 * Created by Bfriedman on 3/12/15.
 */
public class AppData implements Runnable {

    private static String TAG = "OSU-APP-APPDATA";
    private static AppData ourInstance = new AppData();

    private static final String NEWS_URL = "https://raw.githubusercontent.com/OSU-App-Club/OSU-Club-App-Resources/master/news/news.json";
    private static final String PROJECTS_URL = "https://raw.githubusercontent.com/OSU-App-Club/OSU-Club-App-Resources/master/projects/projects.json";
    private static List<NewsObject> newsObjectsList;
    private static List<ProjectObject> projectObjectsList;
    private static AppDataDelegate callback;
    private static Context context;
    private static boolean isReady = false;

    public static AppData getInstance() {
        return ourInstance;
    }

    private AppData() {

    }

    public List<NewsObject> getNewsData() {
        return newsObjectsList;
    }

    public List<ProjectObject> getProjectsData() {
        return projectObjectsList;
    }

    public static void setCallbackListener(AppDataDelegate _callback, Context ctx) {
        if(isReady) {
            //our data is already here, let's just shoot back immediately
            _callback.onAppDataFetched();
        } else {
            callback = _callback;
            context = ctx;
        }
    }

    private String getStream(String _url) {
        String response = null;
        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(_url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    @Override
    public void run() {
        isReady = false; //set not ready
        String news = getStream(NEWS_URL);
        String projects = getStream(PROJECTS_URL);
        newsObjectsList = extractNewsObjects(news);
        projectObjectsList = extractProjectObjects(projects);

        isReady = true; //set ready
        if(callback != null) {
            //we have a registered callback, let's post this onto the main thread!
            Handler mainHandler = new Handler(context.getMainLooper());
            Runnable myRunnable = new Runnable() {
                @Override
                public void run() {
                    callback.onAppDataFetched();
                }
            };
            mainHandler.post(myRunnable);
        }
    }

    private List<NewsObject> extractNewsObjects(String input) {
        try {
            List<NewsObject> newsObjects = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(input);

            JSONArray jsonArray = (JSONArray)jsonObject.get("news");

            int len = jsonArray.length();
            for(int x = 0; x < len; x++) {
                //create a new NewsObject
                NewsObject newsObject = new NewsObject();
                //get our json object to extract the elements through it
                JSONObject job = jsonArray.getJSONObject(x);

                //get id
                newsObject.setId(job.getInt("id"));
                //get title
                newsObject.setTitle(job.getString("title"));
                //get description
                newsObject.setDescription(job.getString("description"));
                //get date
                newsObject.setDate(job.getString("date"));
                //get thumbnail
                newsObject.setThumbnailURL(job.getString("thumbnail"));

                //add this object
                newsObjects.add(newsObject);
            }

            return newsObjects;

        } catch (JSONException je) {
            je.printStackTrace();
        }
        //some error was thrown most likely, return null!
        return null;
    }

    private List<ProjectObject> extractProjectObjects(String input) {
        try {
            List<ProjectObject> projectObjects = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(input);

            JSONArray jsonArray = (JSONArray)jsonObject.get("projects");

            int len = jsonArray.length();
            for(int x = 0; x < len; x++) {
                //create a project object
                ProjectObject projectObject = new ProjectObject();
                //get our json object to extract the elements through it
                JSONObject job = jsonArray.getJSONObject(x);

                //get id
                projectObject.setId(job.getInt("id"));
                //get name
                projectObject.setName(job.getString("name"));
                //get description
                projectObject.setDescription(job.getString("description"));
                //get thumbnail
                projectObject.setThumbnailURL(job.getString("thumbnail"));
                //extract all memberIds
                JSONArray memberIds = job.getJSONArray("member_ids");
                int memLen = memberIds.length();

                //set up List<Integer> containing our member ids
                List<Integer> memberIdsList = new ArrayList<>();

                for(int y = 0; y < memLen; y++) {
                    JSONObject memJob = memberIds.getJSONObject(y);

                    //get member id
                    memberIdsList.add(memJob.getInt("id"));
                }

                //set memberIds
                projectObject.setMemberIds(memberIdsList);
                //get url
                projectObject.setUrl(job.getString("url"));
            }

            return projectObjects;

        } catch (JSONException je) {
            je.printStackTrace();
        }
        //some error was thrown most likely, return null!
        return null;
    }
}