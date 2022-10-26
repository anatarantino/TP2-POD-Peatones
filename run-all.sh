#!/bin/bash

TP_PATH=#complete#

cd $TP_PATH
mvn install

cd server/target
tar -xvzf tpe2-g13-server-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-g13-server-1.0-SNAPSHOT
chmod +x *.sh

cd $TP_PATH/client/target
tar -xvzf tpe2-g13-client-1.0-SNAPSHOT-bin.tar.gz
cd tpe2-g13-client-1.0-SNAPSHOT
chmod +x *.sh

cd $TP_PATH