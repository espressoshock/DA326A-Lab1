package se.espressoshock.exercises.task1;

import java.io.*;

public class CFileManager {
    private static final String filePath = "senderUUIDPanel";

    public static boolean write(Object obj) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static Object read() {
        Object tObject = null;
        try (FileInputStream fis = new FileInputStream(filePath)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            tObject = objectInputStream.readObject();
            return tObject;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
