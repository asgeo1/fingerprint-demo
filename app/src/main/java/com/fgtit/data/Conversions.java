package com.fgtit.data;

public class Conversions {
	
	private static Conversions mCom=null;
	
	public static Conversions getInstance(){
		if(mCom==null){
			mCom=new Conversions();
		}
		return mCom;
	}
	
	public native int StdToIso(int itype,byte[] input,byte[] output);
	public native int IsoToStd(int itype,byte[] input,byte[] output);
	public native int GetDataType(byte[] input);
	public native int StdChangeCoord(byte[] input,int size,byte[] output,int dk);
	
	static {
		System.loadLibrary("conversions");
	}
}
