#include <string.h>
#include <jni.h>

//模板数据
typedef struct _STDTPHEADER {
	int count;
	int val;
	int imgx;
	int imgy;
	int resx;
	int resy;
	int center;
}STDTPHEADER,*LPSTDTPHEADER;

typedef struct _STDMINUTIAE{
	int x;
	int y;
	int ang;
	int val;
	int type;
}STDMINUTIAE,*LPSTDMINUTIAE;

int CheckTemplate(unsigned char *pFeature)
{
	int i;
	unsigned int TmpInfo;
	unsigned char *pTmp;
	int x;
	int y;
	int ang;

	if( pFeature[0] != 3)
	{
		return -1;
	}

	if((pFeature[3]) > 50 || pFeature[3] <= 0)
	{
		return -1;
	}

	pTmp = pFeature+56;
	for(i=0; i<pFeature[3]; i++)
	{
		TmpInfo = (pTmp[i*4]<<24) + (pTmp[i*4+1]<<16) + (pTmp[i*4+2]<<8) +pTmp[i*4+3];
		x = TmpInfo >> 23 ;
		y = ( TmpInfo >> 14 ) & 0x01ff;
		ang = ( TmpInfo >> 5) & 0x01ff;

		if(x >= 256)
		{
			return -1;
		}
		if(y >= 288)
		{
			return -1;
		}
		if(ang >= 360)
		{
			return -1;
		}
	}


	pTmp = pFeature+40;
	TmpInfo = (*pTmp++)*2;
	if(TmpInfo >= 256)
	{
		return -1;
	}
	TmpInfo = (*pTmp++)*2;
	if(TmpInfo >= 288)
	{
		return -1;
	}
	TmpInfo = (*pTmp++)*2;
	if(TmpInfo >= 256)
	{
		return -1;
	}
	TmpInfo = (*pTmp++)*2;
	if(TmpInfo >= 288)
	{
		return -1;
	}

	return 0;
}

void GetStdTpInfo(unsigned char *pData,LPSTDTPHEADER StdTpHeader,LPSTDMINUTIAE StdMinutiaes,LPSTDMINUTIAE StdCenters)
{
	unsigned int info;
	int x;
	int y;
	int ang;
	int b;
	int s;
	int i;

	StdTpHeader->count=pData[3];
	StdTpHeader->val=pData[2];
	StdTpHeader->imgx=256;
	StdTpHeader->imgy=288;
	StdTpHeader->resx=500;
	StdTpHeader->resy=500;
	StdTpHeader->center=0;

	for(i=0; i<pData[3]; i++)
	{
		info = (pData[56+i*4]<<24) + (pData[56+i*4+1]<<16) + (pData[56+i*4+2]<<8) +pData[56+i*4+3];
		x = info >> 23 ;
		y = ( info >> 14 ) & 0x01ff;
		ang = ( info >> 5) & 0x01ff;
		b = (info >> 1) & 0x0F;
		s = info & 0x01;

		StdMinutiaes[i].x=x;
		StdMinutiaes[i].y=y;
		StdMinutiaes[i].ang=ang;
		StdMinutiaes[i].val=b;
		StdMinutiaes[i].type=s;
	}
	if(pData[40]*2>0)
	{
		StdCenters[StdTpHeader->center].x=pData[40]*2;
		StdCenters[StdTpHeader->center].y=pData[41]*2;
		StdCenters[StdTpHeader->center].ang=0;
		StdCenters[StdTpHeader->center].val=0;
		StdCenters[StdTpHeader->center].type=2;
		StdTpHeader->center++;
	}
	if(pData[42]*2>0)
	{
		StdCenters[StdTpHeader->center].x=pData[40]*2;
		StdCenters[StdTpHeader->center].y=pData[41]*2;
		StdCenters[StdTpHeader->center].ang=0;
		StdCenters[StdTpHeader->center].val=0;
		StdCenters[StdTpHeader->center].type=2;
		StdTpHeader->center++;
	}
}

