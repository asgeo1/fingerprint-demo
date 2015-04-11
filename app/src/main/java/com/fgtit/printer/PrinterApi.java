package com.fgtit.printer;


import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.fgtit.printer.DataUtils;

public class PrinterApi {
	
	private static int p0[] = {
        0, 128
    };
    private static int p1[] = {
        0, 64
    };
    private static int p2[] = {
        0, 32
    };
    private static int p3[] = {
        0, 16
    };
    private static int p4[] = {
        0, 8
    };
    private static int p5[] = {
        0, 4
    };
    private static int p6[] = {
        0, 2
    };
    
    private static int Floyd16x16[][] = {
        {
            0, 128, 32, 160, 8, 136, 40, 168, 2, 130, 
            34, 162, 10, 138, 42, 170
        }, {
            192, 64, 224, 96, 200, 72, 232, 104, 194, 66, 
            226, 98, 202, 74, 234, 106
        }, {
            48, 176, 16, 144, 56, 184, 24, 152, 50, 178, 
            18, 146, 58, 186, 26, 154
        }, {
            240, 112, 208, 80, 248, 120, 216, 88, 242, 114, 
            210, 82, 250, 122, 218, 90
        }, {
            12, 140, 44, 172, 4, 132, 36, 164, 14, 142, 
            46, 174, 6, 134, 38, 166
        }, {
            204, 76, 236, 108, 196, 68, 228, 100, 206, 78, 
            238, 110, 198, 70, 230, 102
        }, {
            60, 188, 28, 156, 52, 180, 20, 148, 62, 190, 
            30, 158, 54, 182, 22, 150
        }, {
            252, 124, 220, 92, 244, 116, 212, 84, 254, 126, 
            222, 94, 246, 118, 214, 86
        }, {
            3, 131, 35, 163, 11, 139, 43, 171, 1, 129, 
            33, 161, 9, 137, 41, 169
        }, {
            195, 67, 227, 99, 203, 75, 235, 107, 193, 65, 
            225, 97, 201, 73, 233, 105
        }, {
            51, 179, 19, 147, 59, 187, 27, 155, 49, 177, 
            17, 145, 57, 185, 25, 153
        }, {
            243, 115, 211, 83, 251, 123, 219, 91, 241, 113, 
            209, 81, 249, 121, 217, 89
        }, {
            15, 143, 47, 175, 7, 135, 39, 167, 13, 141, 
            45, 173, 5, 133, 37, 165
        }, {
            207, 79, 239, 111, 199, 71, 231, 103, 205, 77, 
            237, 109, 197, 69, 229, 101
        }, {
            63, 191, 31, 159, 55, 183, 23, 151, 61, 189, 
            29, 157, 53, 181, 21, 149
        }, {
            254, 127, 223, 95, 247, 119, 215, 87, 253, 125, 
            221, 93, 245, 117, 213, 85
        }
    };
    private static int Floyd8x8[][] = {
        {
            0, 32, 8, 40, 2, 34, 10, 42
        }, {
            48, 16, 56, 24, 50, 18, 58, 26
        }, {
            12, 44, 4, 36, 14, 46, 6, 38
        }, {
            60, 28, 52, 20, 62, 30, 54, 22
        }, {
            3, 35, 11, 43, 1, 33, 9, 41
        }, {
            51, 19, 59, 27, 49, 17, 57, 25
        }, {
            15, 47, 7, 39, 13, 45, 5, 37
        }, {
            63, 31, 55, 23, 61, 29, 53, 21
        }
    };
    private static int Floyd4x4[][] = {
        {
            0, 8, 2, 10
        }, {
            12, 4, 14, 6
        }, {
            3, 11, 1, 9
        }, {
            15, 7, 13, 5
        }
    };
    private static int textmodeThreshold = 192;
    public static final int ALGORITHM_DITHER_16x16 = 16;
    public static final int ALGORITHM_DITHER_8x8 = 8;
    public static final int ALGORITHM_TEXTMODE = 2;
    public static final int ALGORITHM_GRAYTEXTMODE = 1;

    
    
	public static byte[] TestPage(){
		byte[] b=new byte[2];
		b[0]=(byte)0x12;
		b[1]=(byte)0x54;
		return b;
	}
	
