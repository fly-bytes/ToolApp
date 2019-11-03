package com.example.touch_shell.touch;

import android.util.Log;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.Command;
import com.stericson.RootTools.execution.CommandCapture;
import com.stericson.RootTools.execution.Shell;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeoutException;

import static com.stericson.RootTools.Constants.TAG;

/**
 * setenforce Permissive
 */
public class ShellUtil {
    public static Shell shell = null;
    public static void exec(String cmd) {
        try {
            Process p = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = p.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            // 将命令写入
            dataOutputStream.writeBytes(cmd);
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void executeCommand(String command) {
        try {
            shell = RootTools.getShell(true, Integer.MAX_VALUE);
            shell.add(new Command(0, command){

                @Override
                public void commandCompleted(int id, int exitCode) {
                    //命令执行完成后会调用此方法
                    Log.d(TAG,"命令执行完毕" + exitCode);
                }

                @Override
                public void commandOutput(int id, String line) {
                    //命令执行的过程中会执行此方法，line参数可用于调试
                    Log.d(TAG,"命令执行中..." + line);
                }

                @Override
                public void commandTerminated(int id, String reason) {
                    //命令被取消后的执行此方法
                    Log.d(TAG,"命令被取消" + reason);
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (RootDeniedException e) {
            e.printStackTrace();
        }
    }
}
