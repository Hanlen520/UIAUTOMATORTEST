package com.udld.android.test.appimpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.udld.android.action.UiActionImpl;
import com.udld.android.core.UIAException;

public class SystemImpl extends UiActionImpl {
	public static final String APP_EXTRA = "app";
	public static final String PACKAGE_EXTRA = "package";
	public static final String X_EXTRA = "x";
	public static final String Y_EXTRA = "y";
	public static final String W_EXTRA = "w";
	public static final String H_EXTRA = "h";
	public static final String STORE_PATH_EXTRA = "store_path";
	
	public void unlockScreen(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException {
			
	}
	
	public void launchApp(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {APP_EXTRA});		
	}
	
	public void exitAppTillHome(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException {
		UiObject okButton = new UiObject(new UiSelector().className("android.widget.Button").text("OK"));
		if (okButton.exists()) {
			okButton.click();
		}
		
		int counter = 0;
		UiObject homeScreen = new UiObject(new UiSelector().className("android.view.View").descriptionContains("Home screen"));	
		while(!homeScreen.exists() || counter<10){
			dut.pressBack();
			counter++;
		}
	}
	
	public void disableLock(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException, InterruptedException {		
		UiScrollable settingList = new UiScrollable(new UiSelector().className("android.widget.ListView").packageName("com.android.settings"));
		UiObject security = settingList.getChildByText(new UiSelector().className("android.widget.TextView").packageName("com.android.settings"), "Security");
		if (security.exists()) {
			security.click();
			UiObject slideScreenlock = new UiObject(new UiSelector().className("android.widget.TextView").text("Slide"));
			UiObject noneScreenlock = new UiObject(new UiSelector().className("android.widget.TextView").text("None"));
			if (slideScreenlock.exists()) {
				slideScreenlock.click();
				noneScreenlock.click();
				Thread.sleep(2000);
				if (slideScreenlock.exists()) {
					throw new UIAException("Can't disable screen lock from Slide!");
				}
			}
		}
	}
	

	/**
	 * dump UI View to Image file
	 * 
	 * @author cchen59
	 * @version
	 * @parameter
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description take screen shot, and get sub image from screen shot, internal use in uia
	 * @return void
	 * @throws IOException 
	 * @see reference
	 * @override
	 */
	public void dumpImage(UiDevice dut, int x, int y, int w, int h, String storePath) throws IOException,UIAException {
		String tmpPath = storePath+".tmp";
		File tmpFile = new File(tmpPath);
		if(tmpFile.exists())
			tmpFile.deleteOnExit();
		assertTrue("take screenshot to temp file, " + tmpPath + ", failed",
				dut.takeScreenshot(tmpFile, 1.0f, 100));
		Bitmap screenshotBitmap = BitmapFactory.decodeFile(tmpPath);
		if (x < 0) {
			x = 0;
		}

		if (y < 0) {
			y = 0;
		}

		if (w < 0) {
			w = screenshotBitmap.getWidth();
		}

		if (h < 0) {
			h = screenshotBitmap.getHeight();
		}
		
		System.out.println("dump view(x="+x+",y="+y+",w="+w+",h="+h+"), to "+storePath);
		Bitmap subBitmap = Bitmap.createBitmap(screenshotBitmap, x, y, w, h);
		File file = new File(storePath);
		if(file.exists())
			file.delete();
		FileOutputStream fOS = new FileOutputStream(file);
		subBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOS);
		fOS.flush();
		fOS.close();
		if(tmpFile.exists())
			tmpFile.delete();
	}
	
	/**
	 * dump UI View to Image file
	 * 
	 * @author cchen59
	 * @version
	 * @parameter -e x 296 -e y 1136 -e w 128 -e store_path /sdcard/uiatest/ui_music_play.png
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description take screen shot, and get sub image from screen shot
	 * @return void
	 * @throws IOException 
	 * @throws UIAException 
	 * @see reference
	 * @override
	 */
	public void dumpImageByRectangle(UiDevice dut, Bundle bundle) throws IOException, UIAException {
		checkExpectedBundleValues(bundle, new String[] { STORE_PATH_EXTRA, 
				X_EXTRA, Y_EXTRA, W_EXTRA, H_EXTRA });
		
		int x = Integer.parseInt(bundle.getString(X_EXTRA));
		int y = Integer.parseInt(bundle.getString(Y_EXTRA));
		int w = Integer.parseInt(bundle.getString(W_EXTRA));
		int h = Integer.parseInt(bundle.getString(H_EXTRA));
		String storePath = bundle.getString(STORE_PATH_EXTRA);
		dumpImage(dut, x, y, w, h, storePath);
	}
	
}
