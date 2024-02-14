USER=${1:-335073}
PORT=${2:-18549}
CONFIG=helios.properties
if [ -f $CONFIG ]; then
  mvn -f ../pom.xml package
  FILES=(../target/*.jar)
  JAR=$(basename "${FILES[0]}")
  mv ../target/"${JAR}" ./
  echo "java -Dspring.config.location=helios.properties -jar ${JAR}" >> start.sh
  scp -P 2222 ./* s"${USER}"@se.ifmo.ru:~/blps/
  rm "$JAR" start.sh
  ssh -p 2222 s"$USER"@se.ifmo.ru -L "$PORT":helios:"$PORT"
else
  echo "Create $CONFIG before deploying"
fi
