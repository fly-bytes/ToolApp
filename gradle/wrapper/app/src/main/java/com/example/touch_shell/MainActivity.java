package com.example.touch_shell;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.touch_shell.touch.FileUtil;
import com.example.touch_shell.touch.ShellUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Button startBtn = findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);
        Button stopBtn = findViewById(R.id.stop_btn);
        stopBtn.setOnClickListener(this);
        Button palyBackBtn = findViewById(R.id.paly_back_btn);
        palyBackBtn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private static String sh = "#!/bin/bash\n" +
            "\n" +
            "\n" +
            "#touch_event=\"/dev/input/event1\"\n" +
            "#touch_event=\"/dev/input/event2\"\n" +
            "touch_event=\"\"\n" +
            "\n" +
            "\n" +
            "while true\n" +
            "do\n" +
            "a=`adb shell getevent -c 11 | grep  \"0035|0036\" | awk '{print $4}'`\n" +
            "y=`echo $a | sed -n '1p'| awk '{print $2}'`\n" +
            "x=`echo $a | sed -n '1p'| awk '{print $1}'`\n" +
            "a=`echo $x | sed 's/\\r//g'`\n" +
            "b=`echo $y | sed 's/\\r//g'`\n" +
            "c=$((16#${a}))\n" +
            "d=$((16#${b}))\n" +
            "if [ $c -ne 0 -a $d -ne 0 ];then\n" +
            "echo \"The touchscreen positon is x=$c,y=$d, command generate: adb shell input tap $c $d\"\n" +
            "echo \" \"\n" +
            "fi\n" +
            "done\n";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                Toast.makeText(getBaseContext(), "你点击了录制开始按钮！", Toast.LENGTH_LONG).show();
//                ShellUtil.executeCommand("chmod +x /data/user/0/com.example.touch_shell/record.sh");
//                this.newFile(sh);
                ShellUtil.executeCommand("getevent >> /data/user/0/com.example.touch_shell/d.log 2>&1");
//                ShellUtil.executeCommand("sh /data/user/0/com.example.touch_shell/record.sh");
                break;
            case R.id.stop_btn:
                Toast.makeText(getBaseContext(), "你点击了录制结束按钮！", Toast.LENGTH_LONG).show();
                try {
                    ShellUtil.shell.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.paly_back_btn:
                Toast.makeText(getBaseContext(), "你点击了录制回放按钮！", Toast.LENGTH_LONG).show();
                for (String line : FileUtil.six2five("/data/user/0/com.example.touch_shell/d.log")) {
                    System.out.println(line);
                }
                break;
            default:
                break;
        }

    }

    public void newFile(String sh) {
        byte[] sourceByte = sh.getBytes();
        if (null != sourceByte) {
            try {
                File file = new File(getBaseContext().getFilesDir().getAbsolutePath() + File.separator + "record.sh");
                System.out.println(file.getPath());
                FileOutputStream outStream = new FileOutputStream(file);
                outStream.write(sourceByte);
                outStream.flush();
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
