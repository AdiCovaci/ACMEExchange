package service;

import java.io.*;
import java.sql.Timestamp;

public class AuditService {
    private File logFile = new File("log/audit.csv");

    private AuditService() {

    }

    public void log(String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            FileWriter fileWriter = new FileWriter(logFile, true);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(timestamp + "," + message + "\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AuditService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static AuditService INSTANCE = new AuditService();
    }
}
