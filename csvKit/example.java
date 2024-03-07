public class example {
    public static void main(String[] args) {
        //csvReader reader = new csvReader("test.csv");
        //String[][] data = reader.read();
        csvReaderGen<Integer> reader = new csvReaderGen<>("test.csv");
        //make an example for generic read
        Object[][] data = reader.read(Integer.class);
        for (int i = 0; i < reader.getRowSize(); i++){
            for (int j = 0; j < reader.getColSize(); j++){
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}

