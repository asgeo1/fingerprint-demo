package com.fgtit.printer;


import java.io.UnsupportedEncodingException;

public class PrinterCmd
{
    public static class Constant
    {

        private static final String NOTSETKEYHINT = "\u6B22\u8FCE\u4F7F\u7528\u3002\n";
        public static final int LAN_ADDR = 0x193000;
        public static final int LAN_INFO_ADDR = 0x193000;
        public static final int LAN_INFO_LEN = 13;
        public static final int PRN_INFO_ADDR = 0x19300d;
        public static final int PRN_INFO_LEN = 40;
        public static final int OEM_NAME_LEN = 40;
        public static final int OEM_INFO_ADDR = 0x193035;
        public static final int OEM_INFO_LEN = 162;
        public static final int USER_INFO_ADDR = 0x1930d7;
        public static final int USER_INFO_LEN = 50;
        public static final int BT_INFO_ADDR = 0x193109;
        public static final int BT_INFO_LEN = 30;
        public static final int IRD_INFO_ADDR = 0x193127;
        public static final int IRD_INFO_LEN = 30;
        public static final int FAC_INFO_ADDR = 0x193145;
        public static final int FAC_INFO_LEN = 68;
        public static final int USER_INFO2_ADDR = 0x193189;
        public static final int USER_INFO2_LEN = 20;
        public static final int PARA_LEN = 413;
        public static final int BT_MAX_NAME_LEN = 12;
        public static final int BT_MAX_PWD_LEN = 15;
        public static final int FAC_MAX_NAME_LEN = 32;
        public static final int FAC_MAX_SN_LEN = 29;
        public static final int BARCODE_TYPE_UPC_A = 65;
        public static final int BARCODE_TYPE_UPC_E = 66;
        public static final int BARCODE_TYPE_EAN13 = 67;
        public static final int BARCODE_TYPE_EAN8 = 68;
        public static final int BARCODE_TYPE_CODE39 = 69;
        public static final int BARCODE_TYPE_ITF = 70;
        public static final int BARCODE_TYPE_CODEBAR = 71;
        public static final int BARCODE_TYPE_CODE93 = 72;
        public static final int BARCODE_TYPE_CODE128 = 73;
        public static final int BARCODE_FONTPOSITION_NO = 0;
        public static final int BARCODE_FONTPOSITION_ABOVE = 1;
        public static final int BARCODE_FONTPOSITION_BELOW = 2;
        public static final int BARCODE_FONTPOSITION_ABOVEANDBELOW = 3;
        public static final int BARCODE_FONTTYPE_STANDARD = 0;
        public static final int BARCODE_FONTTYPE_SMALL = 1;
        public static final int ALIGN_LEFT = 0;
        public static final int ALIGN_CENTER = 1;
        public static final int ALIGN_RIGHT = 2;
        public static final int FONTSTYLE_NORMAL = 0;
        public static final int FONTSTYLE_BOLD = 8;
        public static final int FONTSTYLE_UNDERLINE1 = 128;
        public static final int FONTSTYLE_UNDERLINE2 = 256;
        public static final int FONTSTYLE_UPSIDEDOWN = 512;
        public static final int FONTSTYLE_BLACKWHITEREVERSE = 1024;
        public static final int FONTSTYLE_TURNRIGHT90 = 4096;
        public static final int CODEPAGE_CHINESE = 255;
        public static final int CODEPAGE_BIG5 = 254;
        public static final int CODEPAGE_UTF_8 = 253;
        public static final int CODEPAGE_SHIFT_JIS = 252;
        public static final int CODEPAGE_EUC_KR = 251;
        public static final int CODEPAGE_CP437_Standard_Europe = 0;
        public static final int CODEPAGE_Katakana = 1;
        public static final int CODEPAGE_CP850_Multilingual = 2;
        public static final int CODEPAGE_CP860_Portuguese = 3;
        public static final int CODEPAGE_CP863_Canadian_French = 4;
        public static final int CODEPAGE_CP865_Nordic = 5;
        public static final int CODEPAGE_WCP1251_Cyrillic = 6;
        public static final int CODEPAGE_CP866_Cyrilliec = 7;
        public static final int CODEPAGE_MIK_Cyrillic_Bulgarian = 8;
        public static final int CODEPAGE_CP755_East_Europe_Latvian_2 = 9;
        public static final int CODEPAGE_Iran = 10;
        public static final int CODEPAGE_CP862_Hebrew = 15;
        public static final int CODEPAGE_WCP1252_Latin_I = 16;
        public static final int CODEPAGE_WCP1253_Greek = 17;
        public static final int CODEPAGE_CP852_Latina_2 = 18;
        public static final int CODEPAGE_CP858_Multilingual_Latin = 19;
        public static final int CODEPAGE_Iran_II = 20;
        public static final int CODEPAGE_Latvian = 21;
        public static final int CODEPAGE_CP864_Arabic = 22;
        public static final int CODEPAGE_ISO_8859_1_West_Europe = 23;
        public static final int CODEPAGE_CP737_Greek = 24;
        public static final int CODEPAGE_WCP1257_Baltic = 25;
        public static final int CODEPAGE_Thai = 26;
        public static final int CODEPAGE_CP720_Arabic = 27;
        public static final int CODEPAGE_CP855 = 28;
        public static final int CODEPAGE_CP857_Turkish = 29;
        public static final int CODEPAGE_WCP1250_Central_Eurpoe = 30;
        public static final int CODEPAGE_CP775 = 31;
        public static final int CODEPAGE_WCP1254_Turkish = 32;
        public static final int CODEPAGE_WCP1255_Hebrew = 33;
        public static final int CODEPAGE_WCP1256_Arabic = 34;
        public static final int CODEPAGE_WCP1258_Vietnam = 35;
        public static final int CODEPAGE_ISO_8859_2_Latin_2 = 36;
        public static final int CODEPAGE_ISO_8859_3_Latin_3 = 37;
        public static final int CODEPAGE_ISO_8859_4_Baltic = 38;
        public static final int CODEPAGE_ISO_8859_5_Cyrillic = 39;
        public static final int CODEPAGE_ISO_8859_6_Arabic = 40;
        public static final int CODEPAGE_ISO_8859_7_Greek = 41;
        public static final int CODEPAGE_ISO_8859_8_Hebrew = 42;
        public static final int CODEPAGE_ISO_8859_9_Turkish = 43;
        public static final int CODEPAGE_ISO_8859_15_Latin_3 = 44;
        public static final int CODEPAGE_Thai2 = 45;
        public static final int CODEPAGE_CP856 = 46;
        public static final int CODEPAGE_Cp874 = 47;
        public static final String strcodepages[] = {
            "CHINESE", "BIG5", "UTF-8", "SHIFT-JIS", "EUC-KR", "CP437 [U.S.A., Standard Europe]", "Katakana", "CP850 [Multilingual]", "CP860 [Portuguese]", "CP863 [Canadian-French]", 
            "CP865 [Nordic]", "WCP1251 [Cyrillic]", "CP866 Cyrilliec #2", "MIK[Cyrillic /Bulgarian]", "CP755 [East Europe Latvian 2]", "Iran", "CP862 [Hebrew]", "WCP1252 Latin I", "WCP1253 [Greek]", "CP852 [Latina 2]", 
            "CP858 Multilingual Latin)", "Iran II", "Latvian", "CP864 [Arabic]", "ISO-8859-1 [West Europe]", "CP737 [Greek]", "WCP1257 [Baltic]", "Thai", "CP720[Arabic]", "CP855", 
            "CP857[Turkish]", "WCP1250[Central Eurpoe]", "CP775", "WCP1254[Turkish]", "WCP1255[Hebrew]", "WCP1256[Arabic]", "WCP1258[Vietnam]", "ISO-8859-2[Latin 2]", "ISO-8859-3[Latin 3]", "ISO-8859-4[Baltic]", 
            "ISO-8859-5[Cyrillic]", "ISO-8859-6[Arabic]", "ISO-8859-7[Greek]", "ISO-8859-8[Hebrew]", "ISO-8859-9[Turkish]", "ISO-8859-15 [Latin 3]", "Thai2", "CP856", "Cp874"
        };
        public static final int ncodepages[] = {
            255, 254, 253, 252, 251, 0, 1, 2, 3, 4, 
            5, 6, 7, 8, 9, 10, 15, 16, 17, 18, 
            19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 
            29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 
            39, 40, 41, 42, 43, 44, 45, 46, 47
        };
        public static final int nbaudrate[] = {
            4800, 9600, 19200, 38400, 57600, 0x1c200
        };
        public static final String strbaudrate[] = {
            "4800", "9600", "19200", "38400", "57600", "115200"
        };
        public static final int ndarkness[] = {
            0, 1, 2
        };
        public static final String strdarkness[] = {
            "Level 0", "Level 1", "Level 2"
        };
        public static final int ndefaultfont[] = {
            0, 1, 2, 3, 4
        };
        public static final String strdefaultfont[] = {
            "12x24", "9x24", "9x17", "8x16", "16x18"
        };
        public static final int nlinefeed[] = {
            0, 1
        };
        public static final String strlinefeed[] = {
            "LF", "CRLF"
        };

