import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class csvReaderGen<Item>{
    private String file;
    private String delimiter;
    private int row = 0;
    private int col = 0;

    public csvReaderGen(String file, String delimiter) {
        this.file = file;
        this.delimiter = delimiter;
    }

    public csvReaderGen(String file) {
        this.file = file;
        this.delimiter = ",";
    }
    public csvReaderGen() {
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

    @SuppressWarnings("unchecked")
    public Object[][] read(Class<Item> dataType){
        setSize();
        Item[][] data = (Item[][]) new Object[this.row][this.col];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String currentLine;
            int i = 0;
            while ((currentLine = reader.readLine()) != null) {
                String[] values = currentLine.split(delimiter);
                for (int j = 0; j < values.length; j++) {
                    try{
                        if (dataType == Integer.class){
                            data[i][j] = (Item) Integer.valueOf(values[j]);
                        }
                        else if (dataType == Double.class){
                            data[i][j] = (Item) Double.valueOf(values[j]);
                        }
                        else if (dataType == String.class){
                            data[i][j] = (Item) values[j];
                        }
                        else if (dataType == Boolean.class){
                            data[i][j] = (Item) Boolean.valueOf(values[j]);
                        }
                        else{
                            System.out.println("Error: Unsupported data type");
                        }
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


    public Object[][] stripHeader(Object[][] data){
        Object[][] newData = new Object[this.row-1][this.col];
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

    //transpose means to swap columns and rows
    public Object[][] transpose(){
        Object[][] data = read();
        Object[][] newData = new Object[this.col][this.row];
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.col; j++){
                newData[j][i] = data[i][j];
            }
        }
        return newData;
    }

    public Object[] getRow(int row){
        Object[][] data = read();
        Object[] rowData = new Object[this.col];
        for (int i = 0; i < this.col; i++){
            rowData[i] = data[row][i];
        }
        return rowData;
    }

    public Object[] getCol(int col){
        Object[][] data = read();
        Object[] colData = new Object[this.row];
        for (int i = 0; i < this.row; i++){
            colData[i] = data[i][col];
        }
        return colData;
    }

    public Object getCell(int row, int col){
        Object[][] data = read();
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
        //if location is not found, return -1,-1
        location[0] = -1;
        location[1] = -1;
        return location;
    }
} 
