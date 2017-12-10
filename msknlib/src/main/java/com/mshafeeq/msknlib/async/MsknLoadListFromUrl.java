package com.mshafeeq.msknlib.async;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mshafeeq.msknlib.log.MsknLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/*

//------------------------- Sample code ---------------------//

new MsknLoadListFromUrl<>(MsknLoadListFromUrl.HTTP_GET_METHOD,
        new TypeToken<ArrayList<TestClass>>() {
        }.getType(),
        new OnLoadUrlCompleteListener<ArrayList<TestClass>>() {
            @Override
            public void onLoadComplete(ArrayList<TestClass> items) {
                MsknLog.logit("MSKN-ASYNC", "Response: array: " + items.get(0).getDate());
            }
        }
).execute("https://mshafeeque.000webhostapp.com/arrayobject.php");
 */


@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class MsknLoadListFromUrl<T> extends AsyncTask<String, Integer, T> {

    public static final int HTTP_GET_METHOD     =   1000;
    public static final int HTTP_POST_METHOD    =   1001;

    private int mHttpMethod                     =   HTTP_GET_METHOD;
    private Type mType;
    private String mKey                         =   null;
    private OnLoadUrlCompleteListener<T> mListener = null;

    public MsknLoadListFromUrl(int mHttpMethod,  Type type, OnLoadUrlCompleteListener<T> listener) {
        this.mHttpMethod    = mHttpMethod;
        this.mListener      = listener;
        this.mType          = type;
    }

    public void setSpecificKey(String key) {
        this.mKey = key;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected T doInBackground(String... strings) {

        T               parsedList  = null;
        String          result      = null;

        MsknLog.logit("MSKN-ASYNC", "Request: " + strings[0]);
        if(mHttpMethod == HTTP_GET_METHOD)
            result = connectServerGet(strings[0]);

        MsknLog.logit("MSKN-ASYNC", "Response: " + result);

        if(mKey != null) {
            try {
                JSONObject json = new JSONObject(result);
                if(json.has(mKey)) {
                    MsknLog.logit("MSKN-ASYNC", "Key detected: " + mKey);
                    result = json.getString(mKey);
                    MsknLog.logit("MSKN-ASYNC", "New result: " + result);
                } else {
                    MsknLog.logit("MSKN-ASYNC", "Key: " + mKey + "Not found in response");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(result != null)
            parsedList = new Gson().fromJson(result, mType);

        return parsedList;
    }

    @Override
    protected void onPostExecute(T ts) {
        super.onPostExecute(ts);
        mListener.onLoadComplete(ts);
    }

    private String connectServerGet(String address) {
        HttpURLConnection connection;
        OutputStreamWriter request;

        URL url;
        String response = null;

        try {
            url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

            request = new OutputStreamWriter(connection.getOutputStream());
            request.flush();
            request.close();

            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();


            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            response = stringBuilder.toString();
            inputStreamReader.close();
            bufferedReader.close();

        } catch (UnknownHostException e) {
            return "CONNECT FAILED";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