        public static byte[] getNotSetKeyHint()
        {
            try
            {
                return "\u6B22\u8FCE\u4F7F\u7528\u3002\n".getBytes("GBK");
            }
            catch(UnsupportedEncodingException e)
            {
                return new byte[0];
            }
        }

        public static String getCodePageStr(int nCodePage)
        {
            for(int i = 0; i < ncodepages.length; i++)
                if(ncodepages[i] == nCodePage)
                    return strcodepages[i];

            return "";
        }

        public static int getCodePageInt(String strCodePage)
        {
            for(int i = 0; i < strcodepages.length; i++)
                if(strcodepages[i].equals(strCodePage))
                    return ncodepages[i];

            return -1;
        }

        public static String getBaudrateStr(int nBaudrate)
        {
            for(int i = 0; i < nbaudrate.length; i++)
                if(nbaudrate[i] == nBaudrate)
                    return strbaudrate[i];

            return "";
        }

        public static int getBaudrateInt(String strBaudrate)
        {
            for(int i = 0; i < strbaudrate.length; i++)
                if(strbaudrate[i].equals(strBaudrate))
                    return nbaudrate[i];

            return -1;
        }

        public static String getDarknessStr(int nDarkness)
        {
            for(int i = 0; i < ndarkness.length; i++)
                if(ndarkness[i] == nDarkness)
                    return strdarkness[i];

            return "";
        }

