package cns.workspace.lib.androidsdk.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by liyuhao on 2017/8/17.
 */

public class DownloadService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("test","onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("test","onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("test","onStartCommand");

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("test","onDestroy");
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("test","onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("test","onUnbind");
        return super.onUnbind(intent);
    }
}
