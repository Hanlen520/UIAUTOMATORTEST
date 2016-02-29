package com.udld.android.test.appimpl;

import android.os.Bundle;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.udld.android.action.MyInteractionController;
import com.udld.android.action.UiActionImpl;

public class app_StoreImpl extends UiActionImpl {
	
public void go_Appstore(UiDevice dut, Bundle bundle) throws Exception { 
	
	UiDevice device_T = dut; 
    // go home view
    device_T.pressHome();
    device_T.pressHome();
	sleep(1000);
    //go appStore
    device_T.pressDPadDown();
    device_T.pressDPadDown();
    device_T.pressDPadDown();
    Thread.sleep(1000);
    device_T.pressDPadRight();
    Thread.sleep(1000);
    device_T.pressKeyCode(5004);
    Thread.sleep(1000);

}

public void random_Download_installApp(UiDevice dut, Bundle bundle) throws Exception {
	go_Appstore(dut,bundle);
	UiDevice device_T = dut;
	sleep(2000);
	device_T.pressDPadRight();
	sleep(2000);
	device_T.pressDPadDown();
	sleep(2000);
	device_T.pressKeyCode(5004);
	sleep(2000);
	UiObject app_op= new UiObject(new UiSelector().className("android.widget.TextView").resourceId("com.helios.appstore:id/detaill_install_name_pro"));
	UiObject app_tx= new UiObject(new UiSelector().resourceId("com.helios.appstore:id/detail_app_name_text"));
	String app_Name = "";
	String app_Op = "";
	sleep(1000);
    for(int i =0; i<10; i++){
    	
    	dut.pressKeyCode(5004);
    	sleep(1000);
    	app_Name = app_tx.getText();
    	app_Op = app_op.getText();
    	if(app_Op.equals("安装")){
    		dut.pressKeyCode(5004);
    		break;
    	}
    	else{
    		dut.pressKeyCode(4);
    		sleep(1000);
    		dut.pressDPadRight();
    	}
    		
    }
    sleep(500);     
    if(app_op.exists())
    	app_Op = app_op.getText();
    if(app_Op.equals("下载中")||app_Op.equals("打开应用"))
    	System.out.println("APP INSTALLING........");
    else
    	throw new Exception("Begin install app online failed!!!");
      
    for(int i=0; i<240; i++){ 
    	sleep(1000);
    	app_Op = app_op.getText();
    	if(app_Op.equals("打开应用"))
    		break;   	   	
    }
    if(app_op.exists())
    	app_Op = app_op.getText();
    if(app_Op.equals("打开应用"))
    	System.out.println(app_Name+" download and installed end!!!");
    else
    	throw new Exception(app_Name+" download and install failed in 2 minutes!!!");
    System.out.println("RETURN: "+app_Name);
    //return  app_Name;
    
}
/*
 * @author: chenh
 * @precondition: In appStore， one app is selected 
 * @description: delete this app
 * @parameter: none
 */
public void delete_app(UiDevice dut, Bundle bundle) throws Exception { 

 UiObject open_button= new UiObject(new UiSelector().className("android.widget.TextView").resourceId("com.helios.appstore:id/detaill_install_name_pro"));

 if(open_button.getText().endsWith("打开应用")){
	 
	 dut.pressDPadRight();
	 sleep(500);
	 dut.pressDPadRight();
	 sleep(500);
	 dut.pressDPadCenter();
	 sleep(500);
	 UiObject delete_button= new UiObject(new UiSelector().text("删除").resourceId("android:id/dialogbutton_text"));
	 if(delete_button.exists()){
		 sleep(1000);
		 dut.pressDPadUp();
		 sleep(2000);
		 dut.pressDPadCenter();
		 sleep(4000);
		 if(open_button.getText().endsWith("安装")){
			 UiObject appna_button= new UiObject(new UiSelector().resourceId("com.helios.appstore:id/detail_app_name_text"));
			 System.out.println("Delete app: "+appna_button.getText()+"  success");
			 return;
		 }
		 else
			 throw new Exception("Delete app failed!!!");
		 }
	 else
		 throw new Exception("Delete view not found!!!");
	 
 }
 else
	 System.out.println("The app statu not be installed !!!");
 return;
 
}

public void longPressKeyCode(UiDevice dut, Bundle bundle) throws Exception{
	String keycode=bundle.getString("keycode");
	String time=bundle.getString("time");
	MyInteractionController flong = new MyInteractionController();
	System.out.println("Long press keycode: "+keycode+"  "+time+"  times !!!");
	flong.LongPressKeyCode(Integer.parseInt(keycode),Integer.parseInt(time));
}

}