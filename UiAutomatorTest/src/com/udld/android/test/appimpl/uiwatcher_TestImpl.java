package com.udld.android.test.appimpl;


import android.os.Bundle;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.udld.android.action.UiActionImpl;
import com.udld.android.test.uiwatch.UIWatcher;


public class uiwatcher_TestImpl extends UiActionImpl {
	public void Test(UiDevice dut, Bundle bundle) throws Exception { 

		new my_ApplicationImpl().go_Myapp(dut,bundle);
		dut.pressBack();		
		UIWatcher vs = new UIWatcher();
		dut.registerWatcher("TESTUIW", vs);
		UiObject app_op= new UiObject(new UiSelector().focusable(true).resourceId("com.helios.appstore:id/installed_app_name"));
        String text = app_op.getText();
        if(!text.equals("")){
        	System.out.println(text);
        	System.out.println("Success: uiwatcher test .");
        }
        else
        	System.out.println("Fail: uiwatcher test .");
        dut.removeWatcher("TESTUIW");	
	}
}
