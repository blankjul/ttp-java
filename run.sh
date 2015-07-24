rm -rf experiment

git reset --hard HEAD
git clean -f
git pull

mkdir experiment

mvn clean install
mvn exec:java -Dexec.mainClass="com.moo.ttp.Study"

Rscript Evaluation.R
