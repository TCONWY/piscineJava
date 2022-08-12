mkdir target
javac -cp ".:./lib/JCDP-4.0.2.jar:./lib/jcommander-1.78.jar" -d ./target/ src/java/edu/school21/printer/*/*.java
cd target ; jar xf ../lib/JCDP-4.0.2.jar com ; jar xf ../lib/jcommander-1.78.jar com ; cd ..
cp -r src/resources target/.
rm -f ./target/images-to-chars-printer.jar
jar -cvfm ./target/images-to-chars-printer.jar src/manifest.txt -C target .
java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN
