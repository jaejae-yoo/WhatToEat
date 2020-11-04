package org.techtown.location;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;

public class FragmentStatic extends Fragment {
    MainActivity activity;
    String sendData, receiveData;
    private BarChart[] Charts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_static, container, false);

        activity = (MainActivity) getActivity();
        TextView store_textView = view.findViewById(R.id.store_textView);

        Charts = new BarChart[]{
                view.findViewById(R.id.store_textView)};


        sendData = "프래그먼트에서 보낸 데이터입니다.";
        receiveData = "";
        if(activity.mBundle != null) {
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            store_textView.append(receiveData);
            activity.mBundle = null;
        }



        return view;
    }

}
