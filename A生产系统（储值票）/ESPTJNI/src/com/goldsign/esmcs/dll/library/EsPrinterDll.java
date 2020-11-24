/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.goldsign.esmcs.dll.library;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * ES打印机DLL
 * 
 * @author lenovo
 */
public interface EsPrinterDll extends Library{

    EsPrinterDll INSTANCE = (EsPrinterDll) Native.loadLibrary("PrinterAPI", EsPrinterDll.class);
 
    //?PRINTERAPI_QueryStatus@@YGHPAXPAH@Z
    
    /*************************************************************************
    //函数名：PRINTERAPI_ClosePrinterCom
    //功能  ：关闭串口		Close the port for Printer
    //参数  ：hCom   串口句柄	hCom: the handle of the printer port
    //返回值：0      成功		Return: 0 Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_ClosePrinterCom(Pointer hCom);//Close the Comm 关闭串口
    
    /*************************************************************************
    //函数名：PRINTERAPI_GetDllVersion
    //功能  ：得到动态库版本			Get the version of DLL
    //参数  ：hCom   串口句柄			hcom: the handle of printer port
                    Ver    返回动态库版本		Ver: the version of DLL
    //返回值：0      成功				0: Success
    //		  其他   参见错误代码表
    *************************************************************************/
    boolean PRINTERAPI_GetDllVersion(byte[] Ver);//得到动态库版本        
    
    /*************************************************************************
    //函数名：PRINTERAPI_InitPrinter
    //功能  ：初始化打印机		Init the printer
    //参数  ：hCom   串口句柄	hCom : the handle of the printer port
    //返回值：0      成功		Return: 0 Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_InitPrinter(Pointer hCom);//Init Printer初始化打印机
    
    /*************************************************************************
    //函数名：PRINTERAPI_OpenPrinterCom 
    //功能  ：打开串口		Open the port for Printer
    //参数  ：port int 串口号	ComNo, port number
    //		  baud int 波特率 ComRate, Comm baudrate

    //返回值：整型，串口句柄 Return: the Handle of the Printer port
    *************************************************************************/        
    Pointer PRINTERAPI_OpenPrinterCom(short ComNo, short ComRate);	// Open the comm, 打开串口
    
    /*************************************************************************
    //函数名：PRINTERAPI_PrintBufAndRunOneRow_CR
    //功能  ：打印行缓冲器里的内容并向前1行(回车)	Print the data in the buffer, and CR
    //参数  ：hCom   串口句柄			hCom: the handle of printer port
    //返回值：0      成功				0 Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_PrintBufAndRunOneRow_CR(Pointer hCom);//打印行缓冲器里的内容并向前1行(回车)

    /*************************************************************************
    //函数名：PRINTERAPI_PrintBufAndRunOneRow_LF
    //功能  ：打印行缓冲器里的内容并向前1行(换行)	Print the data in the buffer, and LF
    //参数  ：hCom   串口句柄			hCom: the handle of Printer port
    //返回值：整型，串口句柄			0: Success
    *************************************************************************/
    short PRINTERAPI_PrintBufAndRunOneRow_LF(Pointer hCom);	//打印行缓冲器里的内容并向前1行(换行)
    
    /*************************************************************************
    //函数名：PRINTERAPI_PrintChars
    //功能  ：打印字符				Print Char
    //参数  ：hCom   串口句柄			hCom: the handle of printer port
    //返回值：0      成功				0 Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_PrintChars(Pointer hCom, byte[] ch);	//打印字符
    
    /*************************************************************************
    //函数名：PRINTERAPI_PrinterToChinese
    //功能  ：使打印机进入/退出汉字打印方式 Let printer enter Chinese Char mode or not
    //参数  ：hCom   串口句柄		hCom: the handle of the printer port
    //		  mode   = 0 使打印机退出汉字打印方式	Mode: 0: exit chinese Char Mode
                                    = 1 使打印机进入汉字打印方式 1: enter Chinese Char mode.
    //返回值：0      成功					Return: 0 Success
    //		  其他   参见错误代码表
    *************************************************************************/        
    short PRINTERAPI_PrinterToChinese(Pointer hCom, byte mode);//使打印机进入/退出汉字打印方式
    
    /*************************************************************************
    //函数名：PRINTERAPI_RunNRows
    //功能  ：打印行缓冲器里的内容，并向前走纸n点行 Print the data in the buffer, and run n lines
    //参数  ：hCom   串口句柄			hcom: the handle of printer port
                    n      向前走纸n点行		n: Run n lines
    //返回值：0      成功				0: Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_RunNRows(Pointer hCom, byte n);//打印行缓冲器里的内容，并向前走纸n点行
    
    /*************************************************************************
    //函数名：PRINTERAPI_SetPrintMode
    //功能  ：初始化串口					Set the Printer Mode
    //参数  ：hCom			串口句柄		hCom: the handle of Printer port
                    PrintType		设置打印字体 =0 小字体(默认)    =1 大字体 PrintType: 0 small font(default), 1 Big font
                    PrintDiret	设置打印方向 =0 反向打印(默认)  =1 正向打印 PrintDiret: 0: From Right to Left (default), 1: From Left to Right
    //返回值：0      成功							0 Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_SetPrintMode(Pointer hCom, byte PrintType, byte PrintDiret); // 设置字符打印方式
    
    /*************************************************************************
    //函数名：PRINTERAPI_SetRowDis_16Mile
    //功能  ：设置字符行间距为1/6英寸 Set the gap of char to 1/6 inch
    //参数  ：hCom   串口句柄	 hcom: the handle of printer port
    //返回值：0      成功		0: Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_SetRowDis_16Mile(Pointer hCom);//设置字符行间距为1/6英寸 
    
    /*************************************************************************
    //函数名：PRINTERAPI_SetRowDis_NPoints
    //功能  ：设置行间距为n点	set the line gap to n points,
    //参数  ：hCom   串口句柄	hCom: the handle of printer port
                    n      设置行间距为n点 	n: set the line gap to n points
    //返回值：0      成功				0 Success
    //		  其他   参见错误代码表
    *************************************************************************/
    short PRINTERAPI_SetRowDis_NPoints(Pointer hCom, byte n);//设置行间距为n点        
                     
}
