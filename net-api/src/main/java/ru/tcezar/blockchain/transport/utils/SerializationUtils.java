package ru.tcezar.blockchain.transport.utils;

import java.io.*;

/**
 * Created by Michael on 01.11.2018.
 */
public class SerializationUtils {

    public static <T extends Serializable> T getData(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        T result = (T) objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return result;
    }

    public static byte[] serializeObject(Serializable object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bos);
        objectOutputStream.writeObject(object);
        objectOutputStream.flush();
        objectOutputStream.close();
        byte[] result = bos.toByteArray();
        bos.flush();
        bos.close();
        return result;
    }
}
