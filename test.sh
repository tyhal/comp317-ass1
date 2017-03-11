#!/usr/bin/env bash
mkdir -p test
cd test
javac -d . ../src/*.java
../lz78c.sh file 31
../lz78d.sh file.dec
