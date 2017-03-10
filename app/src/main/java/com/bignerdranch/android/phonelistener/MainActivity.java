package com.bignerdranch.android.phonelistener;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


//http://blog.csdn.net/chy6575/article/details/55251485

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



//    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
//        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.RECORD_AUDIO },
//                10);
//    } else {
//        recordAudio();
//    }

    private void qidong() {

        Intent intent = new Intent(this, PhoneService.class);
        //启动服务
        startService(intent);
    }


    public void StartService(View view) {

        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},5);
        }else {

            qidong();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                qidong();
            } else {
                //User denied Permission.
                Toast.makeText(MainActivity.this, "没有权限！", Toast.LENGTH_LONG).show();
            }
        }
    }
}