void StdToIsoa(unsigned char * pSrc,unsigned char * pDst)
{
	int n=51;
	int isosz=256;
	int tpsize=242;
	unsigned char isotp[512];
	STDTPHEADER		stdtpheader;
	STDMINUTIAE		stdminutiaes[100];
	STDMINUTIAE		stdcenters[5];
	int i;
	int x;
	int y;
	int ang;
	int t;
	int v;

	GetStdTpInfo(pSrc,&stdtpheader,stdminutiaes,stdcenters);

	memset(isotp,0x00,512);
	isotp[0]=0x46;  isotp[1]=0x4d;  isotp[2]=0x52;  isotp[3]=0x00;  //Maker
	isotp[4]=0x30;  isotp[5]=0x33;  isotp[6]=0x30;  isotp[7]=0x00;  //Version
	isotp[8]=0x00;  isotp[9]=0x00;  isotp[10]=(tpsize >> 8);  isotp[11]=(tpsize & 0xff);  //Size
	isotp[12]=0; //reps
	isotp[13]=1; //finger#
	//这里是放在一个BYTE里还是分开两个BYTE
	isotp[14]=0; //rep #
	isotp[15]=4; //type #
	isotp[16]=(256 >> 8);    isotp[17]=(256 & 0xFF);    //X image size
	isotp[18]=(288 >> 8);    isotp[19]=(288 & 0xFF);    //Y image size
	isotp[20]=(500 >> 8);    isotp[21]=(500 & 0xFF);    //X resolution
	isotp[22]=(500 >> 8);    isotp[23]=(500 & 0xFF);    //Y resolution
	isotp[24]=1;		//Count#
	isotp[25]=stdtpheader.val;  //Quality Value
	isotp[26]=0; isotp[27]=0;  //Quality Vendor ID
	isotp[28]=0; isotp[29]=0;  //Quality Algorithm ID
	isotp[30]=0x61;		//Minutia Field Length  , Ridge Ending Type
	isotp[31]=0; isotp[32]=0;  //Minutiae Extraction Vendor ID
	isotp[33]=0; isotp[34]=0;  //Minutiae Extraction Algorithm ID
	isotp[35]=stdtpheader.count;  //Number of Minutiae#

	for(i=0;i<stdtpheader.count;i++)
	{
		x=stdminutiaes[i].x;
		y=stdminutiaes[i].y;
		ang=stdminutiaes[i].ang;
		if(stdminutiaes[i].type==0)
			t=1;
		else if(stdminutiaes[i].type==1)
			t=2;
		else
			t=3;
		v=stdminutiaes[i].val;

		isotp[n]=((t << 6) & 0xc0)+((x >> 8) & 0x3F);
		isotp[n+1]=(x & 0xff);
		isotp[n+2]=((y >> 8) & 0x3F);
		isotp[n+3]=(y & 0xff);
		isotp[n+4]=((360-ang) * 256)/360;
		//isotp[n+4]=((ang) * 256)/ 360;
	    isotp[n+5]=(v*100)/16;
		//isotp[n+5]=v+78;
	    n=n+6;
	}
	if(stdtpheader.count>=34)
		memcpy(pDst,isotp,512);
	else
		memcpy(pDst,isotp,256);
}

void StdToIsob(unsigned char * pSrc,unsigned char * pDst)
{
	int n=28;
	int isosz=378;
	int tpsize=378;
	unsigned char isotp[512];
	STDTPHEADER		stdtpheader;
	STDMINUTIAE		stdminutiaes[100];
	STDMINUTIAE		stdcenters[5];
	int i;
	int x;
	int y;
	int ang;
	int t;
	int v;

	GetStdTpInfo(pSrc,&stdtpheader,stdminutiaes,stdcenters);

	memset(isotp,0x00,512);
	isosz=378;
	tpsize=378;
	isotp[0]=0x46;  isotp[1]=0x4d;  isotp[2]=0x52;  isotp[3]=0x00;  //Maker
	isotp[4]=0x20;  isotp[5]=0x32;  isotp[6]=0x30;  isotp[7]=0x00;  //Version
	isotp[8]=0x00;  isotp[9]=0x00;  isotp[10]=(tpsize >> 8);  isotp[11]=(tpsize & 0xff);  //Size
	isotp[12]=0; //reps
	//isotp[13]=1; //finger#
	isotp[13]=0; //finger#
	isotp[14]=(252 >> 8);    isotp[15]=(252 & 0xFF);    //X image size
	isotp[16]=(324 >> 8);    isotp[17]=(324 & 0xFF);    //Y image size
	isotp[18]=(197 >> 8);    isotp[19]=(197 & 0xFF);    //X resolution
	isotp[20]=(197 >> 8);    isotp[21]=(197 & 0xFF);    //Y resolution
	isotp[22]=1; //Count#
	isotp[26]=stdtpheader.val;  //Quality Value
	isotp[27]=stdtpheader.count;  //Number of Minutiae#

	for(i=0;stdtpheader.count;i++)
	{
		x=stdminutiaes[i].x;
		y=stdminutiaes[i].y;
		ang=stdminutiaes[i].ang;
		if(stdminutiaes[i].type==0)
			t=1;
		else if(stdminutiaes[i].type==1)
			t=2;
		else
			t=3;
		v=stdminutiaes[i].val;

		isotp[n]=((t << 6) & 0xc0)+((x >> 8) & 0x3F);
		isotp[n+1]=(x & 0xff);
		isotp[n+2]=((y >> 8) & 0x3F);
		isotp[n+3]=(y & 0xff);
		isotp[n+4]=((360-ang) * 256)/360;
		//isotp[n+4]=(ang * 256)/360;
		isotp[n+5]=(v*100)/16;
		//isotp[n+5]=v+78;
		n=n+6;
	}
	memcpy(pDst,isotp,378);
}

