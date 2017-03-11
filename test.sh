#!/usr/bin/env bash
mkdir -p test
cd test
javac -d . ../src/*.java
../lz78c.sh enwik8 31
../lz78d.sh file.dec
