import java.text.SimpleDateFormat;
import java.util.Date;

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
        if (IP.equals("") || IP.equals(null)){
            log(message);
        }
        else if (message.equals("") || message.equals(null)){
            return;
        }
        IP = IP.substring(1);
        String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String[] log = {time, IP, port, message};
        csvWriter writer = new csvWriter(this.file, true);
        writer.writeRow(log, new csvReader(this.file).read().length-1);
        System.out.println("[" + IP + ":" + port + "]: " + message);
    }

    public void log(String message){
        String IP = "Unknown";
        String time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String[] log = {time, IP, "Unknown", message};
        csvWriter writer = new csvWriter(this.file, true);
        writer.writeRow(log);
        System.out.println("[" + IP + ":" + "Unknown" + "]: " + message);
    }
    
    public String[][] read(){
        csvReader reader = new csvReader(this.file);
        return reader.read();
    }

}
