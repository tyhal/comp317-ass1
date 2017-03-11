#!/usr/bin/env bash
TESTFILE=$1
OPTS="-server -Xmx8G"

echo "          Unp"
java $OPTS Unpacker file.unp file.pac
echo "      Deb"
java $OPTS Debinarizer file.deb file.unp
echo "  Dec"
java $OPTS Decoder file.dec file.deb
echo "Complete"

#java $OPTS -verbose:gc -Xprof Encoder 31 encoded $TESTFILE > Encoder.prof.txt
#java $OPTS -verbose:gc -Xprof Decoder decoded.txt encoded > Decoder.prof.txt

