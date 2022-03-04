#!/bin/bash
readonly PROC_NAME="com.app.SOTPServerApp"
readonly DAEMON="-cp sotp-serverapp.jar:libs/* com.app.SOTPServerApp"
readonly PID_PATH="/user/stock/sotp_process/"
readonly PROC_PID="${PID_PATH}${PROC_NAME}.pid"
readonly SPRING_PROFILES_ACTIVE="nhreal"
readonly SPRING_COMMANDLINE="sotpstart"

start()
{
 	echo "Starting  ${PROC_NAME}..."
	local PID=$(get_status)
	if [ -n "${PID}" ]; then
		echo "${PROC_NAME} is already running"
		exit 0
	fi
#	nohup java -jar -XX:MaxPermSize=128m -Xms512m -Xmx1024m "${DAEMON}" > /dev/null 2>&1 &
    nohup java -XX:MaxPermSize=128m -Xms512m -Xmx1024m -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} ${DAEMON} ${SPRING_COMMANDLINE} > /dev/null 2>&1 &
    
	local PID=${!}

	if [ -n ${PID} ]; then
		echo " - Starting..."
		echo " - Created Process ID in ${PROC_PID}"
		echo ${PID} > ${PROC_PID}
	else
		echo " - failed to start."
	fi
}
stop()
{
	echo "Stopping ${PROC_NAME}..."
	local DAEMON_PID=`cat "${PROC_PID}"`

	if [ "$DAEMON_PID" -lt 3 ]; then
		echo "${PROC_NAME} was not  running."
	else
		kill $DAEMON_PID
		rm -f $PROC_PID
		echo " - Shutdown ...."
	fi
}
status()
{
	local PID=$(get_status)
	if [ -n "${PID}" ]; then
		echo "${PROC_NAME} is running"
	else
		echo "${PROC_NAME} is stopped"
		# start daemon
		#nohup java -jar "${DAEMON}" > /dev/null 2>&1 &
	fi
}

get_status()
{
	ps ux | grep ${PROC_NAME} | grep -v grep | awk '{print $2}'
}

case "$1" in
    start)
        start
        sleep 7
        ;;
    stop)
        stop
        sleep 5
        ;;
    status)
    status "${PROC_NAME}"
	;;
	*)
	echo "Usage: $0 {start | stop | status }"
esac
exit 0

#REF_URL : https://github.com/Gavinkim/springboot-execute-with-shell/blob/master/springboot.sh
# - spring boot execute script
#		chmod +x sotp_process.sh
#		> start 	./sotp_process.sh start
#		> stop 		./sotp_process.sh stop
#		> status 	./sotp_process.sh status





