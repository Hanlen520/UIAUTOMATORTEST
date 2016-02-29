package com.udld.android.action;

import java.io.File;

import android.os.Bundle;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.udld.android.core.UIAException;
import com.udld.android.core.UIATestCaseImpl;

public class ActionImpl extends UIATestCaseImpl {
	public static final String KEY_CODE_EXTRA = "key";
	public static final String X_EXTRA = "x";
	public static final String Y_EXTRA = "y";
	public static final String START_X_EXTRA = "x0";
	public static final String START_Y_EXTRA = "y0";
	public static final String END_X_EXTRA = "x1";
	public static final String END_Y_EXTRA = "y1";
	public static final String STEPS_EXTRA = "steps";
	public static final String ORIENTATION_EXTRA = "orientation";
	public static final String STORE_PATH_EXTRA = "store_path";
	public static final String CONTENT_DESC = "content_desc";

	/**
	 * press key code
	 * 
	 * @author udld
	 * @version
	 * @parameter -e key 4
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press key code
	 * @return void
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressKeyCode(UiDevice dut, Bundle bundle) throws UIAException {
		checkExpectedBundleValues(bundle, new String[] { KEY_CODE_EXTRA });
		
		assertTrue("press keyCode " + bundle.getString(KEY_CODE_EXTRA)
				+ " failed", dut.pressKeyCode(Integer.parseInt(bundle
				.getString(KEY_CODE_EXTRA))));
	}

	/**
	 * click center of DUT
	 * 
	 * @author udld
	 * @version
	 * @parameter
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description click center of a device
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void clickCenter(UiDevice dut, Bundle bundle) {
		
		dut.click(dut.getDisplayWidth()/2, dut.getDisplayHeight()/2);
	}
	
	/**
	 * click position(x, y)
	 * 
	 * @author udld
	 * @version
	 * @parameter -e x 100 -e y 100
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description click position(x, y)
	 * @return void
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */
	public void click(UiDevice dut, Bundle bundle) throws UIAException {
		checkExpectedBundleValues(bundle, new String[] { X_EXTRA, Y_EXTRA });
		
		assertTrue(
				"click (" + bundle.getString(X_EXTRA) + ", "
						+ bundle.getString(Y_EXTRA) + ") failed",
				dut.click(Integer.parseInt(bundle.getString(X_EXTRA)),
						Integer.parseInt(bundle.getString(Y_EXTRA))));
	}

	/**
	 * drag from (x0, y0) to (x1, y1) Performs a swipe from one coordinate to
	 * another using the number of steps to determine smoothness and speed. Each
	 * step execution is throttled to 5ms per step.
	 * 
	 * @author udld
	 * @version
	 * @parameter -e x0 4 -e y0 10 -e x1 100 -e y1 100 -e steps 2
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description drag from (x0, y0) to (x1, y1)
	 * @return void
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */
	public void drag(UiDevice dut, Bundle bundle) throws UIAException {
		checkExpectedBundleValues(bundle, new String[] { START_X_EXTRA,
				START_Y_EXTRA, END_X_EXTRA, END_Y_EXTRA, STEPS_EXTRA });
		
		dut.swipe(Integer.parseInt(bundle.getString(START_X_EXTRA)),
				Integer.parseInt(bundle.getString(START_Y_EXTRA)),
				Integer.parseInt(bundle.getString(END_X_EXTRA)),
				Integer.parseInt(bundle.getString(END_Y_EXTRA)),
				Integer.parseInt(bundle.getString(STEPS_EXTRA)));
	}

	/**
	 * sleep phone if phone wakes up, otherwise do nothing
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition none
	 * @description sleep phone if phone wakes up, otherwise do nothing
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void sleep(UiDevice dut, Bundle bundle) throws UIAException{
		/*
		 * This method simply presses the power button if the screen is ON else
		 * it does nothing if the screen is already OFF.
		 */
		
