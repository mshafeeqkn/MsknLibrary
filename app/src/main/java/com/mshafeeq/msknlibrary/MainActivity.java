package com.mshafeeq.msknlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.reflect.TypeToken;
import com.mshafeeq.msknlib.async.MsknLoadListFromUrl;
import com.mshafeeq.msknlib.async.OnLoadUrlCompleteListener;
import com.mshafeeq.msknlib.log.MsknLog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MsknLog.enableLogging(true);
//        new MsknLoadListFromUrl<>(MsknLoadListFromUrl.HTTP_GET_METHOD,
//                new TypeToken<ArrayList<TestClass>>() {
//                }.getType(),
//                new OnLoadUrlCompleteListener<ArrayList<TestClass>>() {
//                    @Override
//                    public void onLoadComplete(ArrayList<TestClass> items) {
//                        MsknLog.logit("MSKN-ASYNC", "Response: array: " + items.get(0).getDate());
//                    }
//                }
//        ).execute("https://mshafeeque.000webhostapp.com/arrayobject.php");
//
//        new MsknLoadListFromUrl<>(MsknLoadListFromUrl.HTTP_GET_METHOD,
//                TestClass.class,
//                new OnLoadUrlCompleteListener<TestClass>() {
//                    @Override
//                    public void onLoadComplete(TestClass item) {
//                        MsknLog.logit("MSKN-ASYNC", "Response: Single: " + item.getDate());
//                    }
//                }
//        ).execute("https://mshafeeque.000webhostapp.com/singleobject.php");
//

        MsknLoadListFromUrl<ArrayList<TestClass>> loader = new MsknLoadListFromUrl<>(MsknLoadListFromUrl.HTTP_GET_METHOD,
                new TypeToken<ArrayList<TestClass>>() {}.getType(),
                new OnLoadUrlCompleteListener<ArrayList<TestClass>>() {
            @Override
            public void onLoadComplete(ArrayList<TestClass> item) {
                MsknLog.logit("MSKN-ASYNC", "Response: combined: " + item.get(0).getName());
            }
        });
        loader.setSpecificKey("mDatas");
        loader.execute("https://mshafeeque.000webhostapp.com/combinedobject.php");
    }
}
