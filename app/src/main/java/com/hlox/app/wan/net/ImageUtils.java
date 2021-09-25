package com.hlox.app.wan.net;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

public class ImageUtils {
    public static void loadImage(ImageView imageView, String imagePath, Handler handler) {
        HttpClient.downloadImage(imagePath, null, null, new HttpCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                if(imageView != null){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap((Bitmap) data);
                        }
                    });
                }
            }

            @Override
            public void onError(int code, String message, Exception exception) {
            }
        });
    }
}