	public static byte[] TextOut(String pszString){
		byte pbString[] = null;
		try {
			pbString=pszString.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pbString;
	}
	
	public static byte[] FeedLine(){
        byte data[] = DataUtils.byteArraysToBytes(new byte[][] {
            PrinterCmd.ESCCmd.LF, PrinterCmd.ESCCmd.CR
        });
        return data;
    }

    public static byte[] Align(int align){
        if(align < 0 || align > 2){
            return null;
        }else{
            byte data[] = PrinterCmd.ESCCmd.ESC_a_n;
            data[2] = (byte)align;
            return data;
        }
    }
    
    public static byte[] SetKey(byte key[]){
        byte data[] = PrinterCmd.ESCCmd.DES_SETKEY;
        for(int i = 0; i < key.length; i++)
            data[i + 5] = key[i];
        return data;
    }

    public static byte[] Reset(){
        byte data[] = PrinterCmd.ESCCmd.ESC_ALT;
        return data;
    }

    public static byte[] SetMotionUnit(int nHorizontalMU, int nVerticalMU)
    {
        if(nHorizontalMU < 0 || nHorizontalMU > 255 || nVerticalMU < 0 || nVerticalMU > 255){
            return null;
        } else {
            byte data[] = PrinterCmd.ESCCmd.GS_P_x_y;
            data[2] = (byte)nHorizontalMU;
            data[3] = (byte)nVerticalMU;
            return data;
        }
    }

    public static byte[] SetCharSetAndCodePage(int nCharSet, int nCodePage){
        if((nCharSet < 0) | (nCharSet > 15) | (nCodePage < 0) | (nCodePage > 19) | (nCodePage > 10) & (nCodePage < 16)) {
            return null;
        } else {        	
        	PrinterCmd.ESCCmd.ESC_R_n[2] = (byte)nCharSet;
        	PrinterCmd.ESCCmd.ESC_t_n[2] = (byte)nCodePage;
        	
        	byte data[] = DataUtils.byteArraysToBytes(new byte[][] {
        			PrinterCmd.ESCCmd.ESC_R_n, 
        			PrinterCmd.ESCCmd.ESC_t_n
        	});            
            return data;
        }
    }

    public static byte[] SetLineSpacing(int nDistance){
        if((nDistance < 0) | (nDistance > 255)){
            return null;
        } else {
            PrinterCmd.ESCCmd.ESC_3_n[2] = (byte)nDistance;
            byte data[] = PrinterCmd.ESCCmd.ESC_3_n;
            return data;
        }
    }

    public static byte[] RightSpacing(int nDistance){
        if((nDistance < 0) | (nDistance > 255)){
            return null;
        } else{
        	PrinterCmd.ESCCmd.ESC_SP_n[2] = (byte)nDistance;
            byte data[] = PrinterCmd.ESCCmd.ESC_SP_n;
            return data;
        }
    }

    public static byte[] SetAreaWidth(int nWidth) {
        if((nWidth < 0) | (nWidth > 65535)) {
            return null;
        } else  {
            byte nL = (byte)(nWidth % 256);
            byte nH = (byte)(nWidth / 256);
            PrinterCmd.ESCCmd.GS_W_nL_nH[2] = nL;
            PrinterCmd.ESCCmd.GS_W_nL_nH[3] = nH;
            byte data[] = PrinterCmd.ESCCmd.GS_W_nL_nH;
            return data;
        }
    }
	
	public static byte[] TextOutEx(String pszString, int nOrgx, int nWidthTimes, int nHeightTimes, int nFontType, int nFontStyle){
        if((nOrgx > 65535) | (nOrgx < 0) | (nWidthTimes > 7) | (nWidthTimes < 0) | (nHeightTimes > 7) | (nHeightTimes < 0) | (nFontType < 0) | (nFontType > 4))
            return null;
        PrinterCmd.ESCCmd.ESC_dollors_nL_nH[2] = (byte)(nOrgx % 256);
        PrinterCmd.ESCCmd.ESC_dollors_nL_nH[3] = (byte)(nOrgx / 256);
        byte intToWidth[] = {
            0, 16, 32, 48, 64, 80, 96, 112
        };
        byte intToHeight[] = {
            0, 1, 2, 3, 4, 5, 6, 7
        };
        PrinterCmd.ESCCmd.GS_exclamationmark_n[2] = (byte)(intToWidth[nWidthTimes] + intToHeight[nHeightTimes]);
        PrinterCmd.ESCCmd.ESC_M_n[2] = (byte)nFontType;
        PrinterCmd.ESCCmd.GS_E_n[2] = (byte)(nFontStyle >> 3 & 1);
        PrinterCmd.ESCCmd.ESC_line_n[2] = (byte)(nFontStyle >> 7 & 3);
        PrinterCmd.ESCCmd.FS_line_n[2] = (byte)(nFontStyle >> 7 & 3);
        PrinterCmd.ESCCmd.ESC_lbracket_n[2] = (byte)(nFontStyle >> 9 & 1);
        PrinterCmd.ESCCmd.GS_B_n[2] = (byte)(nFontStyle >> 10 & 1);
        PrinterCmd.ESCCmd.ESC_V_n[2] = (byte)(nFontStyle >> 12 & 1);
        byte pbString[] = null;
        try
        {
            pbString = pszString.getBytes("GBK");
        }
        catch(UnsupportedEncodingException e)
        {
            return null;
        }
        byte data[] = DataUtils.byteArraysToBytes(new byte[][] {
        		PrinterCmd.ESCCmd.ESC_dollors_nL_nH, 
        		PrinterCmd.ESCCmd.GS_exclamationmark_n, 
        		PrinterCmd.ESCCmd.ESC_M_n, 
        		PrinterCmd.ESCCmd.GS_E_n, 
        		PrinterCmd.ESCCmd.ESC_line_n, 
        		PrinterCmd.ESCCmd.FS_line_n, 
        		PrinterCmd.ESCCmd.ESC_lbracket_n, 
        		PrinterCmd.ESCCmd.GS_B_n, 
        		PrinterCmd.ESCCmd.ESC_V_n, pbString
        });
        return data;
    }
	
	 public static byte[] SetBarcode(String strCodedata, int nOrgx, int nType, int nWidthX, int nHeight, int nHriFontType, int nHriFontPosition){
	        if((nOrgx < 0) | (nOrgx > 65535) | (nType < 65) | (nType > 73) | (nWidthX < 2) | (nWidthX > 6) | (nHeight < 1) | (nHeight > 255))
	            return null;
	        byte bCodeData[] = null;
	        try
	        {
	            bCodeData = strCodedata.getBytes("GBK");
	        }
	        catch(UnsupportedEncodingException e)
	        {
	            return null;
	        }
	        PrinterCmd.ESCCmd.ESC_dollors_nL_nH[2] = (byte)(nOrgx % 256);
	        PrinterCmd.ESCCmd.ESC_dollors_nL_nH[3] = (byte)(nOrgx / 256);
	        PrinterCmd.ESCCmd.GS_w_n[2] = (byte)nWidthX;
	        PrinterCmd.ESCCmd.GS_h_n[2] = (byte)nHeight;
	        PrinterCmd.ESCCmd.GS_f_n[2] = (byte)(nHriFontType & 1);
	        PrinterCmd.ESCCmd.GS_H_n[2] = (byte)(nHriFontPosition & 3);
	        PrinterCmd.ESCCmd.GS_k_m_n_[2] = (byte)nType;
	        PrinterCmd.ESCCmd.GS_k_m_n_[3] = (byte)bCodeData.length;
	        byte data[] = DataUtils.byteArraysToBytes(new byte[][] {
	        		PrinterCmd.ESCCmd.ESC_dollors_nL_nH, 
	        		PrinterCmd.ESCCmd.GS_w_n, 
	        		PrinterCmd.ESCCmd.GS_h_n, 
	        		PrinterCmd.ESCCmd.GS_f_n, 
	        		PrinterCmd.ESCCmd.GS_H_n, 
	        		PrinterCmd.ESCCmd.GS_k_m_n_, bCodeData
	        });
	        return data;
	    }

	    public static byte[] SetQRcode(String strCodedata, int nWidthX, int nErrorCorrectionLevel){
	        if((nWidthX < 2) | (nWidthX > 6) | (nErrorCorrectionLevel < 1) | (nErrorCorrectionLevel > 4))
	            return null;
	        byte bCodeData[] = null;
	        try {
	            bCodeData = strCodedata.getBytes("GBK");
	        }catch(UnsupportedEncodingException e){
	            return null;
	        }
	        PrinterCmd.ESCCmd.GS_w_n[2] = (byte)nWidthX;
	        PrinterCmd.ESCCmd.GS_k_m_v_r_nL_nH[4] = (byte)nErrorCorrectionLevel;
	        PrinterCmd.ESCCmd.GS_k_m_v_r_nL_nH[5] = (byte)(bCodeData.length & 0xff);
	        PrinterCmd.ESCCmd.GS_k_m_v_r_nL_nH[6] = (byte)((bCodeData.length & 0xff00) >> 8);
	        byte data[] = DataUtils.byteArraysToBytes(new byte[][] {
	        		PrinterCmd.ESCCmd.GS_w_n, PrinterCmd.ESCCmd.GS_k_m_v_r_nL_nH, bCodeData
	        });
	        return data;
	    }
	
	public static Bitmap toBinaryImage(Bitmap mBitmap, int nWidth, int algorithm)
    {
        int width = ((nWidth + 7) / 8) * 8;
        int height = (mBitmap.getHeight() * width) / mBitmap.getWidth();
        Bitmap rszBitmap = resizeImage(mBitmap, width, height);
        int pixels[] = bitmapToBWPix_int(rszBitmap, algorithm);
        rszBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return rszBitmap;
    }
	
	public static byte[] PrintPicture(Bitmap mBitmap, int nWidth, int nMode)
    {
        int width = ((nWidth + 7) / 8) * 8;
        int height = (mBitmap.getHeight() * width) / mBitmap.getWidth();
        Bitmap grayBitmap = toGrayscale(mBitmap);
        Bitmap rszBitmap = resizeImage(grayBitmap, width, height);
        byte src[] = bitmapToBWPix(rszBitmap);
        byte data[] = pixToCmd(src, width, nMode);
        return data;
    }
	
	public static byte[] PrintPicture(Bitmap mBitmap, int nWidth, int nMode, int algorithm)
    {
        int width = ((nWidth + 7) / 8) * 8;
        int height = (mBitmap.getHeight() * width) / mBitmap.getWidth();
        Bitmap rszBitmap = resizeImage(mBitmap, width, height);
        byte src[] = bitmapToBWPix(rszBitmap, algorithm);
        byte data[] = pixToCmd(src, width, nMode);
        return data;
    }
		
	 public static Bitmap resizeImage(Bitmap bitmap, int w, int h)
	    {
	        Bitmap BitmapOrg = bitmap;
	        int width = BitmapOrg.getWidth();
	        int height = BitmapOrg.getHeight();
	        int newWidth = w;
	        int newHeight = h;
	        float scaleWidth = (float)newWidth / (float)width;
	        float scaleHeight = (float)newHeight / (float)height;
	        Matrix matrix = new Matrix();
	        matrix.postScale(scaleWidth, scaleHeight);
	        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
	        return resizedBitmap;
	    }
	
	 private static void format_K_dither16x16(int orgpixels[], int xsize, int ysize, byte despixels[])
	    {
	        int k = 0;
	        for(int y = 0; y < ysize; y++)
	        {
	            for(int x = 0; x < xsize; x++)
	            {
	                if((orgpixels[k] & 0xff) > Floyd16x16[x & 0xf][y & 0xf])
	                    despixels[k] = 0;
	                else
	                    despixels[k] = 1;
	                k++;
	            }

	        }

	    }
	 
	 private static void format_K_dither8x8(int orgpixels[], int xsize, int ysize, byte despixels[])
	    {
	        int k = 0;
	        for(int y = 0; y < ysize; y++)
	        {
	            for(int x = 0; x < xsize; x++)
	            {
	                if((orgpixels[k] & 0xff) >> 2 > Floyd8x8[x & 7][y & 7])
	                    despixels[k] = 0;
	                else
	                    despixels[k] = 1;
	                k++;
	            }

	        }

	    }
	 
	  private static void format_K_dither16x16_int(int orgpixels[], int xsize, int ysize, int despixels[])
	    {
	        int k = 0;
	        for(int y = 0; y < ysize; y++)
	        {
	            for(int x = 0; x < xsize; x++)
	            {
	                if((orgpixels[k] & 0xff) > Floyd16x16[x & 0xf][y & 0xf])
	                    despixels[k] = -1;
	                else
	                    despixels[k] = 0xff000000;
	                k++;
	            }

	        }

	    }
	  
	 private static void format_K_dither8x8_int(int orgpixels[], int xsize, int ysize, int despixels[])
	    {
	        int k = 0;
	        for(int y = 0; y < ysize; y++)
	        {
	            for(int x = 0; x < xsize; x++)
	            {
	                if((orgpixels[k] & 0xff) >> 2 > Floyd8x8[x & 7][y & 7])
	                    despixels[k] = -1;
	                else
	                    despixels[k] = 0xff000000;
	                k++;
	            }

	        }

	    }
	 
	  private static void format_K_graytextmode(int orgpixels[], int xsize, int ysize, byte despixels[])
	    {
	        int k = 0;
	        for(int y = 0; y < ysize; y++)
	        {
	            for(int x = 0; x < xsize; x++)
	            {
	                if((orgpixels[k] & 0xff) > textmodeThreshold)
	                    despixels[k] = 0;
	                else
	                    despixels[k] = 1;
	                k++;
	            }

	        }

	    }
	  
	   private static void format_ARGB_textmode(int orgpixels[], int xsize, int ysize, byte despixels[])
	    {
	        int k = 0;
	        for(int y = 0; y < ysize; y++)
	        {
	            for(int x = 0; x < xsize; x++)
	            {
	                if((orgpixels[k] & 0xff) > textmodeThreshold || (orgpixels[k] >> 8 & 0xff) > textmodeThreshold || (orgpixels[k] >> 16 & 0xff) > textmodeThreshold)
	                    despixels[k] = 0;
	                else
	                    despixels[k] = 1;
	                k++;
	            }

	        }

	    }
	 
	   public static int[] bitmapToBWPix_int(Bitmap mBitmap, int algorithm)
	    {
	        int pixels[] = new int[0];
	        switch(algorithm)
	        {
	        case 8: // '\b'
	        {
	            Bitmap grayBitmap = toGrayscale(mBitmap);
	            pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, grayBitmap.getWidth(), grayBitmap.getHeight());
	            format_K_dither8x8_int(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), pixels);
	            break;
	        }

	        case 16: // '\020'
	        default:
	        {
	            Bitmap grayBitmap = toGrayscale(mBitmap);
	            pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, grayBitmap.getWidth(), grayBitmap.getHeight());
	            format_K_dither16x16_int(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), pixels);
	            break;
	        }

	        case 2: // '\002'
	            break;
	        }
	        return pixels;
	    }
	   
	 public static byte[] bitmapToBWPix(Bitmap mBitmap)
	    {
	        int pixels[] = new int[mBitmap.getWidth() * mBitmap.getHeight()];
	        byte data[] = new byte[mBitmap.getWidth() * mBitmap.getHeight()];
	        Bitmap grayBitmap = toGrayscale(mBitmap);
	        grayBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
	        format_K_dither16x16(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), data);
	        return data;
	    }
	 
	 private static byte[] bitmapToBWPix(Bitmap mBitmap, int algorithm)
	    {
	        int pixels[] = new int[0];
	        byte data[] = new byte[0];
	        switch(algorithm)
	        {
	        case 8: // '\b'
	        {
	            Bitmap grayBitmap = toGrayscale(mBitmap);
	            pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            data = new byte[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, grayBitmap.getWidth(), grayBitmap.getHeight());
	            format_K_dither8x8(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), data);
	            break;
	        }

	        case 2: // '\002'
	        {
	            pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
	            data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];
	            mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());
	            format_ARGB_textmode(pixels, mBitmap.getWidth(), mBitmap.getHeight(), data);
	            break;
	        }

	        case 1: // '\001'
	        {
	            Bitmap grayBitmap = toGrayscale(mBitmap);
	            pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            data = new byte[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, grayBitmap.getWidth(), grayBitmap.getHeight());
	            format_K_graytextmode(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), data);
	            break;
	        }

	        case 16: // '\020'
	        default:
	        {
	            Bitmap grayBitmap = toGrayscale(mBitmap);
	            pixels = new int[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            data = new byte[grayBitmap.getWidth() * grayBitmap.getHeight()];
	            grayBitmap.getPixels(pixels, 0, grayBitmap.getWidth(), 0, 0, grayBitmap.getWidth(), grayBitmap.getHeight());
	            format_K_dither16x16(pixels, grayBitmap.getWidth(), grayBitmap.getHeight(), data);
	            break;
	        }
	        }
	        return data;
	    }
	 
	 public static Bitmap toGrayscale(Bitmap bmpOriginal)
	    {
	        int height = bmpOriginal.getHeight();
	        int width = bmpOriginal.getWidth();
	        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.RGB_565);
	        Canvas c = new Canvas(bmpGrayscale);
	        Paint paint = new Paint();
	        ColorMatrix cm = new ColorMatrix();
	        cm.setSaturation(0.0F);
	        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
	        paint.setColorFilter(f);
	        c.drawBitmap(bmpOriginal, 0.0F, 0.0F, paint);
	        return bmpGrayscale;
	    }
	 
	 private static byte[] pixToCmd(byte src[], int nWidth, int nMode)
	    {
	        int nHeight = src.length / nWidth;
	        byte data[] = new byte[8 + src.length / 8];
	        data[0] = 29;
	        data[1] = 118;
	        data[2] = 48;
	        data[3] = (byte)(nMode & 1);
	        data[4] = (byte)((nWidth / 8) % 256);
	        data[5] = (byte)(nWidth / 8 / 256);
	        data[6] = (byte)(nHeight % 256);
	        data[7] = (byte)(nHeight / 256);
	        int k = 0;
	        for(int i = 8; i < data.length; i++)
	        {
	            data[i] = (byte)(p0[src[k]] + p1[src[k + 1]] + p2[src[k + 2]] + p3[src[k + 3]] + p4[src[k + 4]] + p5[src[k + 5]] + p6[src[k + 6]] + src[k + 7]);
	            k += 8;
	        }

	        return data;
	    }
}
