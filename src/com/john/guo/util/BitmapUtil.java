package com.john.guo.util;

import java.io.File;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class BitmapUtil {
	
	public static Bitmap getResizeBitmap(File file) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		Bitmap resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);		
		return resizeBmp;
	}
	
	public static File getFileByUri(ContentResolver resolver, Uri uri) {
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		final Cursor cursor = resolver.query(uri,
				filePathColumn, null, null, null);
		cursor.moveToFirst();
		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		final File file = new File(picturePath);
		cursor.close();
		return file;
	}

}
