#!/bin/bash
PWDPATH=$(cd `dirname $0`; pwd)
DEVICEID=$1
curDate=`date +%Y%m%d-%H-%M-%S-%N`
AUTOTEST=$PWDPATH
TESTCASES=$AUTOTEST/case
DAILYTEST=$TESTCASES/dailyTest
EmailLibPath=$PWDPATH/lib/email_lib.py
REPORTDIR=$PWDPATH/report





testApp()
{
    pybot --variable DEVICE_ID:$1 --variable AUTODIR:$PWDPATH --loglevel TRACE  --outputdir $2/$curDate $3
    python $EmailLibPath $1 $2/$curDate
}



tvappPath=$TESTCASES/tv_application.tsv

testApp $DEVICEID $REPORTDIR $tvappPath