        public static int getDarknessInt(String strDarkness)
        {
            for(int i = 0; i < strdarkness.length; i++)
                if(strdarkness[i].equals(strDarkness))
                    return ndarkness[i];

            return -1;
        }

        public static String getDefaultFontStr(int nDefaultFont)
        {
            for(int i = 0; i < ndefaultfont.length; i++)
                if(ndefaultfont[i] == nDefaultFont)
                    return strdefaultfont[i];

            return "";
        }

        public static int getDefaultFontInt(String strDefaultFont)
        {
            for(int i = 0; i < strdefaultfont.length; i++)
                if(strdefaultfont[i].equals(strDefaultFont))
                    return ndefaultfont[i];

            return -1;
        }

        public static String getLineFeedStr(int nLineFeed)
        {
            for(int i = 0; i < nlinefeed.length; i++)
                if(nlinefeed[i] == nLineFeed)
                    return strlinefeed[i];

            return "";
        }

        public static int getLineFeedInt(String strLineFeed)
        {
            for(int i = 0; i < strlinefeed.length; i++)
                if(strlinefeed[i].equals(strLineFeed))
                    return nlinefeed[i];

            return -1;
        }


        public Constant()
        {
        }
    }

    public static class ESCCmd
    {

        public static byte DES_SETKEY[] = {
            31, 31, 0, 8, 0, 1, 1, 1, 1, 1, 
            1, 1, 1
        };
        public static byte DES_ENCRYPT[] = {
            31, 31, 1
        };
        public static byte ERROR[] = new byte[1];
        public static byte ESC_ALT[] = {
            27, 64
        };
        public static byte ESC_L[] = {
            27, 76
        };
        public static byte ESC_CAN[] = {
            24
        };
        public static byte FF[] = {
            12
        };
        public static byte ESC_FF[] = {
            27, 12
        };
        public static byte ESC_S[] = {
            27, 83
        };
        public static byte GS_P_x_y[] = {
            29, 80, 0, 0
        };
        public static byte ESC_R_n[] = {
            27, 82, 0
        };
        public static byte ESC_t_n[] = {
            27, 116, 0
        };
        public static byte LF[] = {
            10
        };
        public static byte CR[] = {
            13
        };
        public static byte ESC_3_n[] = {
            27, 51, 0
        };
        public static byte ESC_SP_n[] = {
            27, 32, 0
        };
        public static byte DLE_DC4_n_m_t[] = {
            16, 20, 1, 0, 1
        };
        public static byte GS_V_m[] = {
            29, 86, 0
        };
        public static byte GS_V_m_n[] = {
            29, 86, 66, 0
        };
        public static byte GS_W_nL_nH[] = {
            29, 87, 118, 2
        };
        public static byte ESC_dollors_nL_nH[] = {
            27, 36, 0, 0
        };
        public static byte ESC_a_n[] = {
            27, 97, 0
        };
        public static byte GS_exclamationmark_n[] = {
            29, 33, 0
        };
        public static byte ESC_M_n[] = {
            27, 77, 0
        };
        public static byte GS_E_n[] = {
            27, 69, 0
        };
        public static byte ESC_line_n[] = {
            27, 45, 0
        };
        public static byte ESC_lbracket_n[] = {
            27, 123, 0
        };
        public static byte GS_B_n[] = {
            29, 66, 0
        };
        public static byte ESC_V_n[] = {
            27, 86, 0
        };
        public static byte GS_backslash_m[] = {
            29, 47, 0
        };
        public static byte FS_p_n_m[] = {
            28, 112, 1, 0
        };
        public static byte GS_H_n[] = {
            29, 72, 0
        };
        public static byte GS_f_n[] = {
            29, 102, 0
        };
        public static byte GS_h_n[] = {
            29, 104, -94
        };
        public static byte GS_w_n[] = {
            29, 119, 3
        };
        public static byte GS_k_m_n_[] = {
            29, 107, 65, 12
        };
        public static byte GS_k_m_v_r_nL_nH[] = {
            29, 107, 97, 0, 2, 0, 0
        };
        public static byte ESC_W_xL_xH_yL_yH_dxL_dxH_dyL_dyH[] = {
            27, 87, 0, 0, 0, 0, 72, 2, -80, 4
        };
        public static byte ESC_T_n[] = {
            27, 84, 0
        };
        public static byte GS_dollors_nL_nH[] = {
            29, 36, 0, 0
        };
        public static byte GS_backslash_nL_nH[] = {
            29, 92, 0, 0
        };
        public static byte FS_line_n[] = {
            28, 45, 0
        };


