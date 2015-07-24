rm -rf experiment
mkdir experiment

if [ -n "$1" ]
then  MAXEVAL=$1
else MAXEVAL=25000
fi


echo "Evaluation Set to "
echo $MAXEVAL 

mvn clean install
mvn exec:java -Dexec.mainClass="com.moo.ttp.Study" -Dexec.args="$MAXEVAL"

Rscript Evaluation.R
