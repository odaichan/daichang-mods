package net.daichang.dcmods.utils.helpers;

import net.daichang.dcmods.library.DCBaseLib;
import net.minecraft.world.entity.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper implements DCBaseLib {
    static final String DEFAULT_FILE_PATH = path + "/dc_list.txt";

    public static List<String> getFileItems(String filePath) {
        List<String> fileItems = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileItems.add(line);
            }
        } catch (IOException ignored) {}
        return fileItems;
    }

    public static boolean hasYouItem(String path, String item) {
        boolean isTrue = false;
        for (String s : getFileItems(path)) {
            if (s.contains(item)) {
                isTrue = true;
                break;
            }
        }
        return isTrue;
    }

    public static void writeYouItem(String filePath, String youItem) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            if (!hasYouItem(filePath, youItem)) {
                bw.write("[" + youItem + "]");
                bw.newLine();
            } else {
                System.out.println("当前内容已经在列表了");
            }
        } catch (IOException ignored) {}
    }

    public static boolean defaultHasTarget(Entity entity) {
        return hasYouItem(DEFAULT_FILE_PATH, entity.getStringUUID());
    }

    public static void defaultWriteYouItem(Entity entity) {
        writeYouItem(DEFAULT_FILE_PATH, entity.getStringUUID());
    }

    public static void removeYouItem(String filePath, String removeItem) {
        File inputFile = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                String currentLine;
                while((currentLine = reader.readLine()) != null) {
                    String trimmedLine = currentLine.trim();
                    if(trimmedLine.equals(removeItem)) continue;
                    writer.write(currentLine);
                    writer.newLine();
                }
                inputFile.delete();
                tempFile.renameTo(inputFile);
        } catch (IOException ignored) {}
    }

    public static void removeDefaultItem(Entity entity) {
        removeYouItem(DEFAULT_FILE_PATH, entity.getStringUUID());
    }
}
