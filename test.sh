#!/usr/bin/env bash
TESTFILE=$1
mkdir -p test
cd test
javac -d . ../src/*.java
#echo "Enc"
#../lz78c $TESTFILE 31
#echo "Dec"
#../lz78d $TESTFILE.lz78

OPTS="-Xdebug -ea"
cat $TESTFILE | java ${OPTS} Packer | java ${OPTS} Unpacker > out
diff $TESTFILE out
#echo "Check"
#diff $TESTFILE $TESTFILE.lz78.orig && rm $TESTFILE.lz78.orig
#rm *.class && cd ..
