public class example {
    public static void main(String[] args) {
        csvReader reader = new csvReader("test.csv");
        String[][] data = new String[reader.getRowSize()][reader.getColSize()];
        data = reader.read();
        for (int i = 0; i < reader.getRowSize(); i++) {
            for (int j = 0; j < reader.getColSize(); j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}

