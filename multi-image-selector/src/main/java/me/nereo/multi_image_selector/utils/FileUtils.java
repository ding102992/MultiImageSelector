package me.nereo.multi_image_selector.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件操作类
 * Created by Nereo on 2015/4/8.
 */
public class FileUtils {

    public static File createTmpFile(Context context){
        String state = Environment.getExternalStorageState();
        File dir;
        // 已挂载
        dir = state.equals(Environment.MEDIA_MOUNTED)
                ? Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                : context.getCacheDir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String fileName = "multi_image_"+timeStamp+"";
        return new File(dir, fileName+".jpg");
    }

}
