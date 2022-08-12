mkdir target
javac -d ./target/ src/java/edu/school21/printer/app/Main.java src/java/edu/school21/printer/logic/Logic.java
jar cvfm ./target/images-to-chars-printer.jar src/manifest.txt -C target edu/ -C src/ resources
cp -r src/resources target/.
java -jar target/images-to-chars-printer.jar . o
