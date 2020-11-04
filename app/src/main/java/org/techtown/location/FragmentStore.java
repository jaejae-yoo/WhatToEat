package org.techtown.location;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import static android.content.ContentValues.TAG;
import static java.sql.DriverManager.println;

public class FragmentStore extends Fragment{
    MainActivity activity;
    String sendData, receiveData;
    SQLiteDatabase database;
    EditText editText;
    String tableName= "storeList";
    String store = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        Log.d(TAG, "onCreateView: FragmentMap created");

        activity = (MainActivity) getActivity();

        TextView textView2 = view.findViewById(R.id.textView2);
        editText = view.findViewById(R.id.editText);

        Button storebutton = (Button)view.findViewById(R.id.storebutton);
        storebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDatabase();
                String storeName = editText.getText().toString();
                insertRecord(storeName);
            }
        });

        sendData = "프래그먼트에서 보낸 데이터입니다.";
        receiveData = "";

        if(activity.mBundle != null) {
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            textView2.append(receiveData);
            activity.mBundle = null;
        }
        return view;
    }

    private void createDatabase() {
        database = getActivity().openOrCreateDatabase("restaurant_store",android.content.Context.MODE_PRIVATE ,null);
        createTable("storeList");
    }

    private void createTable(String name) {
        if (database == null) {
            return;
        }
        database.execSQL("create table if not exists " + name + "("
                + " store_name text)");
    }

    private void insertRecord(String _name) {

        if (database == null) {
            return;
        }
        if (tableName == null) {
            return;
        }
        database.execSQL("INSERT INTO storeList VALUES ('" + _name + "');");
        executeQuery();
    }

    //private void println(String data){
    //    textView.append(data + "\n");
    //    }

    public void executeQuery() {
        String sql = "select * from " +tableName;
        Cursor cursor = database.rawQuery(sql, null);

        int recordCount = cursor.getCount();
        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();
            //int id = cursor.getInt(0);
            String store_name = cursor.getString(0);
            System.out.println("레코드 #" + i + " : " + ", " + store_name);
            store += store_name + "\n";
        }

        Bundle bundle = new Bundle();
        bundle.putString("sendData", store);
        cursor.close();
    }
}
