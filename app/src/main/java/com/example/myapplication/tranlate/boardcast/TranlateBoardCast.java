package com.example.myapplication.tranlate.boardcast;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.asynctask.TranlateTask;
import com.example.myapplication.tranlate.TranlateService;

import java.util.concurrent.ExecutionException;

import static android.content.Context.CLIPBOARD_SERVICE;

public class TranlateBoardCast extends BroadcastReceiver {
    private final String action = "tranlate";
    private final String destroy = "destroy";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "1", Toast.LENGTH_LONG).show();
        if (intent.getAction().equals(action)) {
//            ClipboardManager cm = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
//            ClipData data = cm.getPrimaryClip();
//            if (cm.hasPrimaryClip()) {
//                ClipData.Item item = data.getItemAt(0);
//                String word = item.getText().toString();
//                if (word != null && word!= "" && word.length() > 0){
//                    try {
//                        word = new TranlateTask(context).execute(word).get();
//                    } catch (ExecutionException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(context, word, Toast.LENGTH_LONG).show();
//                }
//            }
        } else if(intent.getAction().equals(destroy)) {
           Intent sevice = new Intent(context, TranlateService.class);
           
        }
    }
}
