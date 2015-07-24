rm -rf experiment


if (( "$#" == 1 )) 
then
    git reset --hard HEAD
    git clean -f
    git pull
fi

mkdir experiment

mvn clean install
mvn exec:java -Dexec.mainClass="com.moo.ttp.Study"

Rscript Evaluation.R
