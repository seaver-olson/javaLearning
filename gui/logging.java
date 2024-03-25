import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

//handles logging for server
public class logging {
    private String file;

    public logging(){
        this.file = "log.csv";
    }
    public logging(String file){
        this.file = file;
    }

    public void log(String message, String IP, String port){
        String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String[] log = {time, IP, port, message};
        csvWriter writer = new csvWriter(this.file);
        writer.writeRow(log, new csvReader(this.file).read().length);
    }
    
    public String[][] read(){
        csvReader reader = new csvReader(this.file);
        return reader.read();
    }

    public String[][] getBy(String Type, String value){
        HashMap<String, Integer> Types = new HashMap<>();
        Types.put("Time", 0);
        Types.put("IP", 1);
        Types.put("Port", 2);
        Types.put("Message", 3);
        int index = Types.get(Type);
        String[][] logs = read();
        String[][] results = new String[0][0];
        for (int i = 0; i < logs.length; i++){
            if (logs[i][index].equals(value)){
                String[][] newResults = new String[results.length+1][results[0].length];
                for (int j = 0; j < results[0].length; j++){
                    newResults[results.length][j] = logs[i][j];
                }
                results = newResults;
            }
        }
        return results;
    }
}
