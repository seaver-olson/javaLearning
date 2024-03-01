//create helper object to read csv file

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class csvReader {
    private String file;
    private String delimiter;
    private int row = 0;
    private int col = 0;

    public csvReader(String file, String delimiter) {
        this.file = file;
        this.delimiter = delimiter;
    }

    public csvReader(String file) {
        this.file = file;
        this.delimiter = ",";
    }
    public csvReader() {
        this.file = "";
        this.delimiter = ",";
        System.out.println("Warning: No file specified");
        System.out.println("Remember to use the setFile() method to specify the file to read from");
    }

    //setSize() is used as the object reads the file to make the runtime more efficient
    //however later it'd like to test weither more memory is used with multiple row,col vars or if the speed up is worth it
    public void setSize(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            //check if line is not empty, if not empty, split the line by delimiter
            while ((currentLine = reader.readLine()) != null) {
                String[] values = currentLine.split(delimiter);
                this.col = values.length;
                this.row++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public int getRowSize(){
        return this.row;
    }
    public int getColSize(){
        return this.col;
    }

    public void setFile(String file){
        this.file = file;
    }

    public void setDelimiter(String delimiter){
        this.delimiter = delimiter;
    }

    public String[][] read(){
        setSize();
        String[][] data = new String[this.row][this.col];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            int i = 0;
            while ((currentLine = reader.readLine()) != null) {
                String[] values = currentLine.split(delimiter);
                for (int j = 0; j < values.length; j++) {
                    try{
                        data[i][j] = values[j];
                    } catch (ArrayIndexOutOfBoundsException e){
                        System.out.println("ArrayIndexOutOfBoundsException: " + e);
                    }
                }
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String[][] stripHeader(String[][] data){
        String[][] newData = new String[this.row-1][this.col];
        for (int i = 1; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                newData[i-1][j] = data[i][j];
            }
        }
        return newData;
    }
    
    public boolean isEmpty(){
        if (this.file.equals("")){
            return true;
        }
        return false;
    }

    //swaps columns and rows
    public String[][] transpose(){
        String[][] data = read();
        String[][] newData = new String[this.col][this.row];
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                newData[j][i] = data[i][j];
            }
        }
        return newData;
    }

    public String[] getRow(int row){
        String[][] data = read();
        String[] rowData = new String[this.col];
        for (int i = 0; i < this.col; i++){
            rowData[i] = data[row][i];
        }
        return rowData;
    }

    public String[] getCol(int col){
        String[][] data = read();
        String[] colData = new String[this.row];
        for (int i = 0; i < this.row; i++){
            colData[i] = data[i][col];
        }
        return colData;
    }

    public String getCell(int row, int col){
        String[][] data = read();
        return data[row][col];
    }

    public int[] search(String value){
        String[][] data = read();
        int[] location = new int[2];
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                if (data[i][j].equals(value)){
                    location[0] = i;
                    location[1] = j;
                    return location;
                }
            }
        }
        location[0] = -1;
        location[1] = -1;
        return location;
    }

}