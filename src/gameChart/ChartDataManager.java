package gameChart;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import fileManager.UserFileManager;

public class ChartDataManager {
    private final String filePath;

    public ChartDataManager(String playerName) {
        this.filePath = UserFileManager.getAccountPath() + File.separator + playerName + File.separator + "chart.txt";
    }

    public Map<Integer, Integer> loadData() throws IOException {
        Map<Integer, Integer> data = new HashMap<>();
        File file = new File(filePath);
        
        if (!file.exists()) return data;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    try {
                        data.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    } catch (NumberFormatException ignored) {}
                }
            }
        }
        return data;
    }

    public void saveData(Map<Integer, Integer> data) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
            }
        }
    }

    public void initializeFile(int initialMoney) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            saveData(Collections.singletonMap(0, initialMoney));
        }
    }
}