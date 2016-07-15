package com.example.wxhelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * TODO 这里描述下用途吧
 * Created by lingxiaoming on 2016/7/15 0015.
 */
public class ImageUtils {

    public static Bitmap getBitmap(String imgPath, int pixelW, int pixelH) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);//此时返回bm为空
        newOpts.inJustDecodeBounds = false;
        int realWidth = newOpts.outWidth;
        int realHeight = newOpts.outHeight;
        float realScale = (float) realWidth / realHeight;

        int wantWidth = pixelW;
        int wantHeight = pixelH;
        float wantScale = (float) wantWidth / wantHeight;

        int be = 1;

        if(wantHeight <= 0){
            if(realWidth > wantWidth){
                be = realWidth / wantWidth;
            }else{
                be = 1;
            }
        }else if(wantWidth <= 0){
            if(realHeight > wantHeight){
                be = realHeight / wantHeight;
            }else {
                be = 1;
            }
        }else if(realScale >= wantScale){
            be = realWidth / wantWidth;
        }else {
            be = realHeight / wantHeight;
        }


        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        // 压缩好比例大小后再进行质量压缩
//        return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除
        return bitmap;
    }
}
