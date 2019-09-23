package com.example.myapplication.tranlate;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.asynctask.TranlateTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TranlateService extends IntentService {

    private final String action = "tranlate";
    private static List<String> record = new ArrayList<>();

    public TranlateService() {
        super("TranlateService");
    }

    public TranlateService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    //Service被创建时调用
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //Service被启动时调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    //Service被关闭之前回调
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent tranlateIntent = new Intent(getBaseContext(), TranlateService.class);
        startService(tranlateIntent);
        tarnlate(getBaseContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void tarnlate(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        ClipData data = cm.getPrimaryClip();
        if (cm.hasPrimaryClip()) {
            ClipData.Item item = data.getItemAt(0);
            String word = item.getText().toString();
            if (word != null && word != "" && word.length() > 0 && !record.contains(word)) {
                try {
                    record.add(word);
                    word = new TranlateTask(context).execute(word).get();
                } catch (ExecutionException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (InterruptedException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String channelId = "chat";//设置通道的唯一ID
                    String channelName = "聊天消息";//设置通道名
                    int importance = NotificationManager.IMPORTANCE_HIGH;//设置通道优先级
                    createNotificationChannel(channelId, channelName, importance, "翻译结果", word);
                } else {
                    sendSubscribeMsg("翻译结果", word);
                }
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance, String title, String text) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        sendSubscribeMsg(title, text);
    }

    public void sendSubscribeMsg(String title,String text) {
        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getBaseContext(), "chat")
                .setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getBaseContext().getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .build();
        manager.notify(2, notification);
    }
}
