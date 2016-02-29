package com.udld.android.action;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import com.android.uiautomator.core.UiDevice;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

/**
 *  LongPressKeyCode.
 * 
 */
public class MyInteractionController  {
	
	// Prepare for small operation
	private static final String CLASS_INTERACTION_CONTROLLER = "com.android.uiautomator.core.InteractionController";
	private static final String METHOD_SEND_KEY = "sendKey";  
	private static final String METHOD_INJECT_EVENT_SYNC = "injectEventSync";
	private static final String METHOD_TOUCH_DOWN = "touchDown";  
	private static final String METHOD_TOUCH_UP = "touchUp";      
	private static final String METHOD_TOUCH_MOVE = "touchMove"; 
	public static final String METHOD_PERFORM_MULTI_POINTER_GESTURE = "performMultiPointerGesture";  
    public Integer keyCode;
    public Integer metaState;

    public void LongPressKeyCode(int keycode) {
    	LongPressKeyCode(keycode, 1);
    }
    public void LongPressKeyCode(int keycode, int times) {
	 
	int keyCode = keycode;
	int timeCount = times;
    try {
      //Reflection call----prepare
      UiDevice device = UiDevice.getInstance();    
      Class UiDevice_class = UiDevice.class;
      Field field_UiD = UiDevice_class.getDeclaredField("mUiAutomationBridge");
      field_UiD.setAccessible(true);
      Object uiAutomatorBridge  =  field_UiD.get(device);
      
      Class tmp = Class.forName("com.android.uiautomator.core.UiAutomatorBridge");
      Field field = tmp.getDeclaredField("mInteractionController");
      field.setAccessible(true);
      Object interactionController = field.get(uiAutomatorBridge);
      //Begin action....   
      metaState = 0;
      final long eventTime = SystemClock.uptimeMillis();
      // Send an initial down event
      final KeyEvent downEvent = new KeyEvent(eventTime, eventTime,
          KeyEvent.ACTION_DOWN, keyCode, 0, metaState,
          KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_KEYBOARD);
      System.out.println("......lll,....");
      if (injectEventSync(downEvent,interactionController)) {
        // Send a repeat event. This will cause the FLAG_LONG_PRESS to be set.
        final KeyEvent repeatEvent = KeyEvent.changeTimeRepeat(downEvent,
            eventTime, 1);
        for(int i=0; i<timeCount; i++){
    	   
    	   injectEventSync(repeatEvent,interactionController);
    	   
       }        
        // Finally, send the up event
        System.out.println("......lll,....");
        final KeyEvent upEvent = new KeyEvent(eventTime, eventTime,
            KeyEvent.ACTION_UP, keyCode, 0, metaState,
            KeyCharacterMap.VIRTUAL_KEYBOARD, 0, 0, InputDevice.SOURCE_KEYBOARD);
        injectEventSync(upEvent,interactionController);

      }
      return ;
    } catch (final Exception e) {
      return ;
    }
  }
  
  public boolean injectEventSync(InputEvent event,Object interactionController) {
	    return (Boolean) invoke(method(CLASS_INTERACTION_CONTROLLER, METHOD_INJECT_EVENT_SYNC, InputEvent.class), interactionController, event);
	  }
  
  public static Class getClass(final String name) {
	    try {
	      return Class.forName(name);
	    } catch (final ClassNotFoundException e) {
	      final String msg = String.format("unable to find class %s", name);
	      throw new RuntimeException(msg, e);
	    }
	  }
  
  public static Object invoke(final Method method, final Object object,
	      final Object... parameters) {
	    try {
	      return method.invoke(object, parameters);
	    } catch (final Exception e) {
	      final String msg = String.format(
	          "error while invoking method %s on object %s with parameters %s",
	          method, object, Arrays.toString(parameters));
	      //Logger.error(msg + " " + e.getMessage());
	      throw new RuntimeException(msg, e);
	    }
	  }

  public static Method method(final Class clazz, final String methodName,
	      final Class... parameterTypes) {
	    try {
	      final Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
	      method.setAccessible(true);

	      return method;
	    } catch (final Exception e) {
	      final String msg = String
	          .format(
	              "error while getting method %s from class %s with parameter types %s",
	              methodName, clazz, Arrays.toString(parameterTypes));
	      //Logger.error(msg + " " + e.getMessage());
	      throw new RuntimeException(msg, e);
	    }
	  }

  public static Method method(final String className, final String method,
	      final Class... parameterTypes) {
	    return method(getClass(className), method, parameterTypes);
	}
}
