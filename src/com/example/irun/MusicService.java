package com.example.irun;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

//“Ù¿÷∑˛ŒÒ
public class MusicService extends Service {

	private MediaPlayer mp;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	if(intent != null)
    	{
    		Bundle bundle = intent.getExtras();
            int id = bundle.getInt("id");
            mp = mp.create(MusicService.this,id);
            mp.start();
    	}
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mp.release();
        super.onDestroy();
    }
}
