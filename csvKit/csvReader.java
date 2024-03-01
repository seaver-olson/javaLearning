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

    public String[] readCol(int col){
        setSize();
        String[] data = new String[this.row];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            int i = 0;
            while ((currentLine = reader.readLine()) != null) {
                String[] values = currentLine.split(delimiter);
                try{
                    data[i] = values[col];
                } catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("ArrayIndexOutOfBoundsException: " + e);
                }
                i++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public String[] readRow(int row){
        setSize();
        String[] data = new String[this.col];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            int i = 0;
            while ((currentLine = reader.readLine()) != null) {
                String[] values = currentLine.split(delimiter);
                if (i == row){
                    for (int j = 0; j < values.length; j++) {
                        try{
                            data[j] = values[j];
                        } catch (ArrayIndexOutOfBoundsException e){
                            System.out.println("ArrayIndexOutOfBoundsException: " + e);
                        }
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

    public String readCell(int row, int col){
        setSize();
        String data = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            int i = 0;
            while ((currentLine = reader.readLine()) != null) {
                String[] values = currentLine.split(delimiter);
                if (i == row){
                    try{
                        data = values[col];
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

}