#!/bin/bash
#TP_PATH=#complete#
TP_PATH=/Users/martirodriguez/Documents/ITBA/POD/TP2-POD-Peatones

cd $TP_PATH
mvn install

cd server/target
tar -xvzf tpe2-g13-parent-server-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-g13-parent-server-1.0-SNAPSHOT
chmod +x *.sh

cd $TP_PATH/client/target
tar -xvzf tpe2-g13-parent-client-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-g13-parent-client-1.0-SNAPSHOT
chmod +x *.sh

cd $TP_PATH