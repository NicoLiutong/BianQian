package com.example.bianqian.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by 刘通 on 2017/5/25.
 */

public class PictureOperation {
    /*private boolean hasExternalStaragePrivateFile(){
        File file = new File(getExternalFilesDir(null),"user_picture.jpg");
        if(file != null){
            return file.exists();
        }
        return false;
    }*/

    public static void creatExternalStaragePrivateFile(Context context){
        File file = new File(context.getExternalFilesDir(null),"user_picture.jpg");
        if(file != null){
            file.delete();
        }
        try{
            InputStream is = context.getResources().getAssets().open("user_picture.jpg");
            OutputStream os = new FileOutputStream(file);
            byte[] date = new byte[is.available()];
            is.read(date);
            os.write(date);
            is.close();
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
