package com.udld.android.test.app;


import com.udld.android.core.UIATestCase;
import com.udld.android.test.appimpl.uiwatcher_TestImpl;


public class uiwatcher_Test extends UIATestCase{
private uiwatcher_TestImpl uiwt = new uiwatcher_TestImpl();
	
	public void Test() {
		executeOperation(uiwt, "Test");
	}
	

}