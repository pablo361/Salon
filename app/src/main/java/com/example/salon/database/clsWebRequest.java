package com.example.salon.database;

import android.content.Context;
import android.util.Log;

import com.example.salon.database.clsGlobal;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

public class clsWebRequest {
    public static AsyncHttpClient client;

    static{
        //create object of loopj client
        //443 will save you from ssl exception
        client = new AsyncHttpClient(true,80,443);
    }

    public static void get(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.get(context, getAbsoluteUrl(url), params, responseHandler);
    }

    //concatenation of base url and file name
    private static String getAbsoluteUrl(String relativeUrl) {
        Log.d("response URL: ", clsGlobal.getInstance().BASE_URL + relativeUrl+" ");

        return clsGlobal.getInstance().BASE_URL + relativeUrl;
    }

    public static void post(Context context, String url, RequestParams params, ResponseHandlerInterface responseHandler) {
        client.post(context, getAbsoluteUrl(url), params, responseHandler);
    }
}
