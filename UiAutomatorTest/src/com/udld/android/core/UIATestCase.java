package com.udld.android.core;


import java.io.File;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.testrunner.IAutomationSupport;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
public class UIATestCase extends UiAutomatorTestCase {
	
	/* Parameter passing handling*/
	private static final String[] STRING_REPLACE = new String[]{"{{","}}"};
	private static final String[] STRING_REPLACE_REX = new String[]{"\\{\\{","\\}\\}"};	
	private static final Map<String , String> STRING_REPLACE_MAP = new HashMap<String , String>();
	public static IAutomationSupport AUTOMATION_SUPPORT = null;

	public  UiDevice dut = null; 
	public 	String methodName = null;
	
	public Bundle  getParams() {
		/* Get command line parameters. On the command line when passing -e key value pairs, 
		 * the Bundle will have the key value pairs conveniently available to the tests. 
		 */
		return super.getParams();
	}
	
	/*
	 * @deprecated
	 */
	public Bundle retrieveVerifiedBundle(String[] keys) {
		/*
		 * there are some parameters passing to UIATest which need to translate, like 
		 * string parameters which contain space, here UIATest will translate these parameters
		 * into the target parameters;
		 * this function will also verify whether the parameters passing is correct
		 */

		Bundle bundle = retrieveBundle();
		List<String> missingKeys = new ArrayList<String>();
		for(String key : keys) {
			if(!bundle.containsKey(key)) {
				missingKeys.add(key);
			}
		}
		if(!missingKeys.isEmpty()) {
			assertFalse("keys missed: "+missingKeys.toString(), true);
		}
				
		return bundle;
	}
	
	public Bundle retrieveBundle() {
		/*
		 * there are some parameters passing to UIATest which need to translate, like 
		 * string parameters which contain space, here UIATest will translate these parameters
		 * into the target parameters;
		 * this function will also verify whether the parameters passing is correct
		 */
		STRING_REPLACE_MAP.clear();
		STRING_REPLACE_MAP.put("SPACE", " ");
		STRING_REPLACE_MAP.put("EMPTY", "");
		STRING_REPLACE_MAP.put("SINGLEQUOTES", "'");
		STRING_REPLACE_MAP.put("DOUBLEQUOTES", "\"");
		STRING_REPLACE_MAP.put("LEFTPARENTHESIS", "(");
		STRING_REPLACE_MAP.put("RIGHTPARENTHESIS", ")");
		String str=null;

		Bundle bundle = getParams();
		for(String key : bundle.keySet()) {
			str = bundle.getString(key);
			Iterator<Entry<String, String>> iter = STRING_REPLACE_MAP.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) iter.next();
				String mapString = STRING_REPLACE_REX[0] + entry.getKey() + STRING_REPLACE_REX[1];
				str = str.replaceAll(mapString, entry.getValue());
			}
			bundle.putString(key, str);
		}
				
		return bundle;
	}
	

	/*
	 * UI Operations entrance 
	 * use java reflection mechanism to invoke the implemented method 
	 */
	public void executeOperation(UIATestCaseImpl impl, String methodName) {
		//Initialize local variables
		boolean didExecute = false;
		AUTOMATION_SUPPORT = this.getAutomationSupport();//initialize the AUTOMATION_SUPPORT
		Object rObject = null;
		dut = getUiDevice();

		// Get all public methods of this object.
		Method[] publicMethods = impl.getClass().getMethods();

		//Iterate on declared methods in order to find the one
		//that must be executed.
        for(Method method : publicMethods) {
        	if(method.getName().equals(methodName)) {
				try {
					rObject = method.invoke(impl, dut, retrieveBundle());
					if (rObject!=null) {
						AUTOMATION_SUPPORT.sendStatus(0, (Bundle)(rObject));
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					assertUIATFalse(e.getMessage());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					assertUIATFalse(e.getMessage());
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();					
				    try {
				    	//e.getCause().printStackTrace();
						throw e.getCause();
					} 
				    catch (UIAException e1) {
				    	assertUIATFalse(e1.getMessage());
				    }
				    catch (UiObjectNotFoundException e1) {
				    	SimpleDateFormat uiaDataFormat = new SimpleDateFormat("MMddHHmmssSSS");
				    	String timestr = uiaDataFormat.format(new java.util.Date());
				    	String fileName = Utilities.STORE_PATH+
				    		impl.getClass().getName().split("\\.")[impl.getClass().getName().split("\\.").length-1]+
				    		"#"+methodName+ "_" +timestr + ".png";			    	
				    	File f = new File(fileName);
				    	System.out.println(dut.takeScreenshot(f));
				    	assertUIATFalse(e1.getMessage()+", UiObject Not Found; screenshot taken: "+fileName);
				    }
				    catch (Throwable e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
				    	assertUIATFalse(e1.getMessage());
					}
				}
        		didExecute = true;
        	}
        }
        
        if(didExecute) {
        	assertUIATTrue(this.getName()+":"+methodName+" passed");
        }
	}
	
	public void assertUIATTrue(String msg) {
		assertTrue("UIAT: "+msg, true);
	}

	public void assertUIATFalse(String msg) {
		assertFalse("UIAT: "+msg, true);
	}
}