        public ESCCmd()
        {
        }
    }

    public static class PCmd
    {

        public static byte test[] = {
            3, -1, 32, 0, 0, 0, 0, 0, 8, 0, 
            -44, 24, 68, 69, 86, 73, 67, 69, 63, 63
        };
        public static byte startUpdate[] = {
            3, -1, 47, 0, 0, 0, 0, 0, 0, 0, 
            -45, 0
        };
        public static byte imaUpdate[] = {
            3, -1, 46, 0, 0, 0, 0, 0, 0, 1, 
            -45, 0
        };
        public static byte endUpdate[] = {
            3, -1, 47, 0, -1, -1, -1, -1, 0, 0, 
            -45, 0
        };
        public static byte fontUpdate[] = {
            3, -1, 45, 0, 0, 0, 0, 0, -1, 0, 
            46, 0
        };
        public static byte setBaudrate[] = {
            3, -1, 43, 0, -128, 37, 0, 0, 0, 0, 
            114, 0
        };
        public static byte setPrintParam[] = {
            3, -1, 96, 0, 0, 0, 0, 0, 16, 0, 
            -116, 24, -128, 37, 0, 0, -1, 2, 0, 0, 
            64, 0, -1, 0, -1, 0, -1, 0
        };
        public static byte readFlash[] = {
            3, -1, 44, 0, 0, 0, 0, 0, 0, 0, 
            -48, 0
        };
        public static byte setBluetooth[] = {
            3, -1, 97, 0, 0, 0, 0, 0, 0, 0, 
            0, 0
        };
        public static byte setSystemInfo[] = {
            3, -1, 100, 0, 0, 0, 0, 0, 0, 0, 
            0, 0
        };


        public PCmd() {
        }
    }

    public PrinterCmd() {
    }
}

