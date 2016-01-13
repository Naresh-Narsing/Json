package com.example.bridge.json;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {

    private static String url = "http://jsonplaceholder.typicode.com/posts";

    private static final String USERID = "userId";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String BODY = "body";

    ArrayList<HashMap<String ,String >> jsonlist = new ArrayList<HashMap<String, String>>();

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new ProgressTask(MainActivity.this).execute();
    }

        private class ProgressTask extends AsyncTask<String,Void,Boolean>{
            private ProgressDialog dialog;

            private ListActivity activity;

            private Context context;

            public ProgressTask(ListActivity activity){
                this.activity = activity;
                context = activity;
                dialog = new ProgressDialog(context);
            }


            @Override
            protected void onPreExecute() {
                this.dialog.setMessage("PROGRESS START");
                this.dialog.show();
            }


            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (dialog.isShowing()){
                    dialog.dismiss();
                }

                ListAdapter adapter = new SimpleAdapter(context,jsonlist,R.layout.list_item, new String[]
                                        {USERID,ID,TITLE,BODY},new int[] {R.id.userid,R.id.id1,
                                        R.id.title,R.id.body});

                setListAdapter(adapter);
                lv = getListView();

            }


            @Override
            protected Boolean doInBackground(String... params) {

                JsonParser jParser = new JsonParser();

                JSONArray json = jParser.getJSONFromUrl(url);

                for (int i = 0; i < json.length(); i++){
                    try{
                        JSONObject c = json.getJSONObject(i);
                        String userid1 = c.getString("userId");
                        String id = c.getString("id");
                        String title = c.getString("title");
                        String body = c.getString("body");

                        HashMap<String,String > map = new HashMap<String,String>();
                        map.put(USERID,userid1);
                        map.put(ID,id);
                        map.put(TITLE,title);
                        map.put(BODY,body);
                        jsonlist.add(map);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                return false;
            }
        }

}
