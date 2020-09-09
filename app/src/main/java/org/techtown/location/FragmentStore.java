package org.techtown.location;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.ContentValues.TAG;

public class FragmentStore extends Fragment {

    MainActivity activity;
    String sendData, receiveData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        Log.d(TAG, "onCreateView: FragmentMap created");

        activity = (MainActivity) getActivity();


        TextView textView2 = view.findViewById(R.id.textView2);
        Button button3 = (Button)view.findViewById(R.id.button3);

        sendData = "프래그먼트에서 보낸 데이터입니다.";
        receiveData = "";

        if(activity.mBundle != null) {
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");

            textView2.append(receiveData);
            activity.mBundle = null;

        }

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sendData", sendData);


                activity.fragBtnClick(bundle);
            }
        });


        return view;
    }

}
