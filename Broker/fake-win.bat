@ECHO OFF

SET ROOM_NR=%1
IF [%ROOM_NR%]==[] GOTO :missing_room_nr

CALL dns-sd -P "Broker-Room-%ROOM_NR%" _coap._udp local 5683 "/broker" 127.0.0.1

:missing_room_nr
	ECHO No room number is provided as argument.