		try {
			dut.sleep();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertFalse("UI operation Device sleep failed: " + e.getMessage(),
					true);
		}

	}

	/**
	 * wake up phone if phone sleeps, otherwise do nothing
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition none
	 * @description wake up phone if phone sleeps, otherwise do nothing
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void wakeUp(UiDevice dut, Bundle bundle) throws UIAException{
		/*
		 * This method simulates pressing the power button if the screen is OFF
		 * else it does nothing if the screen is already ON. If the screen was
		 * OFF and it just got turned ON, this method will insert a 500ms delay
		 * to allow the device time to wake up and accept input.
		 */
		
		try {
			dut.wakeUp();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertFalse(
					"UI operation Device wake up failed: " + e.getMessage(),
					true);
		}

	}

	/**
	 * set orientation as left/right/natural/auto
	 * 
	 * @author udld
	 * @version
	 * @parameter -e orientation left/right/natural/auto
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description set orientation as left/right/natural/auto
	 * @return void
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */
	public void setOrientation(UiDevice dut, Bundle bundle) throws UIAException {
		checkExpectedBundleValues(bundle, new String[] { ORIENTATION_EXTRA });
		String orientation = bundle.getString(ORIENTATION_EXTRA);
		
		try {
			if (orientation.equals("left")) {
				dut.setOrientationLeft();
			} else if (orientation.equals("right")) {
				dut.setOrientationRight();
			} else if (orientation.equals("natural")) {
				dut.setOrientationNatural();
			} else if (orientation.equals("auto")) {
				dut.unfreezeRotation();
			} else {
				assertFalse("invalid orientation: " + orientation, true);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * take screen shot
	 * 
	 * @author szhen11
	 * @version
	 * @parameter -e store_path /sdcard/uiatest/screenshot.png
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description take screen shot
	 * @return void
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */
	public void takeScreenshot(UiDevice dut, Bundle bundle) throws UIAException {
		checkExpectedBundleValues(bundle, new String[] { STORE_PATH_EXTRA });
		String storePath = bundle.getString(STORE_PATH_EXTRA);
		
		File f = new File(storePath);
		assertTrue("take screenshot to " + storePath + " failed",
				dut.takeScreenshot(f));
	}

	/**
	 * press back
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press key code
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressBack(UiDevice dut, Bundle bundle) {
		
		dut.pressBack();

	}

	/**
	 * press delete
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press delete
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressDelete(UiDevice dut, Bundle bundle) {
		
		dut.pressDelete();

	}

	/**
	 * press center
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press center
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressDPadCenter(UiDevice dut, Bundle bundle) {
		
		dut.pressDPadCenter();

	}

	/**
	 * press down
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press down
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressDPadDown(UiDevice dut, Bundle bundle) {
		
		dut.pressDPadDown();

	}

	/**
	 * press left
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press left
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressDPadLeft(UiDevice dut, Bundle bundle) {
		
		dut.pressDPadLeft();

	}

	/**
	 * press right
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press right
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressDPadRight(UiDevice dut, Bundle bundle) {
		
		dut.pressDPadRight();

	}

	/**
	 * press up
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press up
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressDPadUp(UiDevice dut, Bundle bundle) {
		
		dut.pressDPadUp();
	}

	/**
	 * press enter
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press enter
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressEnter(UiDevice dut, Bundle bundle) {
		
		dut.pressEnter();

	}

	/**
	 * press home
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press home
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressHome(UiDevice dut, Bundle bundle) {
		
		dut.pressHome();

	}

	/**
	 * press menu
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press menu
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressMenu(UiDevice dut, Bundle bundle) {
		
		dut.pressMenu();

	}

	/**
	 * press recent apps
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press recent apps
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressRecentApps(UiDevice dut, Bundle bundle) throws UIAException{
		
		try {
			assertTrue("pressRecentApps failed", dut.pressRecentApps());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue("pressRecentApps failed, " + e.getMessage(), false);
		}
	}

	/**
	 * press power
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press Power
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressPower(UiDevice dut, Bundle bundle)throws UIAException {
		
		assertTrue("press keyCode KeyEvent.KEYCODE_POWER("
				+ KeyEvent.KEYCODE_POWER + ") failed",
				dut.pressKeyCode(KeyEvent.KEYCODE_POWER));
	}

	/**
	 * press volume up
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press volume up
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressVolumeUp(UiDevice dut, Bundle bundle) throws UIAException{
		
		assertTrue("press keyCode KeyEvent.KEYCODE_VOLUME_UP("
				+ KeyEvent.KEYCODE_VOLUME_UP + ") failed",
				dut.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP));
	}

	/**
	 * press volume up Ignore Warning
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press volume up Ignore Warning
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressVolumeUpIgnoreWarning(UiDevice dut, Bundle bundle) throws UIAException{
		
		assertTrue("press keyCode KeyEvent.KEYCODE_VOLUME_UP("
				+ KeyEvent.KEYCODE_VOLUME_UP + ") failed",
				dut.pressKeyCode(KeyEvent.KEYCODE_VOLUME_UP));

		UiObject highVolumeWarningText = new UiObject(new UiSelector()
				.className("android.widget.TextView").packageName("android")
				.textContains("Raise volume above safe level"));
		if (highVolumeWarningText.exists()) {
			UiObject highVolumeWarningOKButton = new UiObject(new UiSelector()
					.className("android.widget.Button").packageName("android")
					.text("OK"));
			try {
				highVolumeWarningOKButton.click();
			} catch (UiObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				assertTrue(
						"press keyCode KeyEvent.KEYCODE_VOLUME_UP("
								+ KeyEvent.KEYCODE_VOLUME_UP
								+ "), ignore high volume warning failed,"
								+ e.getMessage(), false);
			}
		}
	}

	/**
	 * press volume down
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press volume up
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressVolumeDown(UiDevice dut, Bundle bundle)throws UIAException {
		
		assertTrue("press keyCode KeyEvent.KEYCODE_VOLUME_DOWN("
				+ KeyEvent.KEYCODE_VOLUME_DOWN + ") failed",
				dut.pressKeyCode(KeyEvent.KEYCODE_VOLUME_DOWN));
	}

	/**
	 * press search
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description press search
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void pressSearch(UiDevice dut, Bundle bundle) {
		
		dut.pressSearch();
	}

	/**
	 * click on a view with content-desc
	 * 
	 * @author udld
	 * @version
	 * @parameter -e content_desc Apps
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description click on a view with content-desc
	 * @return void
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */
	/*
	 * click on a view with content-desc
	 */
	public void clickOnViewDesc(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] { CONTENT_DESC });
		String content_desc = bundle.getString(CONTENT_DESC);
		
		UiObject view = new UiObject(
				new UiSelector().descriptionContains(content_desc));
		assertTrue(String.format(
				"UIATest: click on view with '%s' content_desc failed",
				content_desc), view.click());
	}
	
	/**
	 * double click position(x, y)
	 * @throws UIAException 
	 */
	public void doubleClick(UiDevice dut, Bundle bundle) throws UIAException {
		checkExpectedBundleValues(bundle, new String[] { X_EXTRA, Y_EXTRA });
		assertTrue(
				"double click (" + bundle.getString(X_EXTRA) + ", "
						+ bundle.getString(Y_EXTRA) + ") failed",
				dut.click(Integer.parseInt(bundle.getString(X_EXTRA)),
						Integer.parseInt(bundle.getString(Y_EXTRA))));
		this.sleep(10);
		assertTrue(
				"double click (" + bundle.getString(X_EXTRA) + ", "
						+ bundle.getString(Y_EXTRA) + ") failed",
				dut.click(Integer.parseInt(bundle.getString(X_EXTRA)),
						Integer.parseInt(bundle.getString(Y_EXTRA))));

	}
	/**
	 * long click position(x, y)
	 * 
	 * @author udld
	 * @version
	 * @parameter -e x 4 -e y 10 -e steps 2
	 * @since
	 * @precondition phone wake up and unlock screen
	 * @description long click position(x, y)
	 * @return void
	 * @throws
	 * @see reference
	 * @override
	 */
	public void longClick(UiDevice dut, Bundle bundle) throws UIAException {
		checkExpectedBundleValues(bundle, new String[] { X_EXTRA,
				Y_EXTRA, STEPS_EXTRA });

		dut.swipe(Integer.parseInt(bundle.getString(X_EXTRA)), 
				Integer.parseInt(bundle.getString(Y_EXTRA)), 
				Integer.parseInt(bundle.getString(X_EXTRA)), 
				Integer.parseInt(bundle.getString(Y_EXTRA)), 
				Integer.parseInt(bundle.getString(STEPS_EXTRA)));
	}
	
	/**
	 * isScreenOn
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition none
	 * @description isScreenOn
	 * @return void
	 * @throws RemoteException 
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */	
	public void isScreenOn(UiDevice dut, Bundle bundle) throws RemoteException, UIAException {
		assertTrue("dut isScreenOn", dut.isScreenOn());
	}
	
	/**
	 * isScreenOff
	 * 
	 * @author udld
	 * @version
	 * @parameter none
	 * @since
	 * @precondition none
	 * @description isScreenOff
	 * @return void
	 * @throws RemoteException 
	 * @throws UIAException 
	 * @throws
	 * @see reference
	 * @override
	 */	
	public void isScreenOff(UiDevice dut, Bundle bundle) throws RemoteException, UIAException {
		assertTrue("dut isScreenOff", !dut.isScreenOn());
	}
}
