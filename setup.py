import os

os.system("cd csvKit; javac csvWriter.java; javac csvReader.java; cd ..")
os.system("mv csvKit/csvWriter.class gui/csvWriter.class")
os.system("mv csvKit/csvReader.class gui/csvReader.class")
print("The Environment is set up.")