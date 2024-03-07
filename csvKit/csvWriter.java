import java.io.FileWriter;

public class csvWriter {
    private String file;
    private boolean append;
    private String[][] data;
    
    public csvWriter(String file, boolean append){
        this.file = file;
        this.append = append;
        csvReader reader = new csvReader(file);
        this.data = reader.read();
    }

    public csvWriter(String file){
        this.file = file;
        System.out.println("Warning: No append value given, defaulting to false.");
        this.append = false;
    }

    public csvWriter(){
        System.out.println("Warning: No file given, defaulting to test.csv");
        this.file = "";
        System.out.println("Warning: No append value given, defaulting to false.");
        this.append = false;
    }    

    public void setFile(String file){
        this.file = file;
        setAppend();
    }

    public void setAppend(boolean append){
        this.append = append;
        if (append){
            csvReader reader = new csvReader(file);
            this.data = reader.read();
        }
        else{
            this.data = new String[0][0];
        }
    }

    public void setAppend(){
        if (this.append){
            csvReader reader = new csvReader(file);
            this.data = reader.read();
        }
        else{
            this.data = new String[0][0];
        }
    }

    public void writeRow(String[] row, int index){
        //if append then add String[] to data String[][]
        if (append){ 
            String[][] newData = new String[this.data.length+1][this.data[0].length];
            for (int i = 0; i < index; i++){
                for (int j = 0; j < this.data[0].length; j++){
                    newData[i][j] = this.data[i][j];
                }
            }
            for (int i = 0; i < row.length; i++){
                newData[index][i] = row[i];
            }
            for (int i = index; i < this.data.length; i++){
                for (int j = 0; j < this.data[0].length; j++){
                    newData[i+1][j] = this.data[i][j];
                }
            }
            this.data = newData;
        }
        else{
            this.data = new String[1][row.length];
        }
        //write data to file
        try{
            FileWriter writer = new FileWriter(this.file);
            for (int i = 0; i < this.data.length; i++){
                for (int j = 0; j < this.data[0].length; j++){
                    writer.append(this.data[i][j]);
                    // if not last ele add comma
                    if (j < this.data[0].length-1){
                        writer.append(",");
                    }
                }
                // if not last row add newline
                writer.append("\n");
            }
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    
}