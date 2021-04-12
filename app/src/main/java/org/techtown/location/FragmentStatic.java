package org.techtown.location;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FragmentStatic extends Fragment {
    private static String IP_ADDRESS = "IP";
    private static String TAG = "phptest";
    private String mJsonString;
    TextView randomView;
    Button searchButton;
    BarChart chart;

    HashMap<String, Integer> Hmap = new HashMap<>();
    ArrayList<BarEntry> Entries = new ArrayList<>();
    ArrayList storename = new ArrayList();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        chart = view.findViewById(R.id.quality_chart);
        randomView = view.findViewById(R.id.randomView);
        GetData task = new GetData();
        task.execute( "http://" + IP_ADDRESS + "/getvisitjson.php", "");

        searchButton = (Button)view.findViewById(R.id.searchbutton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GetData task = new GetData();
                task.execute( "http://" + IP_ADDRESS + "/getrecommendjson.php", "");
            }
        });
        return view;
    }


    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            Log.d(TAG, "response - " + result);

            String TAG_JSON="jae";
            String TAG_NAME = "name";

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);
                    String name = item.getString(TAG_NAME);
                    System.out.println(name);
                    if(Hmap.keySet().contains(name))
                    {
                        Hmap.put(name, Hmap.get(name)+1);
                    }
                    else {
                        Hmap.put(name, 1);
                    }
                }

                int cnt=0;
                for ( String key : Hmap.keySet() ) {
                    Integer getNum = Hmap.get(key);
                    Entries.add(new BarEntry(getNum, cnt));
                    cnt++;
                }

                for ( String key : Hmap.keySet() ) {
                    System.out.println(key + "key");
                    storename.add(key);
                }

                XAxis xAxis = chart.getXAxis();
                BarDataSet bardataset = new BarDataSet(Entries, "Restaurant");
                bardataset.setValueTextColor(0xFFA9A9A9);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(2);
                BarData data = new BarData(storename, bardataset);
                chart.getAxisLeft().setEnabled(false);
                chart.getLegend().setEnabled(false);
                chart.getAxisRight().setEnabled(false);
                chart.setPinchZoom(false);
                chart.setTouchEnabled(false);
                chart.invalidate();
                xAxis.setTextColor(0xFFA9A9A9);
                bardataset.setColors(Collections.singletonList(0xFFA9A9A9));
                chart.setData(data);

            } catch (JSONException e) {
                Log.d(TAG, "showResult : ", e);
            }

            if (result == null){
                randomView.setText(errorString);
            }
            else {

                mJsonString = result;
            }
        }


        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String postParameters = params[1];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code : " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


}


