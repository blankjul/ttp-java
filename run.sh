rm -rf experiment
mkdir experiment

mvn clean install
mvn exec:java -Dexec.mainClass="com.moo.ttp.Study"

