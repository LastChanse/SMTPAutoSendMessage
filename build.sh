set +v
echo "0/5 Build on Linux"
read -p "Press any key to resume ..."
clear
echo "1/5 Deleate target and out"
rm -rf target
rm -rf out
read -p "Press any key to resume ..."
clear
echo "2/5 Build jar with dependencies"
bash mvnw install
bash mvnw dependency:copy-dependencies
read -p "Press any key to resume ..."
clear
echo "3/5 Make out folder"
mkdir out
cd out
mkdir exe
mkdir jar
cd ..
mkdir ./out/jar/jdk-19
cp -R ./jdk-19/* ./out/jar/jdk-19/
mkdir ./out/exe/jdk-19
cp -R ./jdk-19/* ./out/exe/jdk-19/
mkdir ./out/jar/dependency
cp -R ./target/dependency/* ./out/jar/dependency/
mkdir ./out/exe/dependency
cp -R ./target/dependency/* ./out/exe/dependency/
cp ./target/SMTPAutoSendMessage-1.0-SNAPSHOT.jar ./out/jar/
mv -i ./out/jar/SMTPAutoSendMessage-1.0-SNAPSHOT.jar ./out/jar/smtpautomess.jar
touch ./out/jar/start.sh
echo "./jdk-19/bin/java --module-path=dependency/ --add-modules javafx.controls,javafx.fxml,java.mail -cp smtpautomess.jar com.example.smtpautosendmessage.SMTPApplication" > ./out/jar/start.sh
read -p "Press any key to resume ..."
clear
echo "4/5 Build exe"
java -jar ./Launch4j/launch4j.jar  buildLaunch4j.xml
read -p "Press any key to resume ..."
clear
echo "5/5 Build done"
read -p "Press any key to resume ..."
exit