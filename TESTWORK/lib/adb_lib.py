# -*- coding: utf-8 -*-
'''
Created on 2016-1-24

@author: hc
'''

from PIL import Image
import sys
import subprocess
import traceback
import time
import os
import re
from _struct import pack
from robot.api import logger

INDICATOR_RETURN_OUTPUT = "INSTRUMENTATION_STATUS";
IGNORE_RETURN_OUTPUT = {": current=", ": id=", ": class=", ": stream=", ": numtests=", ": test=", "_CODE: "};
DELIMITER_RETURN_OUTPUT ="----------------\n";



def adbCmd(cmd,Device_ID ,timeout=300):
    #print "*WARN* Danger Will Robinson"
    logger.console("Execute command: "+"adb -s " + Device_ID + " " + cmd)
    print "Execute command: "+"adb -s " + Device_ID + " " + cmd
    proc=subprocess.Popen("adb -s " + Device_ID + " " + cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    return_string=getStrFromExctCMD(proc)
    return return_string

#   GET OUT OF EXE_CMD
def getStrFromExctCMD(resultByCMD):
    str_result = ''
    if resultByCMD is not None:
        list_result = resultByCMD.stdout.readlines()
        for item in list_result:
            str_result += item+' '
        return str_result
    else:
        return None

def adb_disconnect(Device_ID):
    conn_CMD = 'adb disconnect %s'%(Device_ID)
    disconn_result =subprocess.Popen(conn_CMD, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    disconnInfo = getStrFromExctCMD(disconn_result)
    print disconnInfo 

def sendRemoteKeyCode(key_code,devices=''):
    cmd = "shell input keyevent "+str(key_code)
    print cmd
    adbCmd(cmd,devices)
    
def goEnter(devices=''):
    sendRemoteKeyCode(5004,devices)
    
def goBack(devices=''):
    sendRemoteKeyCode(4,devices)

def goHome(devices=''):
    sendRemoteKeyCode(3,devices)
    sendRemoteKeyCode(3,devices)
      
def matchRegex(pattern,src):
    match_result = re.search(pattern,src)
    if match_result is not None:
        return match_result
    else:
        raise Exception('Fail to match ['+src+'] by ['+pattern+']')
        return None
def adbConnect_cmd(Device_ID):
    global connState
    global DUTState
    conn_CMD = 'adb connect %s'%(Device_ID)
    conn_result =subprocess.Popen(conn_CMD, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    connInfo = getStrFromExctCMD(conn_result) 
    pattern_conn_result = '.*connected to.*'
    if matchRegex(pattern_conn_result,connInfo):
        print " Success: " + conn_CMD
        return True
    else:
        raise Exception('connect '+Device_ID+'fail!!!')
        return False
def kill_adb_server():
    killServer_CMD = 'kill-server'
    ex_result = adbCmd(killServer_CMD)
    print ex_result

def connectService(Device_ID,connTime=10):  
    for conncet_time in range(connTime):
        if adbConnect_cmd(Device_ID):               
            return True
    return  False

#   RUN UIAUTOMATOR TEST CASE
def UI_RUN_METHOD(c_method,Device_ID):
    cmd = "shell uiautomator runtest Test.jar -c "+c_method
    out = adbCmd(cmd,Device_ID)
    is_pass = convertUIAutomatorResult(out)
    if(is_pass):
        return  'pass'
    else:
        raise    Exception(cmd + "Failed !!!")
        return   'fail'
    
#   CONVERT THE RESULT OF UIAUTOMATOR RESULT
def convertUIAutomatorResult(outstr=""):
    mReturnOutput=''    
    if("OK (" in outstr):
        output_lines = outstr.split("\n");
        for i in range(0, len(output_lines)):
            if(INDICATOR_RETURN_OUTPUT not in output_lines[i]):
                mReturnOutput+=output_lines[i]+'\n'
            if("Test results for WatcherResultPrinter=" in output_lines[i]):
                break
        print mReturnOutput
        return True
    elif("FAILURES!!!" in outstr):
        output_lines = outstr.split("\n");
        for i in range(0, len(output_lines)):
            if(INDICATOR_RETURN_OUTPUT not in output_lines[i]):
                mReturnOutput+=output_lines[i]+'\n';
            if("Test results for WatcherResultPrinter=" in output_lines[i]):
                break;
        print mReturnOutput
        return False;  
    return False;  


#   GET THE VIEW OF TV AS PICTURE         

def getPicture(save_path,pic_name,Device_ID):
    cmd = " shell /system/bin/screencap -p /data/local/tmp/"+pic_name+".png"
    make_out = adbCmd(cmd,Device_ID)
    print make_out
    cmd = " pull /data/local/tmp/"+pic_name+".png  "+save_path
    pull_out = adbCmd(cmd,Device_ID)
    print pull_out

    
def get_appAbs(app_file_name):
    add_path = "resource/dut/APK/"
    path_Cur = path_Cur=os.path.split(os.path.realpath(__file__))[0]
    len_Cur = len(path_Cur)
    path_lines = path_Cur.split("/")
    len_last = len(path_lines[len(path_lines)-1])
    app_Abspath = path_Cur[0:len_Cur-len_last]+add_path+app_file_name
    app_Exit = os.path.exists(app_Abspath)
    if(not app_Exit):
        raise    Exception("APP File: "+app_Abspath+" not exit !!!")    
    return app_Abspath


def adbInstall(app_rout,Device_ID):
    app_rout = get_appAbs(app_rout)
    cmd = "install -r "+app_rout 
    return_string=adbCmd(cmd,Device_ID)
    if("Success" in return_string):
        print app_rout+"installed  success !!!"+"/n"
        return True
    else:
        print return_string
        raise    Exception("APP File: "+app_rout+" installed failed!!!") 
        return False

def adbUninstall(pack,Device_ID):
    cmd = "uninstall  " +  pack
    return_string=adbCmd(cmd,Device_ID)
    if("Success" in return_string):
        print pack+" uninstalled  success !!!"+"/n"
        return True
    else:
        print return_string
        raise    Exception(pack+" uninstalled failed!!!") 
        return False
   

