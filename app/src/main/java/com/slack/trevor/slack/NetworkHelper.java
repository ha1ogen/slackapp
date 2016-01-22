package com.slack.trevor.slack;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by trevor on 1/21/16.
 */
public class NetworkHelper {

    private static final String LOG_TAG = "NetworkHelper";

    private static final String API_URL = "https://slack.com/api/";
    private static final String USERS_LIST = "users.list";
    private static final String USERS_LIST_API = API_URL + USERS_LIST + "?";

    private static final String TOKEN_PARAMETER = "token=";
    private static final String TOKEN = "xoxp-5048173296-5048487710-19045732087-b5427e3b46";

    private final Context mContext;
    private final DbHelper mDbHelper;

    public NetworkHelper(Context context, DbHelper dbHelper) {
        mContext = context;
        mDbHelper = dbHelper;
    }

    public void getUserList() {
        // Instantiate the RequestQueue
        RequestQueue queue = Volley.newRequestQueue(mContext);

        String url = String.format(USERS_LIST_API + TOKEN_PARAMETER + TOKEN);

        // Request a JSON object response
        JsonObjectRequest stringRequest = new JsonObjectRequest(
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, response.toString());

                        mDbHelper.writeToDb(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(LOG_TAG, "Error fetching users list:" + error.getMessage());

                    }
                });

        // Add the request to the RequestQueue
        queue.add(stringRequest);
    }
}
