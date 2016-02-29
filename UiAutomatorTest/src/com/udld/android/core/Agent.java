package com.udld.android.core;

import android.os.Bundle;

/**
 * The agent class for the interface of UIATest runner.
 * All agent classes can be retrieved by the Agent class,
 * for example:
 *     adb shell uiautomator runtest Test.jar -c udld_impl.Agent#entry -e object UiAction -e method clickbyText -e text OK
 * @author udld
 * 
 */
public class Agent extends UIATestCase{
	
	/* the command line parameter tag to indicate the object like Action, UiAction, App */
	public static final String OBJECT_NAME_EXTRA = "object";
	/* the command line parameter tag to indicate the method to invoke */ 
	public static final String METHOD_NAME_EXTRA = "method";
	
	private UIATestCaseImpl object=null;
	private String objName = null;
	private String methodName = null;

	public void entry() throws UIAException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		/* retrieve the object and method */
		Bundle bundle = retrieveBundle();
		if (!bundle.containsKey(OBJECT_NAME_EXTRA)) {
			throw new UIAException("keys missed: "+OBJECT_NAME_EXTRA);
		}
		if (!bundle.containsKey(METHOD_NAME_EXTRA)) {
			throw new UIAException("keys missed: "+METHOD_NAME_EXTRA);
		}
		objName = bundle.getString(OBJECT_NAME_EXTRA);
		methodName = bundle.getString(METHOD_NAME_EXTRA);
		
		/* retrieve the object to create an instance of the object */
		String fullClassName = null;		
		if (objName.equals("Action") || objName.equals("UiAction")) {
			fullClassName = "action."+objName+"Impl";			
		}
		else {
			fullClassName = "test_impl."+objName+"Impl";
		}
		
		Class<?> objClass = Class.forName(fullClassName);
		object = (UIATestCaseImpl)objClass.newInstance();		
		
		/* search and invoke the method in the object */
		executeOperation(object, methodName);
	}
}
