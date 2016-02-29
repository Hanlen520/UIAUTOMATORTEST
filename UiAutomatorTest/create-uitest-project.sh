
#echo echo start to build jar...

if [ -d bin/classes/com ]; then
    :
else
    mkdir -p bin/classes/com
fi
rm -rf bin/classes/com
cp -R bin/com bin/classes/com/

android create uitest-project -n Test -t android-22 -p .

#ant build
