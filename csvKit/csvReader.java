import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class csvReader {
    private String file;
    private String delimiter;

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

    public String[][] read(){
        String[][] data = new String[0][0];
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            String line = br.readLine();
            int row = 0;
            while (line != null){
                String[] values = line.split(this.delimiter);
                String[][] newData = new String[row+1][values.length];
                for (int i = 0; i < row; i++){
                    for (int j = 0; j < values.length; j++){
                        newData[i][j] = data[i][j];
                    }
                }
                for (int i = 0; i < values.length; i++){
                    newData[row][i] = values[i];
                }
                data = newData;
                row++;
                line = br.readLine();
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    public int getColSize(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            String line = br.readLine();
            br.close();
            return line.split(this.delimiter).length;
        } catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    public int getRowSize(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(this.file));
            int row = 0;
            while (br.readLine() != null){
                row++;
            }
            br.close();
            return row;
        } catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isEmpty(){
        if (this.file.equals("")){
            return true;
        }
        return false;
    }
    public String[][] transpose(){
        String[][] data = read();
        String[][] newData = new String[getColSize()][getRowSize()];
        for (int i = 0; i < getRowSize(); i++){
            for (int j = 0; j < getColSize(); j++){
                newData[j][i] = data[i][j];
            }
        }
        return newData;
    }
    public String[] getRow(int row){
        String[][] data = read();
        return data[row];
    }

    public String[] getCol(int col){
        String[][] data = read();
        String[] colData = new String[getColSize()];
        for (int i = 0; i < getColSize(); i++){
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
        for (int i = 0; i < getRowSize(); i++){
            for (int j = 0; j < getColSize(); j++){
                if (data[i][j].equals(value)){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }
}