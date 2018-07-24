package com.example.after.alittletwo.util;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


/**
 * Created by hongjian.chen on 2018/7/24.
 */

public class BitmapFileSetting {


    /**
     * 把batmap 转file
     *
     * @param bitmap
     * @param filepath
     */
    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据网络url 得到 bitmap
     *
     * @param urlPath
     * @return
     * @throws IOException
     */
    public static Bitmap getByUrl(String urlPath) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(urlPath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 根据 路径 得到 file 得到 bitmap
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static Bitmap decodeFile(String filePath) throws IOException {
        Bitmap b;
        int IMAGE_MAX_SIZE = 600;

        File f = new File(filePath);
        if (f == null) {
            return null;
        }
        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = new FileInputStream(f);
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();

        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        fis = new FileInputStream(f);
        b = BitmapFactory.decodeStream(fis, null, o2);
        fis.close();
        return b;
    }

}
