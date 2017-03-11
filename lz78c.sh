#!/usr/bin/env bash
TESTFILE=$1
BITS=$2
OPTS="-server -Xmx8G"

echo "Start"
echo "  Enc"
java $OPTS Encoder ${BITS:-16} file.enc $TESTFILE
echo "      Bin"
java $OPTS Binarizer file.bin file.enc
echo "          Pac"
java $OPTS Packer file.pac file.bin

#java $OPTS -verbose:gc -Xprof Encoder 31 encoded $TESTFILE > Encoder.prof.txt
#java $OPTS -verbose:gc -Xprof Decoder decoded.txt encoded > Decoder.prof.txt

