package com.udld.android.action;

import android.os.Bundle;
import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.udld.android.core.UIAException;

public class UiActionImpl extends ActionImpl{
	public static final String TEXT_EXTRA = "text";
	public static final String CLASS_NAME_EXTRA = "class_name";
	public static final String CLASS_NAME_EXTRA_PAR = "class_name_par";
	public static final String DESCRIPTION_EXTRA = "description";
	public static final String DIRECTION_EXTRA = "direction";
	public static final String STEPS_EXTRA = "steps";
	public static final String LONG_ClICK = "long_click";
	public static final String SCROLLABLE_ORIENTATION_EXTRA = "scrollable_orientation";
	public static final String INDEX_EXTRA = "index";
	public static final String CHECK_EXTRA = "check";	
	public static final String IGNORE_ERROR = "ignore_error";
	public static final String TIMEOUT_SECONDS = "timeout_seconds";
	
	protected void waitForUiObjectExist(UiObject muUiObject, Bundle bundle) {
		int index = 0;
		int timeout_seconds = 5;
		if (bundle.containsKey(TIMEOUT_SECONDS)) {
			timeout_seconds = Integer.parseInt(bundle.getString(TIMEOUT_SECONDS));
		}
		while (!muUiObject.exists() && (index < timeout_seconds*2)) {
			try {
				Thread.sleep(500);
				index ++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void clickbyText(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		boolean check = false;
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		UiObject textObject = new UiObject(new UiSelector().text(text));
		waitForUiObjectExist(textObject, bundle);
		if (!check || textObject.exists()) {
			if (ignore.toLowerCase().equals("no"))
				textObject.click();
			else {
				if (textObject.exists())
					textObject.click();
			}
		}
	}	
	
	public void longClickbyText(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		UiObject textObject = new UiObject(new UiSelector().text(text));
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no"))
			textObject.longClick();
		else {
			if (textObject.exists())
				textObject.longClick();
		}
	}
	public void longClickbyDescriptionContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {DESCRIPTION_EXTRA});
		String text = bundle.getString(DESCRIPTION_EXTRA);
		
		UiObject textObject = new UiObject(new UiSelector().descriptionContains(text));
		waitForUiObjectExist(textObject, bundle);
		textObject.longClick();
	}
	/**
	 * click view by TextMatches
	 * @param text
	 * @param isLongClick if true, long click the uiobj
	 * @throws UiObjectNotFoundException
	 * @throws UIAException 
	 */
	public void clickbyTextMatches(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		boolean isLongClick = false;
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		if (bundle.containsKey(LONG_ClICK)){
			isLongClick = Boolean.parseBoolean(bundle.getString(LONG_ClICK));
		}
		
		UiObject textObject = new UiObject(new UiSelector().textMatches(text));	
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (isLongClick){
				textObject.longClick();
			} else {
				textObject.click();
			}
		} else {
			if (isLongClick){
				if (textObject.exists())
					textObject.longClick();
			} else {
				if (textObject.exists())
					textObject.click();
			}
		}
	}


	/**
	 * click view by TextContains
	 * @param text
	 * @param isLongClick if true, long click the uiobj
	 * @throws UiObjectNotFoundException
	 * @throws UIAException 
	 */
	public void clickbyTextContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		boolean isLongClick = false;
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		if (bundle.containsKey(LONG_ClICK)){
			isLongClick = Boolean.parseBoolean(bundle.getString(LONG_ClICK));
		}
		
