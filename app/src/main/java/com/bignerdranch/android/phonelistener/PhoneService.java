package com.bignerdranch.android.phonelistener;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;

public class PhoneService extends Service {

    private MediaRecorder mRecorder;
    private static final String TAG = PhoneService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onCreate() {
        super.onCreate();

        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);




        tm.listen(new MyPhoneStateListener(),PhoneStateListener.LISTEN_CALL_STATE);
    }


    private class MyPhoneStateListener extends PhoneStateListener {





        @Override
        public void onCallStateChanged(int state, String incomingNumber) {



            //判断电话处于什么状态
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.i(TAG,"空闲中");
                    if (mRecorder != null){
                        mRecorder.stop();
                        mRecorder.reset();
                        mRecorder.release();
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    Log.i(TAG,"通话中");
                    //开始录音
                    mRecorder.start();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    mRecorder = new MediaRecorder();
                    //sLog.i(TAG,"响铃中");
                    //设置音频的来源
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //输出格式
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //编码方式
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    //文件保存路径
                    mRecorder.setOutputFile("/mnt/sdcard/luyin.3gp");
                    //准备录音
                    try{
                        mRecorder.prepare();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