void IsoaToStd(unsigned char * pSrc,unsigned char * pDst)
{
	int n=51;
	int count=0;
	int i=0;
	unsigned int info=0;
	int x,y,a,t,v;

	pDst[0]=3;	pDst[1]=1;	pDst[2]=pSrc[25];

	for(i=0;i<pSrc[35];i++)
	{
		t=(pSrc[n] >> 6)& 0x03;
		x=(pSrc[n]& 0x3f)*256+pSrc[n+1];
		y=(pSrc[n+2]& 0x3f)*256+pSrc[n+3];
		a=(pSrc[n+4]);
		v=(pSrc[n+5]);

		if((t==1)||(t==2))
		{
			info=0;
			info=x << 23;
			info=info+(y << 14);
			info=info+((360-((a*360)/256)) << 5);
			info=info+((v*16/100) << 1);
			info=info+(t-1);

			pDst[56+count*4+0]=info >> 24;
			pDst[56+count*4+1]=info >> 16;
			pDst[56+count*4+2]=info >> 8;
			pDst[56+count*4+3]=info >> 0;

			count=count+1;
		}
		n=n+6;
	}
	pDst[3]=count;
}

void IsobToStd(unsigned char * pSrc,unsigned char * pDst)
{
	int n=28;
	int count=0;
	int i=0;
	unsigned int info=0;
	int x,y,a,t,v;

	pDst[0]=3;	pDst[1]=1;	pDst[2]=pSrc[26];

	for(i=0;i<pSrc[27];i++)
	{
		t=(pSrc[n] >> 6)& 0x03;
		x=(pSrc[n]& 0x3f)*256+pSrc[n+1];
		y=(pSrc[n+2]& 0x3f)*256+pSrc[n+3];
		a=(pSrc[n+4]);
		v=(pSrc[n+5]);

		if((t==1)||(t==2))
		{
			info=0;
			info=x << 23;
			info=info+(y << 14);
			info=info+((360-((a*360)/256)) << 5);
			info=info+((v*16/100) << 1);
			info=info+(t-1);

			pDst[56+count*4+0]=info >> 24;
			pDst[56+count*4+1]=info >> 16;
			pDst[56+count*4+2]=info >> 8;
			pDst[56+count*4+3]=info >> 0;

			count=count+1;
		}
		n=n+6;
	}
	pDst[3]=count;
}

int CheckHeader(unsigned char * pData)
{
	if((pData[0]==3)&&
		(pData[1]==1)
		)
		return 1;
	else if((pData[0]==0x46)&&
			(pData[1]==0x4d)&&
			(pData[4]==0x30)&&
			(pData[5]==0x33)&&
			(pData[6]==0x30)&&
			(pData[7]==0x00)
			)
		return 2;
	else if ((pData[0]==0x46)&&
			(pData[1]==0x4d)&&
			(pData[4]==0x20)&&
			(pData[5]==0x32)&&
			(pData[6]==0x30)&&
			(pData[7]==0x00)
			)
		return 3;
	else
		return 0;
}

