package com.example.nasmanage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

public class GloabValue {
    public static String ip="192.168.137.1";
    public static String port="80";
    public static String base_api_url="";
    public static String base_api_path="";
    public static String base_image_url="";
    public static String base_vedio_url="";
    public static String replace="";
    public static String buju="";
    public static String imgSize="";
    public void init(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        ip=prefs.getString("ip", "null");
        port=prefs.getString("port", "null");
        base_vedio_url="http://"+ip+":"+port+prefs.getString("baseVideoPath", "/vedio/");
        base_image_url="http://"+ip+":"+port+prefs.getString("baseImagePath", "/image/");
        base_api_url="http://"+ip+":"+port+prefs.getString("apiprefix", "/");
        base_api_path=prefs.getString("basepath","");
        buju=prefs.getString("buju", "预览图");
        replace=prefs.getString("replace","E:\\vedio");
        imgSize=prefs.getString("imgSize","1616");
    }
}
