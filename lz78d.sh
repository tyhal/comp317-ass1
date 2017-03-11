#!/usr/bin/env bash
mkdir -p test
cd test
TESTFILE=$1
BITS=$2
OPTS="-server -Xmx8G"

echo "      Deb"
java $OPTS Debinarizer encoded.lz78.bin encoded.lz78
echo "  Dec"
java $OPTS Decoder ${BITS:-16} encoded.lz78 $TESTFILE

#java $OPTS -verbose:gc -Xprof Encoder 31 encoded $TESTFILE > Encoder.prof.txt
#java $OPTS -verbose:gc -Xprof Decoder decoded.txt encoded > Decoder.prof.txt

