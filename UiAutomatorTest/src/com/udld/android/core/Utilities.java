package com.udld.android.core;

import java.io.File;
import java.util.ArrayList;

import android.os.Environment;
import android.os.StatFs;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class Utilities extends UiAutomatorTestCase {

	public static final String STORE_PATH = "/sdcard/";
	
    public static final String DCIM = "/mnt/shell/emulated/0";

    public static final String DIRECTORY = DCIM + "/Camera";

    // Match the code in MediaProvider.computeBucketValues().
    public static final String BUCKET_ID =
            String.valueOf(DIRECTORY.toLowerCase().hashCode());

    public static final long UNAVAILABLE = -1L;
    public static final long PREPARING = -2L;
    public static final long UNKNOWN_SIZE = -3L;
    public static final long LOW_STORAGE_THRESHOLD= 50000000;
    
    /*
     * get current storage space status
     * author: udld
     */
    @SuppressWarnings("deprecation")
	public static long getAvailableSpace() {
        String state = Environment.getExternalStorageState();
        System.out.println(String.format("Environment.getExternalStorageState(): %s", state));
        System.out.println(String.format("!Environment.MEDIA_MOUNTED.equals(state): %s", !Environment.MEDIA_MOUNTED.equals(state)));

        if (Environment.MEDIA_CHECKING.equals(state)) {
            return PREPARING;
        }
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return UNAVAILABLE;
        }
        File dir = new File(DIRECTORY);
        dir.mkdirs();
        if (!dir.isDirectory() || !dir.canWrite()) {
            return UNAVAILABLE;
        }
        try {
        	StatFs stat = new StatFs(DIRECTORY);
            return stat.getAvailableBlocks() * (long) stat.getBlockSize();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return UNKNOWN_SIZE;
    }
    /*
     * given a dir path, return file list in the dir
     * author: udld
     */
    public static ArrayList<String> getAllImageFiles(String path){
    	   ArrayList<String> allFiles = new ArrayList<String> ();
    	   File mfile = new File(path);
    	   File[] files = mfile.listFiles();
    	   for (int i = 0; i < files.length; i++){
    		   allFiles.add(files[i].getPath());
    	   }
    	   return allFiles;
    	}
}
