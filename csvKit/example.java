public class example {
    public static void main(String[] args) {
        csvReader reader = new csvReader("test.csv");
        String[][] data = reader.read();
        System.out.println(data[0][0]);
        
    }
}

