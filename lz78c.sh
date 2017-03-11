#!/usr/bin/env bash
TESTFILE=$1
BITS=$2
OPTS="-server -Xmx2G"

echo "Start"
echo "  Enc"
java ${OPTS} Encoder ${BITS:-16} file.enc $TESTFILE
echo "      Bin"
java ${OPTS} Binarizer file.bin file.enc
echo "          Pac"
java ${OPTS} Packer file.pac file.bin


