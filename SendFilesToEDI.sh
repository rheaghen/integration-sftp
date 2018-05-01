#!/bin/bash

umask 002
export "$@"

echo "SendFilesToSAP parameters from scheduler = ${@:4}"
echo "SendFilesToSAP preferences loading from $PREFS"

PROPS="-Dvsp.environment=$ENV -Dvsp.preferences=$PREFS -Djava.io.tmpdir=$BASETEMPDIR "

echo "SendFilesToSAP System Properties = $PROPS"

java -cp payment-sftp-example.jar $APMPROPS $PROPS org.springframework.batch.core.launch.support.CommandLineJobRunner com.vsp.payment.sftp.example.config.RunConfiguration SendFilesToSAP $JOBINSTANCE "${@:7}"