package com.example.myapplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader {
    public static boolean downloadImage(String imgUrl, File destFile){
        try{
            URL url = new URL(imgUrl);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(destFile);

            byte[] buf = new byte[1024];
            int byteRead = -1;
            while((byteRead = in.read(buf)) != -1){
                out.write(buf, 0, byteRead);
            }

            out.close();
            in.close();
            return true;

        } catch (Exception e){
            return false;
        }
    }
}
