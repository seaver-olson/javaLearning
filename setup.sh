cd csvKit
javac csvWriter.java
javac csvReader.java
cd ..
mv csvKit/csvWriter.class gui/csvWriter.class
mv csvKit/csvReader.class gui.csvReader.class
cd gui
javac logging.java
javac fetchFile.java
javac fetchPage.java
javac connectionThread.java
javac system.java
javac clients.java
echo "Enviroment has been setup"
