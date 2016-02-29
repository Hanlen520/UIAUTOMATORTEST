#!/usr/bin/env python3  
#coding: utf-8  
import smtplib  
import os
import sys
from email.mime.text import MIMEText  
from email.mime.multipart import MIMEMultipart
from email.header import Header  
  
sender = 'YOUR EMAIL ADDRESS'  
receiver = 'EMAIL ADDRESS YOU SEND TO'  
subject = 'Test Report Email'  
smtpserver = 'smtp.qq.com'  //EMAIL SERVER
username = 'YOUR EMAIL ADDRESS'  
password = 'PASSWORD OF YOUR EMAIL' 
#username = 'Whaley_autest@qq.com'  
#password = 'whaley2016'  
def sendReportToEmail(deviceid,outdir):  
    #msg = MIMEText('你好','text','utf-8')#中文需参数‘utf-8’，单字节字符不需要  
    msg = MIMEMultipart()
    msg['Subject'] = Header(subject+deviceid, 'utf-8')  
    msg['From'] = sender
    msg['to'] = ";".join([receiver])
    path_Cur=outdir
    log_path = path_Cur+"/log.html"
    report_path = path_Cur+"/report.html"
    output_path = path_Cur+"/output.xml"
    #Add email file_log
    att1 = MIMEText(open(log_path, 'rb').read(), 'base64', 'gb2312')
    att1["Content-Type"] = 'application/octet-stream'
    att1["Content-Disposition"] = 'attachment; filename="log.html"'#这里的filename可以任意写，写什么名字，邮件中显示什么名字
    msg.attach(att1)
    #Add email file_output
    att2 = MIMEText(open(output_path, 'rb').read(), 'base64', 'gb2312')
    att2["Content-Type"] = 'application/octet-stream'
    att2["Content-Disposition"] = 'attachment; filename="output.xml"'#这里的filename可以任意写，写什么名字，邮件中显示什么名字
    msg.attach(att2)
    #Add email file_report
    att3 = MIMEText(open(report_path, 'rb').read(), 'base64', 'gb2312')
    att3["Content-Type"] = 'application/octet-stream'
    att3["Content-Disposition"] = 'attachment; filename="report.html"'#这里的filename可以任意写，写什么名字，邮件中显示什么名字
    msg.attach(att3)
    smtp = smtplib.SMTP_SSL()  
    smtp.connect('smtp.qq.com')  
    smtp.login(username, password)
    smtp.sendmail(sender, receiver, msg.as_string())  
    smtp.quit()  
if __name__ == '__main__':
    sendReportToEmail(sys.argv[1],sys.argv[2])
    
    
