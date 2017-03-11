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


