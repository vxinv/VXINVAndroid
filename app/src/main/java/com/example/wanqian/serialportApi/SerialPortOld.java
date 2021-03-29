package com.example.wanqian.serialportApi;

import android.util.Log;

import java.io.File;
import java.io.IOException;

public class SerialPortOld {

	private static final String TAG = "SerialPort";

	/*
	 * Do not remove or rename the field mFd: it is used by native method close();
	 */
	private int iFLid = 0;
	
	//private FileDescriptor mFd;
	
	public SerialPortOld(String sComNo, int baudrate, int flags, int idatabits,
						 int istopbits, char cparity) throws SecurityException, IOException {

		File device = new File(sComNo);
		/* Check access permission */
		if (!device.canRead() || !device.canWrite()) {
			try {
				/* Missing read/write permission, trying to chmod the file */
				Process su;
				su = Runtime.getRuntime().exec("/system/bin/su");
				String cmd = "chmod 666 " + device.getAbsolutePath() + "\n"
						+ "exit\n";
				su.getOutputStream().write(cmd.getBytes());
				if ((su.waitFor() != 0) || !device.canRead()
						|| !device.canWrite()) {
					throw new SecurityException();
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new SecurityException();
			}
		}

		iFLid = open(device.getAbsolutePath(), baudrate, flags, idatabits, istopbits, cparity);
		if (iFLid < 0) {
			Log.e(TAG, "native open returns null");
			throw new IOException();
		}

	}

	public boolean WriteDataByte(byte[] leftollCode)
	{
		serialportWrite(iFLid, leftollCode, 10);
		return false;
	}
	
	
	public int ES_SerialPortWrite(byte[] inputData)
	{
		return serialportWrite(iFLid, inputData, inputData.length);
	}
	
	public int ES_SerialPortRead(byte[] outputData, int timeOut)
	{
		return serialportRead(iFLid, outputData, timeOut);
	}

	public void ES_CloseSerialPort()
	{
		 close(iFLid);
	}

	// JNI
	private native int open(String path, int baudrate, int flags, int idatabits,
                            int istopbits, char cparity);
	public native int serialportWrite(int iFLid, byte[] inputData, int len);
	public native int serialportRead(int iFLid, byte[] outputData, int timeOut);
	public native void close(int iFLid);
	static {
		System.loadLibrary("serialport");
	}

}
