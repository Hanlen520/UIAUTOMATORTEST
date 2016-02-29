'''
Created on 2016-1-25

@author: hc
'''
import sys
import subprocess
import traceback
import time
import os
import re
import adb_lib
import commands
ISOTIMEFORMAT = '%Y%m%d%H%M%S';



def make_ReportDir(name=""):
    #global ISOTIMEFORMAT
    time_Cur = time.strftime( ISOTIMEFORMAT, time.localtime() )
    re_Dir = get_repAbs(time_Cur)+name
    os.mkdir(re_Dir)
    open(re_Dir+"/TV_LOG.txt",'w')
    return re_Dir
    
    
def get_repAbs(app_file_name):
    add_path = "log/"
    path_Cur=os.path.split(os.path.realpath(__file__))[0]
    len_Cur = len(path_Cur)
    path_lines = path_Cur.split("/")
    len_last = len(path_lines[len(path_lines)-1])
    app_Abspath = path_Cur[0:len_Cur-len_last]+add_path+app_file_name   
    return app_Abspath    
    
    
def logcat_start(dir , Device_ID):
    print dir
    pr_commond = "adb -s "+Device_ID+" logcat -c "
    os.system(pr_commond)
    cmd ="ps -ef | grep "+"\'"+Device_ID+" logcat -b main -b system -K -v threadtime"+"\'"
    commond ="adb -s "+Device_ID+" logcat -b main -b system -K -v threadtime > "+dir+"/TV_LOG.txt "+" &"
    print "ADB EXCU:  "+commond
    os.popen(commond).read()
    print "ADB EXCU:  "+cmd
    proc=subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    return_string=adb_lib.getStrFromExctCMD(proc)
    output_lines = return_string.split("\n");
    logcat_pid = 0;
    for i in range(0, len(output_lines)):
        if("adb" in output_lines[i]):
            key_lines = output_lines[i].split("    ")
            print key_lines[0]
            print key_lines[1] 
            if(int(key_lines[1])>logcat_pid):
                logcat_pid=int(key_lines[1])
    if(logcat_pid==0):
        raise    Exception(commond+" Excuted Failed!!!") 
    print "................................................"
    print logcat_pid
    print return_string
    print "Logcat "+Device_ID+"SUCESS AND THE PID IS : "+str(logcat_pid)
    return 
    
def logcat_end(Device_ID): 
    cmd ="ps -ef | grep "+"\'"+Device_ID+" logcat -b main -b system -K -v threadtime"+"\'" 
    proc=subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    return_string=adb_lib.getStrFromExctCMD(proc)
    output_lines = return_string.split("\n");
    logcat_pid = 0;
    for i in range(0, len(output_lines)):
        if("adb" in output_lines[i]):
            key_lines = output_lines[i].split("    ")
            if(int(key_lines[1])>logcat_pid):
                logcat_pid=int(key_lines[1])
    if(logcat_pid==0):
        raise    Exception("Logcat not found!!!") 
    print "Logcat "+Device_ID+" PID IS : "+str(logcat_pid)
    print "Will kill the pid of logcat !!!"
    command = "kill -9 "+str(logcat_pid)
    os.system(command)  
       
    
    
    
    
    
    
    
    
    
    
    
