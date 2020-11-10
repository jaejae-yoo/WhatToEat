package org.techtown.location;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FragmentStatic extends Fragment {
    String tableName= "storeList";
    SQLiteDatabase database;
    String store = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static, container, false);
        BarChart chart = view.findViewById(R.id.quality_chart);
        TextView randomView = view.findViewById(R.id.randomView);

        String _stn ="";
        createDatabase();
        _stn = executeQuery();

        ArrayList _store = new ArrayList();
        String[] text = _stn.split(",");
        for(String st: text){
            _store.add(st);
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

    private void createDatabase() {
        database = getActivity().openOrCreateDatabase("restaurant_store.db",android.content.Context.MODE_PRIVATE ,null);
        createTable("storeList");
    }

    private void createTable(String name) {
        if (database == null) {
            return;
        }
        database.execSQL("create table if not exists " + name + "("
                + " store_name text)");
    }

    public String executeQuery() {

        String sql = "select * from " + tableName;
        Cursor cursor = database.rawQuery(sql, null);

        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            String store_name = cursor.getString(0);
            System.out.println("레코드" + i + " : " + store_name);
            if (i < recordCount - 1) {
                store += store_name + ",";
            } else if (i == recordCount - 1) {
                store += store_name;
            }
        }

        return store;
        }


}
