rem 192.168.0.101

cd ..\src\test\resources\

java -jar selenium-server-standalone-3.8.0.jar -role node -hub http://localhost:4444/grid/register/ -port 10001 -browser "browserName=chrome,maxInstances=3" -browser "browserName=firefox,maxInstances=3"