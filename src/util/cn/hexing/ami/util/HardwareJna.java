package cn.hexing.ami.util;

import com.sun.jna.Library;
import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;

public interface HardwareJna extends Library{
	
	public static final int S4_USER_PIN 	=	0x000000a1; 
	public static final int S4_DEV_PIN 		=	0x000000a2; 
	public static final int S4_AUTHEN_PIN 	=	0x000000a3; 
	public static final int S4_GET_SERIAL_NUMBER =  0X00000026;
	
	public static class SENSE4_CONTEXT extends Structure {
		public	int			dwIndex;                                        /** device index; index begins at zero*/
		public	int			dwVersion;                                      /** device version*/ 
		public	int			hLock;                                          /** device handle*/
		public	byte[]		reserve		= new byte[12];                                    /** reserved*/
		public	byte[]		bAtr		= new byte[56];                              /** ATR*/
		public	byte[]		bID			= new byte[8];                                /** device ID*/
		public	int			dwAtrLen;                                       /** ATR length*/
	}
	
	
	
	public static final int S4_SUCCESS =  0x0;
	public int S4Enum(SENSE4_CONTEXT ctx, IntByReference pSize);
	
	
	public int S4Open(SENSE4_CONTEXT ctx);
	
	
	public int S4ChangeDir(SENSE4_CONTEXT ctx, String dir);
	
	
	public int S4VerifyPin(SENSE4_CONTEXT ctx, String passwd, int passwdLen, int type);
	
	
	public int S4Execute(SENSE4_CONTEXT ctx, String fileID, byte[] inputBuf, int inputSize, byte[] outputBuf, int outputSize, IntByReference pSize);
	
	public int S4Control(SENSE4_CONTEXT ctx, int ctlCode, byte[]inBuff,int inBuffLen,byte[] outBuff, int outBuffLen,IntByReference pBytesReturn);
	
	
	public int S4Close(SENSE4_CONTEXT ctx);
}
