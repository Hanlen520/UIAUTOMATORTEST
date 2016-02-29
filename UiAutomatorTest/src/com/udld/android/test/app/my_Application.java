package com.udld.android.test.app;



import com.udld.android.core.UIATestCase;
import com.udld.android.test.appimpl.my_ApplicationImpl;



public class my_Application extends UIATestCase{

	private my_ApplicationImpl myApp = new my_ApplicationImpl();
	
	public void run_Pr_app() {
		executeOperation(myApp,"run_Pr_app");
	}
	
	public void launch_app() {
		executeOperation(myApp, "launch_app");
	}
	public void go_Myapp() {
		executeOperation(myApp, "goMyapp");
	}
}
