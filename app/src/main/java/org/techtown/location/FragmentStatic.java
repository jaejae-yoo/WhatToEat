package org.techtown.location;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

public class FragmentStatic extends Fragment {
    MainActivity activity;
    String receiveData;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        activity = (MainActivity) getActivity();
        BarChart chart = view.findViewById(R.id.quality_chart);
        TextView randomView = view.findViewById(R.id.randomView);

        receiveData = "";
        ArrayList _store = new ArrayList();
        if(activity.mBundle != null) {
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            System.out.println(receiveData+"rdrdrd");
            String[] text = receiveData.split(",");
            activity.mBundle = null;
            for(String st: text){
                _store.add(st);
            }
        }

        HashMap<String, Integer> Hmap = new HashMap<>();
        for(int j=0; j <_store.size(); j++) {
            if(Hmap.keySet().contains(_store.get(j)))
            {
                Hmap.put((String) _store.get(j), Hmap.get(_store.get(j))+1);
            }
            else {
                Hmap.put((String) _store.get(j), 1);
            }
        }

        ArrayList<BarEntry> Entries = new ArrayList<>();
        int cnt=0;
        for ( String key : Hmap.keySet() ) {
            Integer getNum = Hmap.get(key);
            Entries.add(new BarEntry(getNum, cnt));
            cnt++;
        }

        ArrayList storename = new ArrayList();
        for ( String key : Hmap.keySet() ) {
            System.out.println(key+ "key");
            storename.add(key);
        }
        System.out.println(storename+ "storename");
        int n = Hmap.keySet().size();
        double _num = Math.random()*n;
        int num = (int) _num;
        randomView.append("오늘의 추천 음식점: " + storename.get(num).toString());


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
        //chart.setDoubleTapToZoomEnabled(false);
        chart.invalidate();
        xAxis.setTextColor(0xFFA9A9A9); // X축 텍스트컬러설정
        bardataset.setColors(Collections.singletonList(0xFFA9A9A9));
        chart.setData(data);
        return view;
    }




}
