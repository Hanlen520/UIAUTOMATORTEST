package com.udld.android.test.app;

import com.udld.android.core.UIATestCase;
import com.udld.android.test.appimpl.app_StoreImpl;



public class app_Store extends UIATestCase{

	private app_StoreImpl app_Store = new app_StoreImpl();
	
	public void go_Appstore() {
		executeOperation(app_Store, "go_Appstore");
	}
	
	public void random_Download_installApp() {
		executeOperation(app_Store, "random_Download_installApp");
	}
	public void delete_app(){
		executeOperation(app_Store, "delete_app");
	}
	public void longPressKeyCode(){
		executeOperation(app_Store, "longPressKeyCode");
	}
}