		UiObject textObject = new UiObject(new UiSelector().textContains(text));		
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (isLongClick){
				textObject.longClick();
			} else {
				textObject.click();
			}
		}
		else {
			if (isLongClick){
				if (textObject.exists())
					textObject.longClick();
			} else {
				if (textObject.exists())
					textObject.click();
			}
		}
	}
	
	/**
	 * click view by textMatches
	 * @param text
	 * @param isLongClick if true, long click the uiobj
	 * @throws UiObjectNotFoundException
	 * @throws UIAException 
	 */
	public void clickbyClassnameAndTextMatches(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {CLASS_NAME_EXTRA, TEXT_EXTRA});
		String className = bundle.getString(CLASS_NAME_EXTRA);
		String text = bundle.getString(TEXT_EXTRA);
		boolean isLongClick = false;
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		if (bundle.containsKey(LONG_ClICK)){
			isLongClick = Boolean.parseBoolean(bundle.getString(LONG_ClICK));
		}
		
		UiObject uiObject = new UiObject(new UiSelector().className(className).textMatches(text));	
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (isLongClick){
				uiObject.longClick();
			} else {
				uiObject.click();
			}
		}
		else {
			if (isLongClick){
				if (uiObject.exists())
					uiObject.longClick();
			} else {
				if (uiObject.exists())
					uiObject.click();
			}
		}
	}
	
	/**
	 * click view by Classname and TextContains
	 * @param text
	 * @param isLongClick if true, long click the uiobj
	 * @throws UiObjectNotFoundException
	 * @throws UIAException 
	 */
	public void clickbyClassnameAndTextContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {CLASS_NAME_EXTRA, TEXT_EXTRA});
		String className = bundle.getString(CLASS_NAME_EXTRA);
		String text = bundle.getString(TEXT_EXTRA);
		boolean isLongClick = false;
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		if (bundle.containsKey(LONG_ClICK)){
			isLongClick = Boolean.parseBoolean(bundle.getString(LONG_ClICK));
		}
		
		UiObject uiObject = new UiObject(new UiSelector().className(className).textContains(text));		
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (isLongClick){
				uiObject.longClick();
			} else {
				uiObject.click();
			}
		}
		else {
			if (uiObject.exists()) {
				if (isLongClick){
					uiObject.longClick();
				} else {
					uiObject.click();
				}
			}
		}
	}
	
	public void swipbyText(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA, DIRECTION_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		String direciton = bundle.getString(DIRECTION_EXTRA);
		int step = 2;
		if (bundle.containsKey(STEPS_EXTRA)) {
			step = Integer.parseInt(bundle.getString(STEPS_EXTRA));
		}
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject textObject = new UiObject(new UiSelector().text(text));
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		} else if (textObject.exists()) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		}
		
	}
	
	public void swipbyTextContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA, DIRECTION_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		String direciton = bundle.getString(DIRECTION_EXTRA);
		int step = 2;
		if (bundle.containsKey(STEPS_EXTRA)) {
			step = Integer.parseInt(bundle.getString(STEPS_EXTRA));
		}
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject textObject = new UiObject(new UiSelector().textContains(text));
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		} else if (textObject.exists()) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		}
		
	}	
	
	public void swipbyDescription(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {DESCRIPTION_EXTRA, DIRECTION_EXTRA});
		String decription = bundle.getString(DESCRIPTION_EXTRA);
		String direciton = bundle.getString(DIRECTION_EXTRA);
		int step = 2;
		if (bundle.containsKey(STEPS_EXTRA)) {
			step = Integer.parseInt(bundle.getString(STEPS_EXTRA));
		}
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject textObject = new UiObject(new UiSelector().description(decription));
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		} else if (textObject.exists()) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		}
		
	}
	
	public void swipbyDescriptionContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {DESCRIPTION_EXTRA, DIRECTION_EXTRA});
		String decription = bundle.getString(DESCRIPTION_EXTRA);
		String direciton = bundle.getString(DIRECTION_EXTRA);
		int step = 2;
		if (bundle.containsKey(STEPS_EXTRA)) {
			step = Integer.parseInt(bundle.getString(STEPS_EXTRA));
		}
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject textObject = new UiObject(new UiSelector().descriptionContains(decription));
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		} else if (textObject.exists()) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		}
		
	}
	
	public void swipbyClassName(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {CLASS_NAME_EXTRA, DIRECTION_EXTRA});
		String className = bundle.getString(CLASS_NAME_EXTRA);
		String direciton = bundle.getString(DIRECTION_EXTRA);
		int step = 2;
		if (bundle.containsKey(STEPS_EXTRA)) {
			step = Integer.parseInt(bundle.getString(STEPS_EXTRA));
		}
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject textObject = new UiObject(new UiSelector().className(className));
		waitForUiObjectExist(textObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		} else if (textObject.exists()) {
			if (direciton.toLowerCase().equals("up")) {
				textObject.swipeUp(step);
			}
			else if (direciton.toLowerCase().equals("down")) {
				textObject.swipeDown(step);
			}
			else if (direciton.toLowerCase().equals("left")) {
				textObject.swipeLeft(step);
			}
			else if (direciton.toLowerCase().equals("right")) {
				textObject.swipeRight(step);
			}
		}
		
	}	

	public void clickbyClassName(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {CLASS_NAME_EXTRA});
		String className = bundle.getString(CLASS_NAME_EXTRA);
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject uiObject = new UiObject(new UiSelector().className(className));
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no"))
			uiObject.click();
		else {
			if (uiObject.exists())
				uiObject.click();
		}
	}

	public void clickbyClassNameMatches(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {CLASS_NAME_EXTRA});
		String className = bundle.getString(CLASS_NAME_EXTRA);
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject uiObject = new UiObject(new UiSelector().classNameMatches(className));		
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no"))
			uiObject.click();
		else {
			if (uiObject.exists())
				uiObject.click();
		}	
	}
	
	public void clickbyDescription(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {DESCRIPTION_EXTRA});
		String desc = bundle.getString(DESCRIPTION_EXTRA);
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject uiObject = new UiObject(new UiSelector().description(desc));		
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no"))
			uiObject.click();
		else {
			if (uiObject.exists())
				uiObject.click();
		}
	}	
	
	public void clickbyDescriptionContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {DESCRIPTION_EXTRA});
		String desc = bundle.getString(DESCRIPTION_EXTRA);
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject uiObject = new UiObject(new UiSelector().descriptionContains(desc));		
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no"))
			uiObject.click();
		else {
			if (uiObject.exists())
				uiObject.click();
		}
	}	
	public void clickbyTextContainsfromListView(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		String scollableOrientation = "vertical";
		if (bundle.containsKey(SCROLLABLE_ORIENTATION_EXTRA)) {
			scollableOrientation = bundle.getString(scollableOrientation);
		}
		
		UiObject listObject = new UiObject(new UiSelector().className(android.widget.ListView.class.getName()));
		UiObject uiObject = null;
		if (listObject.isScrollable()) {
			UiScrollable scrollObject = new UiScrollable(listObject.getSelector());
			if (scollableOrientation.equalsIgnoreCase("horizontal")) {
				scrollObject.setAsVerticalList();
			}
			scrollObject.flingToBeginning(5);
			do {
				uiObject = scrollObject.getChild(new UiSelector().textContains(text));
				if (uiObject.exists()) {
					break;
				}
			} while (scrollObject.scrollForward());
			//uiObject = scrollObject.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), text);
		}
		else {
			uiObject = listObject.getChild(new UiSelector().textContains(text));
		}
		waitForUiObjectExist(uiObject, bundle);
		uiObject.click();	
	}
	public void clickbyTextfromListView(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		String scollableOrientation = "vertical";
		if (bundle.containsKey(SCROLLABLE_ORIENTATION_EXTRA)) {
			scollableOrientation = bundle.getString(scollableOrientation);
		}
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject listObject = new UiObject(new UiSelector().className(android.widget.ListView.class.getName()));
		UiObject uiObject = null;
		if (listObject.isScrollable()) {
			UiScrollable scrollObject = new UiScrollable(listObject.getSelector());
			if (scollableOrientation.equalsIgnoreCase("horizontal")) {
				scrollObject.setAsVerticalList();
			}
			scrollObject.flingToBeginning(5);
			do {
				uiObject = scrollObject.getChild(new UiSelector().text(text));
				if (uiObject.exists()) {
					break;
				}
			} while (scrollObject.scrollForward());
			//uiObject = scrollObject.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), text);
		}
		else {
			uiObject = listObject.getChild(new UiSelector().text(text));
		}
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no"))
			uiObject.click();
		else {
			if (uiObject.exists())
				uiObject.click();
		}	
	}
	
	public void clickbyTextfromGridView(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		String scollableOrientation = "vertical";
		if (bundle.containsKey(SCROLLABLE_ORIENTATION_EXTRA)) {
			scollableOrientation = bundle.getString(scollableOrientation);
		}
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		UiObject gridObject = new UiObject(new UiSelector().className(android.widget.GridView.class.getName()));
		UiObject uiObject = null;
		if (gridObject.isScrollable()) {
			UiScrollable scrollObject = new UiScrollable(gridObject.getSelector());
			if (scollableOrientation.equalsIgnoreCase("horizontal")) {
				scrollObject.setAsVerticalList();
			}
			scrollObject.flingToBeginning(5);
			do {
				uiObject = scrollObject.getChild(new UiSelector().text(text));
				if (uiObject.exists()) {
					break;
				}
			} while (scrollObject.scrollForward());
			//uiObject = scrollObject.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), text);
		}
		else {
			uiObject = gridObject.getChild(new UiSelector().text(text));
		}
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no"))
			uiObject.click();
		else {
			if (uiObject.exists())
				uiObject.click();
		}	
	}
	
	public void clickbyTextfromScrollView(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException{
		UiScrollable scroll = new UiScrollable(new UiSelector().className(android.widget.ScrollView.class.getName()));
		scroll.getChildByText(new UiSelector().text(bundle.getString("text")), bundle.getString("text")).click();
	}
	public void clickbyDescriptionContainsfromListView(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {DESCRIPTION_EXTRA});
		String text = bundle.getString(DESCRIPTION_EXTRA);
		String scollableOrientation = "vertical";
		if (bundle.containsKey(SCROLLABLE_ORIENTATION_EXTRA)) {
			scollableOrientation = bundle.getString(scollableOrientation);
		}
		
		UiObject listObject = new UiObject(new UiSelector().className(android.widget.ListView.class.getName()));
		UiObject uiObject = null;
		if (listObject.isScrollable()) {
			UiScrollable scrollObject = new UiScrollable(listObject.getSelector());
			if (scollableOrientation.equalsIgnoreCase("horizontal")) {
				scrollObject.setAsVerticalList();
			}
			scrollObject.flingToBeginning(5);
			do {
				uiObject = scrollObject.getChild(new UiSelector().descriptionContains(text));
				if (uiObject.exists()) {
					break;
				}
			} while (scrollObject.scrollForward());
			//uiObject = scrollObject.getChildByText(new UiSelector().className(android.widget.TextView.class.getName()), text);
		}
		else {
			uiObject = listObject.getChild(new UiSelector().descriptionContains(text));
		}
		waitForUiObjectExist(uiObject, bundle);
		uiObject.click();		
	}	
	public void setTextinEditText(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		String text = bundle.getString(TEXT_EXTRA);
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		
		int index = -1;
		if (bundle.containsKey(INDEX_EXTRA)) {
			index = Integer.parseInt(bundle.getString(INDEX_EXTRA));
		}
		UiObject objEditText = null;
		if (index<0) {
			objEditText = new UiObject(new UiSelector().className(android.widget.EditText.class.getName()));
		}
		else {
			objEditText = new UiObject(new UiSelector().className(android.widget.EditText.class.getName()).index(index));
		}
		waitForUiObjectExist(objEditText, bundle);
		if (ignore.toLowerCase().equals("no"))
			objEditText.setText(text);
		else {
			if (objEditText.exists())
				objEditText.setText(text);
		}
	}	
	
	public Bundle getTextByContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {TEXT_EXTRA});
		
		String text = bundle.getString(TEXT_EXTRA);
		
		UiObject textObject = new UiObject(new UiSelector().textContains(text));		
		waitForUiObjectExist(textObject, bundle);
		if (textObject.exists() ) {
			Bundle rbd = new Bundle();
			rbd.putString("text", textObject.getText());
			return rbd;
		}
		else {
			return null;
		}

	}
	
	public void clickbyIndexAndClassnameInClassname(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {INDEX_EXTRA, CLASS_NAME_EXTRA, CLASS_NAME_EXTRA_PAR});
		int index = Integer.parseInt(bundle.getString(INDEX_EXTRA));
		String classname = bundle.getString(CLASS_NAME_EXTRA);
		String classname_par = bundle.getString(CLASS_NAME_EXTRA_PAR);
		UiSelector objsSelector = new UiSelector().className(classname).index(index);
		UiObject targetObject = new UiObject(new UiSelector().className(classname_par).childSelector(objsSelector));
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		if (ignore.toLowerCase().equals("no")) {
			targetObject.clickAndWaitForNewWindow();
		}
		else {
			if (targetObject.exists())
				targetObject.clickAndWaitForNewWindow();
		}
	}
	
	/**
	 * click view by DscriptionMatches
	 * @param text
	 * @param isLongClick if true, long click the uiobj
	 * @throws UiObjectNotFoundException
	 * @throws UIAException 
	 */
	public void clickbyClassnameAndDescriptionMatches(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {CLASS_NAME_EXTRA, DESCRIPTION_EXTRA});
		String className = bundle.getString(CLASS_NAME_EXTRA);
		String text = bundle.getString(DESCRIPTION_EXTRA);
		boolean isLongClick = false;
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		if (bundle.containsKey(LONG_ClICK)){
			isLongClick = Boolean.parseBoolean(bundle.getString(LONG_ClICK));
		}
		
		UiObject uiObject = new UiObject(new UiSelector().className(className).descriptionMatches(text));	
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (isLongClick){
				uiObject.longClick();
			} else {
				uiObject.click();
			}
		}
		else {
			if (isLongClick){
				if (uiObject.exists())
					uiObject.longClick();
			} else {
				if (uiObject.exists())
					uiObject.click();
			}
		}
	}
	
	/**
	 * click view by Classname and DescriptionContains
	 * @param text
	 * @param isLongClick if true, long click the uiobj
	 * @throws UiObjectNotFoundException
	 * @throws UIAException 
	 */
	public void clickbyClassnameAndDescriptionContains(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		checkExpectedBundleValues(bundle, new String[] {CLASS_NAME_EXTRA, DESCRIPTION_EXTRA});
		String className = bundle.getString(CLASS_NAME_EXTRA);
		String text = bundle.getString(DESCRIPTION_EXTRA);
		boolean isLongClick = false;
		String ignore = "no";
		if (bundle.containsKey(IGNORE_ERROR)) {
			ignore = bundle.getString(IGNORE_ERROR);
		}
		if (bundle.containsKey(LONG_ClICK)){
			isLongClick = Boolean.parseBoolean(bundle.getString(LONG_ClICK));
		}
		
		UiObject uiObject = new UiObject(new UiSelector().className(className).descriptionContains(text));		
		waitForUiObjectExist(uiObject, bundle);
		if (ignore.toLowerCase().equals("no")) {
			if (isLongClick){
				uiObject.longClick();
			} else {
				uiObject.click();
			}
		}
		else {
			if (uiObject.exists()) {
				if (isLongClick){
					uiObject.longClick();
				} else {
					uiObject.click();
				}
			}
		}
	}
	
	public void swipeLeftOnScreen(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		int x1 = dut.getDisplayWidth()/5;
		int y = dut.getDisplayHeight()/2;
		int x2 = x1*4;
		dut.swipe(x2, y, x1, y, 20);
	}
	
	public void swipeRightOnScreen(UiDevice dut, Bundle bundle) throws UiObjectNotFoundException, UIAException {
		int x1 = dut.getDisplayWidth()/5;
		int y = dut.getDisplayHeight()/2;
		int x2 = x1*4;
		dut.swipe(x1, y, x2, y, 20);
	}
}
