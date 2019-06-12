package com.timemory.daydream;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by weiliana.
 * @author AUSTER
 */
public class PictureLoader {

    private ImageView loadImage;
    /**图片链接*/
    private String imageUrl;
    /**图片字符流*/
    private byte[] picByte;

    /**change the ImageView bitmap.*/
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                if (picByte != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0, picByte.length);
                    loadImage.setImageBitmap(bitmap);
                }
                else {
                    System.out.println("picImge is null");
                }
            }
        }
    };

    public void load(ImageView loadImg, String imgUrl){
        this.loadImage = loadImg;
        this.imageUrl = imgUrl;
        Drawable drawable = loadImg.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable){
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            if (bitmap != null && !bitmap.isRecycled()){
                bitmap.recycle();
            }
        }
        else {
            System.out.println("drawable is null");
        }
        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(imageUrl);
                /**建立网络连接*/
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                System.out.println("get the image");
                if (connection.getResponseCode() == 200){
                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] bytes = new byte[1024];
                    int length = -1;
                    while ((length = inputStream.read(bytes)) != -1){
                        out.write(bytes, 0, length);
                    }
                    picByte = out.toByteArray();
                    inputStream.close();
                    out.close();
                    handler.sendEmptyMessage(0x123);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    };
}
