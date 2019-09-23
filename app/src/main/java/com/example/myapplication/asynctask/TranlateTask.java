package com.example.myapplication.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.myapplication.util.HttpUtils;

public class TranlateTask extends AsyncTask<String,Integer,String> {

    private Context context;

    public TranlateTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        return HttpUtils.youdao(strings[0]);
    }
}
