import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class csvWriter {
    private String file;
    private String delimiter = ",";
    private boolean a = false;
    
    public csvWriter(String file, char mode){
        if (mode == 'a'){
            this.a = true;
        }
        this.file = file;
        System.out.println("File, " + file + ", has been opened for writing");
    }

    public csvWriter(String file){
        this.file = file;
        System.out.println("Warning: No mode specified, defaulting to write mode");
    }

    public void setDelimiter(String delimiter){
        this.delimiter = delimiter;
    }

    public void setMode(char mode){
        //this only accounts for if the user knows to put a or w, i will add error correction later
        if (mode == 'a'){
            this.a = true;
        }
        else{
            this.a = false;
        }
    }

    public void WriteRow(String[] data){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, a));
            for (int i = 0; i < data.length; i++){
                writer.write(data[i]);
                if (i < data.length - 1){
                    writer.write(delimiter);
                }
            }
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteColumn(String[] data){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, a));
            for (int i = 0; i < data.length; i++){
                writer.write(data[i]);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WriteCell(String data){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, a));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendCell(String data, int row, int col){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, a));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendRow(String[] data, int row){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, a));
            for (int i = 0; i < data.length; i++){
                writer.write(data[i]);
                if (i < data.length - 1){
                    writer.write(delimiter);
                }
            }
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendColumn(String[] data, int col){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, a));
            for (int i = 0; i < data.length; i++){
                writer.write(data[i]);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}