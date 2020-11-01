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
        //println("createDatabase 호출됨.");

        database = getActivity().openOrCreateDatabase("restaurant_store",android.content.Context.MODE_PRIVATE ,null);

        //println("데이터베이스 생성함 : " + name);
        createTable("storeList");
    }

    private void createTable(String name) {
        //println("createTable 호출됨.");

        if (database == null) {
            //println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        database.execSQL("create table if not exists " + name + "("
                + " store_name text)");

        //println("테이블 생성함 : " + name);
    }

    private void insertRecord(String _name) {
        //println("insertRecord 호출됨.");

        if (database == null) {
           //println("데이터베이스를 먼저 생성하세요.");
            return;
        }

        if (tableName == null) {
            //println("테이블을 먼저 생성하세요.");
            return;
        }
        database.execSQL("INSERT INTO storeList VALUES ('" + _name + "');");



        //println("레코드 추가함.");
        executeQuery();
    }

    //private void println(String data){
    //    textView.append(data + "\n");
    //    }

    public void executeQuery() {
        //println("executeQuery 호출됨.");

        String sql = "select * from " +tableName;
        Cursor cursor = database.rawQuery(sql, null);

        int recordCount = cursor.getCount();
        //println("레코드 개수 : " + recordCount);

        for (int i = 0; i < recordCount; i++) {
            cursor.moveToNext();

            //int id = cursor.getInt(0);
            String store_name = cursor.getString(0);
            System.out.println("레코드 #" + i + " : " + ", " + store_name);
        }

        cursor.close();
    }

    }
