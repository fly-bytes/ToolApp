package com.example.myapplication.wifi;

public class WifiInfo {
    public String Ssid="";
    public String Password="";

    @Override
    public String toString() {
        return "WifiInfo{" +
                "Ssid='" + Ssid + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}