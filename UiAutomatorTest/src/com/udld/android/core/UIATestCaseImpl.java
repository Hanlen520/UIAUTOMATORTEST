package com.udld.android.core;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import com.android.uiautomator.core.UiDevice;

public class UIATestCaseImpl {
	
	public void checkExpectedBundleValues(Bundle bundle, String[] keys) throws UIAException {
		/*
		 * there are some parameters passing to UIATest which need to translate, like 
		 * string parameters which contain space, here UIATest will translate these parameters
		 * into the target parameters;
		 * this function will also verify whether the parameters passing is correct
		 */
		
		List<String> missingKeys = new ArrayList<String>();
		for(String key : keys) {
			if(!bundle.containsKey(key)) {
				missingKeys.add(key);
			}
		}
		if(!missingKeys.isEmpty()) {
			throw new UIAException("keys missed: "+missingKeys.toString());
		}
	}
	
	public void assertTrue(String str, boolean bl) throws UIAException {
		if (!bl) {
				throw new UIAException(str);
		}
	}
	public void assertFalse(String str, boolean bl) throws UIAException {
		if (bl) {
				throw new UIAException(str);
		}
	}
	
	public void sleep(long sleep_time) {
		try {
			Thread.sleep(sleep_time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void add_log(String l) {
		
		SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");     
		Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间     
		String    str    =    formatter.format(curDate);
		System.out.println(l+"..............."+str);
	}
	
	public void assertEquals(String str, boolean bl1, boolean bl2) {
		if (bl1 == bl2) {
			java.lang.System.out.println(str);
		}
		else {
			try {
				throw new UIAException(str + "failed!");
			} catch (UIAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void saveScreenShotForException(UiDevice dut, String name) {
		SimpleDateFormat uiaDataFormat = new SimpleDateFormat("MMddHHmmssSSS");
		String timestr = uiaDataFormat.format(new java.util.Date());
		String fileName = null;
		if (name != null) {
			fileName = Utilities.STORE_PATH + name + "_" + timestr + ".png";
		} else {
			fileName = Utilities.STORE_PATH + "saveScreenShotForException" + "_" + timestr + ".png";
		}
		
		File f = new File(fileName);
		System.out.println(dut.takeScreenshot(f));

	}
}
