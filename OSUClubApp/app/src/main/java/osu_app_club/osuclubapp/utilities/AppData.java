package osu_app_club.osuclubapp.utilities;

import android.content.Context;
import android.os.Handler;

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

import osu_app_club.osuclubapp.interfaces.AppDataCallback;
import osu_app_club.osuclubapp.models.MemberObject;
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
    private static final String MEMBERS_URL = "https://api.github.com/orgs/OSU-App-Club/public_members";

    private static List<NewsObject> newsObjectsList;
    private static List<ProjectObject> projectObjectsList;
    private static List<MemberObject> memberObjectsList;

    private static AppDataCallback callback;
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

    public List<MemberObject> getMemberData() {
        return memberObjectsList;
    }

    public static void setCallbackListener(AppDataCallback _callback, Context ctx) {
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

        //get news json
        String newsData = getStream(NEWS_URL);
        //get projects json
        String projectsData = getStream(PROJECTS_URL);
        //get members json
        String membersData = getStream(MEMBERS_URL);

        //extract news objects
        newsObjectsList = extractNewsObjects(newsData);
        //extract project objects
        projectObjectsList = extractProjectObjects(projectsData);
        //extract member objects
        memberObjectsList = extractMemberObjects(membersData);

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

    private List<MemberObject> extractMemberObjects(String input) {
        try {
            List<MemberObject> memberObjects = new ArrayList<>();
            JSONArray members = new JSONArray(input);
            int len = members.length();

            for(int x = 0; x < len; x++) {
                //create a new MemberObject
                MemberObject mo = new MemberObject();
                //get our json object to extract the elements through it
                JSONObject job = members.getJSONObject(x);

                //get id
                mo.setId(job.getInt("id"));
                //get login (username)
                mo.setLogin(job.getString("login"));
                //get avatar_url (image)
                mo.setAvatarURL(job.getString("avatar_url"));
                //get url for API calls on this user
                mo.setUrl(job.getString("url"));
                //get html_url for normal page
                mo.setHtml_url(job.getString("html_url"));
                //get gists_url
                mo.setGists_url(job.getString("gists_url"));
                //get starred_url
                mo.setStarred_url(job.getString("starred_url"));
                //get repos_url
                mo.setRepos_url(job.getString("repos_url"));

                //add this object
                memberObjects.add(mo);
            }

            return memberObjects;

        } catch (JSONException je) {
            je.printStackTrace();
        }
        //some error was thrown most likely, return null!
        return null;
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