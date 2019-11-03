package com.example.touch_shell.touch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static String filePath = "C:\\Users\\A\\AndroidStudioProjects\\touchshell\\app\\src\\main\\java\\com\\example\\touch_shell\\1.txt";
    /**
     *
     * @return
     */
    public static List<String> six2five(String filePath) {
        List<String> result = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
            String read;
            while ((read = bufferedReader.readLine()) != null) {
                result.add("sendevent " + execFileLine(read));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String execFileLine(String line) {
        StringBuilder sb = new StringBuilder();
        String[] arr = line.split(" ");
        for (int i = 0; i < arr.length - 2; i++) {
            sb.append(arr[i]);
            sb.append(" ");
        }
        sb.append(new BigInteger(arr[arr.length - 2], 16));
        sb.append(" ");
        sb.append(new BigInteger(arr[arr.length - 1], 16));
        return sb.toString().replaceAll(":", "");
    }

    public static void main(String[] args) {
        for (String line : six2five(filePath)) {
            System.out.println(line);
        }
    }
}