void SetTemplateDirect(unsigned char * pSrc,unsigned char * pDst,int dk)
{
	unsigned int info=0;
	STDTPHEADER		stdtpheader;
	STDMINUTIAE		stdminutiaes[100];
	STDMINUTIAE		stdcenters[5];
	int i;
	int x;
	int y;
	int a;
	int t;
	int v;

	GetStdTpInfo(pSrc,&stdtpheader,stdminutiaes,stdcenters);
	memset(pDst,0x00,256);

	pDst[0]=3;	pDst[1]=1;	pDst[2]=pSrc[2]; pDst[3]=pSrc[3];
	for(i=0;i<stdtpheader.count;i++)
	{
		x=stdminutiaes[i].x;
		y=stdminutiaes[i].y;
		a=stdminutiaes[i].ang;
		t=stdminutiaes[i].type;
		v=stdminutiaes[i].val;

		info=0;
		switch(dk)
		{
		case 1:
			info=x << 23;
			info=info+((288-y) << 14);
			info=info+((360-a) << 5);
			break;
		case 2:
			info=(256-x) << 23;
			info=info+(y << 14);
			if(a<=180)
				info=info+((180-a) << 5);
			else
				info=info+((180+(360-a)) << 5);
			break;
		case 3:
			info=(256-x) << 23;
			info=info+((288-y) << 14);
			if(a<=180)
				info=info+((180+a) << 5);
			else
				info=info+((a-180) << 5);
			break;
		default:
			info=x << 23;
			info=info+(y << 14);
			info=info+(a << 5);
			break;
		}
		info=info+(v << 1);
		info=info+t;

		pDst[56+i*4+0]=info >> 24;
		pDst[56+i*4+1]=info >> 16;
		pDst[56+i*4+2]=info >> 8;
		pDst[56+i*4+3]=info >> 0;
	}
}


int Java_com_fgtit_data_Conversions_StdToIso( JNIEnv* env,jobject thiz,jint itype,jbyteArray input,jbyteArray output)
{
	int iret=0;
	jbyte * inputByte= (*env)->GetByteArrayElements(env,input,0);
	unsigned char * pinput = (unsigned char *)inputByte;
	unsigned char poutput[512];

	if(CheckHeader(pinput)==1)
	{
		switch(itype)
		{
		case 1:
			StdToIsoa(pinput,poutput);
			(*env)->SetByteArrayRegion(env,output,0,256,(jbyte*)poutput);
			iret=256;
			break;
		case 2:
			StdToIsob(pinput,poutput);
			(*env)->SetByteArrayRegion(env,output,0,378,(jbyte*)poutput);
			iret=378;
			break;
		default:
			break;
		}
	}
	(*env)->ReleaseByteArrayElements(env, input, inputByte, 0);
	return iret;
}

int Java_com_fgtit_data_Conversions_IsoToStd( JNIEnv* env,jobject thiz,jint itype,jbyteArray input,jbyteArray output)
{
	int iret=0;
	jbyte * inputByte= (*env)->GetByteArrayElements(env,input,0);
	unsigned char * pinput = (unsigned char *)inputByte;
	unsigned char poutput[512];

	switch(itype)
	{
	case 1:
		if(CheckHeader(pinput)==2)
		{
			IsoaToStd(pinput,poutput);
			(*env)->SetByteArrayRegion(env,output,0,256,(jbyte*)poutput);
			iret=256;
		}
		break;
	case 2:
		if(CheckHeader(pinput)==3)
		{
			IsobToStd(pinput,poutput);
			(*env)->SetByteArrayRegion(env,output,0,378,(jbyte*)poutput);
			iret=256;
		}
		break;
	default:
		break;
	}

	(*env)->ReleaseByteArrayElements(env, input, inputByte, 0);
	return iret;
}

int Java_com_fgtit_data_Conversions_StdChangeCoord( JNIEnv* env,jobject thiz,jbyteArray input,jint size,jbyteArray output,jint dk)
{
	int iret=0;
	jbyte * inputByte= (*env)->GetByteArrayElements(env,input,0);
	unsigned char * pinput = (unsigned char *)inputByte;
	unsigned char poutput[512];
	if(CheckHeader(pinput)==1)
	{
		if(size==512)
		{
			SetTemplateDirect(pinput,poutput,dk);
			SetTemplateDirect(&pinput[256],&poutput[256],dk);
			(*env)->SetByteArrayRegion(env,output,0,512,(jbyte*)poutput);
		}
		else
		{
			SetTemplateDirect(pinput,poutput,dk);
			(*env)->SetByteArrayRegion(env,output,0,256,(jbyte*)poutput);
		}
		iret=1;
	}
	(*env)->ReleaseByteArrayElements(env, input, inputByte, 0);
	return iret;
}

int Java_com_fgtit_data_Conversions_GetDataType( JNIEnv* env,jobject thiz,jbyteArray input)
{
	int iret=0;
	jbyte * inputByte= (*env)->GetByteArrayElements(env,input,0);
	unsigned char * pinput = (unsigned char *)inputByte;

	iret=CheckHeader(pinput);

	(*env)->ReleaseByteArrayElements(env, input, inputByte, 0);
	return iret;
}



