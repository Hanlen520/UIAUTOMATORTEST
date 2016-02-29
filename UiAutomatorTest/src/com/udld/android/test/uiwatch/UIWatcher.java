package com.udld.android.test.uiwatch;


import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiWatcher;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;



public class UIWatcher implements UiWatcher {
	
	public boolean checkForCondition() {
		UiObject rl = new UiObject(new UiSelector().focusable(true).resourceId("com.helios.appstore:id/installed_app_name"));
		if (!rl.exists()) {
			try {
				UiDevice.getInstance().pressKeyCode(5004);
				System.out.println("Adb send keyevent of 5004 !");
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			
		}
		else {
			return false;
		}
	}
}
