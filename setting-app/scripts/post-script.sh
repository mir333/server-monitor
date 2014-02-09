#!/usr/bin/env bash

RESULT=$(ssh root@$1 -p 222 "/opt/bamt/serverstatus;" 2>/dev/null)

echo $RESULT
if [[ -z "$RESULT" ]]; then
    curl -X POST  -d "error=$1" http://ligas.im:8080/setting-app/saveSettings
else
    curl -X POST  -d "data=$RESULT" http://ligas.im:8080/setting-app/saveSettings
fi
