package com.lone.wjm.dating.Util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by: Lone on 2016/6/1.
 * Contact: 4951048@qq.com
 */
public class FileUtil {
    public static String getImagePathFromUri(Context context, Uri fileUrl) {
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (fileUrl != null) {
            if (fileUrl.getScheme().toString().compareTo("content") == 0) {
                // content://开头的uri
                Cursor cursor = context.getContentResolver().query(fileUrl,
                        null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // 取出文件路径

                    // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                    if (!fileName.startsWith("/storage")
                            && !fileName.startsWith("/mnt")) {
                        // 检查是否有”/mnt“前缀
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            } else if (fileUrl.getScheme().compareTo("file") == 0) { // file:///开头的uri

                fileName = filePathUri.toString();// 替换file://
                fileName = filePathUri.toString().replace("file://", "");
                int index = fileName.indexOf("/sdcard");
                fileName = index == -1 ? fileName : fileName.substring(index);
                // if (!fileName.startsWith("/mnt")) {
                // // 加上"/mnt"头
                // fileName = "/mnt" + fileName;
                // }
            }
        }
        return fileName;
    }
}
