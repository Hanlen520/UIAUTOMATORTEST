package com.udld.android.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.logging.Handler;

import android.R.integer;

import com.android.uiautomator.testrunner.UiAutomatorTestCase;

public class LocatCapture extends UiAutomatorTestCase {
	protected static String captured = null;
	private String keyString = null;
	protected boolean result_get = false;
	private static final String[] LOGCA_STRING = {"locat -c", "/C", "logcat"};
	public static boolean stop_monitor = true;
	Thread mThread = null;
	
	public Runnable mLogcatUpdateRunnable = new Runnable() {
		/*
		 * add a new thread to write the messages.
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				Process proc = Runtime.getRuntime().exec("logcat -c");
				proc = Runtime.getRuntime().exec("logcat");
				InputStream input = proc.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(input);
				BufferedReader stdInput = new BufferedReader(inputStreamReader);
				String line = "";
				while (stop_monitor) {
					if (!stdInput.ready()) {
						stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
						continue;
					}
					line = stdInput.readLine();
					if (line == null) {
						continue;
					}
					//java.lang.System.out.println(line);
					if (line.contains(keyString)) {
						java.lang.System.out.println("Captured:------------------------+++++++++++++++++++++++++--------------------------");
						java.lang.System.out.println(line);
						result_get = true;
					}
				}
				stdInput.close();
				proc.destroy();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
	public void startTrace(String command) {
		keyString = command;
		if (keyString != null) {
			mThread = new Thread(mLogcatUpdateRunnable);
			mThread.start();
		}
	}
	
	public boolean stopTrace(long timeout) {
		try {
			for (int i=0; i<10; i++) {
				if (result_get)
					break;
				Thread.sleep(timeout/10);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stop_monitor = false;
		mThread = null;
		return result_get;
	}
	
}
