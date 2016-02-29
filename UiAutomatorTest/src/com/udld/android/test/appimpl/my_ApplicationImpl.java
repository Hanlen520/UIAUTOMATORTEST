package com.udld.android.test.appimpl;

import java.util.HashSet;
import android.os.Bundle;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiSelector;
import com.udld.android.action.UiActionImpl;


public class my_ApplicationImpl extends UiActionImpl {
	
public void go_Myapp(UiDevice dut, Bundle bundle) throws Exception { 
		
		UiDevice device_T = dut;
	      // go home view
	      device_T.pressHome();
	      device_T.pressHome();
		  Thread.sleep(1000);
	
	      //go myApplication
	      device_T.pressDPadDown();
	      device_T.pressDPadDown();
	      device_T.pressDPadDown();
	      Thread.sleep(1000);
	      device_T.pressDPadRight();
	      device_T.pressDPadRight();
	      Thread.sleep(1000);
	      device_T.pressKeyCode(5004);
	      Thread.sleep(1000);

	}
	public void launch_app(UiDevice dut, Bundle bundle) throws Exception { 
		
		go_Myapp(dut, bundle);
		UiDevice device_T = dut;
	     String app_name=bundle.getString("app_name");
	    if(app_name.equals("tvtaobao"))
	    	app_name="电视淘宝";
	    UiObject app_op= new UiObject(new UiSelector().text(app_name).focusable(true).resourceId("com.helios.appstore:id/installed_app_name"));
	   
	    for(int i=1; i<30; i++){
	    	  
	    	  // open  APK
	    	  if(app_op.exists()){
	    		  device_T.pressKeyCode(5004);
	    		  break;    		  		  
	    	  }
	    	  if(i==29)
	    		  throw new Exception("The 30th installed app not found:  "+"  "+app_name);
            //choose the app you want.
	    	  int l_or_r = ((int)(i/7))%2;
	    	  int k = i%7;
	    	  if(k!=0){
	    		  if(l_or_r==0)
	    			  device_T.pressDPadRight();
	    		  else
	    			  device_T.pressDPadLeft();    		  
	    	  }
	    	  else
	    		  device_T.pressDPadDown();
	    	  sleep(3000);
	    	
	         //**********************************
		      
	      }
	      
	  }   
	    	

	
	 public void run_Pr_app(UiDevice dut, Bundle bundle) throws Exception {   
		 			
		  go_Myapp(dut, bundle);
		  UiDevice device_T = dut;
	      UiObject app_op= new UiObject(new UiSelector().focusable(true).resourceId("com.helios.appstore:id/installed_app_name"));

	      HashSet<String> app_l =new HashSet<String>();//"虾米音乐","一键加速","本地播放","设置","音乐电台","屏保"
	      
	      app_l.add("虾米音乐");	app_l.add("一键加速");		app_l.add("本地播放");
	      app_l.add("设置");      app_l.add("信号源");
	      app_l.add("音乐电台");    app_l.add("屏保");
	      int op_Num =0;
	      for(int i=1; i<10; i++){
	    	  
	    	  // open pr_installed APK
	    	  if(app_op.exists()){
	    		  
	    		  String app_na = app_op.getText();
	    		  if(app_l.contains(app_na)){
	    			  add_log("Open  "+app_na);
	    			  if(app_op.isFocusable())
	    				  System.out.println("focusable of success");
	    			  op_Num++;
	    			  device_T.pressKeyCode(5004);  //open APK
	    			  sleep(3000);
	    			  add_log("Back my_App from  "+app_na);
	    			  if(app_na.equals("一键加速")){
	    				  sleep(10000);
	    				  if(!app_op.exists())
	    					  device_T.pressBack();
	    			  }				  
	    			  else
	    				  device_T.pressBack();
	    			  sleep(1000);
	    			  sleep(3000);
	    			  if(!app_op.exists())
	    				  throw new Exception("From apk back to app_installed failed"+"  "+app_na);
	    			  
	    		  }		  
	    	  }
              //choose the app you want.
	    	  int l_or_r = ((int)(i/7))%2;
	    	  int k = i%7;
	    	  if(k!=0){
	    		  if(l_or_r==0)
	    			  device_T.pressDPadRight();
	    		  else
	    			  device_T.pressDPadLeft();    		  
	    	  }
	    	  else
	    		  device_T.pressDPadDown();
	    	  sleep(3000);
	    	
	         //**********************************
	    	 if(op_Num==6)
	    		 break;
		      
	      }
	      device_T.pressHome();
	      device_T.pressHome();
	      sleep(2000);
	      //getAutomationSupport().sendStatus(Activity.RESULT_OK, bundle);
	  }   
	}


