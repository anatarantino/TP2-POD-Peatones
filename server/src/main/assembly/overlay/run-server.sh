#!/bin/bash
java --add-opens jdk.management/com.sun.management.internal=ALL-UNNAMED -cp 'lib/jars/*' "ar.edu.itba.pod.server.Server" $*
