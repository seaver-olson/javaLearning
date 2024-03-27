echo "Setting up enviroment..."
cd csvKit;
javac csvReader.java; javac csvWriter.java;
cd ..;
mv csvKit/csvReader.class FTP/Admin/csvReader.class; cp FTP/Admin/csvReader.class FTP/Client/csvReader.class;
mv csvKit/csvWriter.class FTP/Admin/csvWriter.class; cp FTP/Admin/csvWriter.class FTP/Client/csvWriter.class;
cd FTP/Admin/;
javac logging.java; javac fetchFile.java; javac connectionThread.java; javac system.java;
cd ..; mv Admin/fetchFile.class Client/fetchFile.class;#fix later and make it only rely on Client
cd Client/;
javac fetchPage.java; javac register.java; javac login.java; javac clients.java;
#add path adding UI;
echo "Finished setting up enviroment"