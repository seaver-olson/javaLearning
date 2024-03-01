public class example {
    public static void main(String[] args) {
        csvReader reader = new csvReader("test.csv");
        String[][] data = reader.read();
        csvWriter writer = new csvWriter("test.csv",'a');
        String[] newdata = {"new","data","to","append"};
        writer.WriteRow(newdata);
        data = reader.read();
        for (int i = 0; i < data.length; i++){
            for (int j = 0; j < data[i].length; j++){
                System.out.print(data[i][j] + " ");
            }
            System.out.println();
        }
    }
}

