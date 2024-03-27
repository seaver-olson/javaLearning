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
        return new csvReader(this.file).read();
    }

    public String[][] getByUser(String IP){
        String[][] data = read();
        String[][] userLog = new String[0][0];
        for (int i = 0; i < data.length; i++){
            if (data[i][1].equals(IP)){
                String[][] newData = new String[userLog.length+1][data[0].length];
                for (int j = 0; j < userLog.length; j++){
                    for (int k = 0; k < data[0].length; k++){
                        newData[j][k] = userLog[j][k];
                    }
                }
                for (int j = 0; j < data[0].length; j++){
                    newData[userLog.length][j] = data[i][j];
                }
                userLog = newData;
            }
        }
        return userLog;
    }

    public String[][] getByUser(String IP, int port){
        String[][] data = read();
        String[][] userLog = new String[0][0];
        for (int i = 0; i < data.length; i++){
            if (data[i][1].equals(IP) && data[i][2].equals(Integer.toString(port))){
                String[][] newData = new String[userLog.length+1][data[0].length];
                for (int j = 0; j < userLog.length; j++){
                    for (int k = 0; k < data[0].length; k++){
                        newData[j][k] = userLog[j][k];
                    }
                }
                for (int j = 0; j < data[0].length; j++){
                    newData[userLog.length][j] = data[i][j];
                }
                userLog = newData;
            }
        }
        return userLog;   
    }

    public String[][] getByMessage(String message){
        String[][] data = read();
        String[][] messageLog = new String[0][0];
        for (int i = 0; i < data.length; i++){
            if (data[i][3].equals(message)){
                String[][] newData = new String[messageLog.length+1][data[0].length];
                for (int j = 0; j < messageLog.length; j++){
                    for (int k = 0; k < data[0].length; k++){
                        newData[j][k] = messageLog[j][k];
                    }
                }
                for (int j = 0; j < data[0].length; j++){
                    newData[messageLog.length][j] = data[i][j];
                }
                messageLog = newData;
            }
        }
        return messageLog;
    }

}
