package com.company;

import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class json {
    public static void writeJsonSimpleDemo(String filename) throws Exception {
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("name", "Stackabuser");
        sampleObject.put("id", 1234);

        Files.write(Paths.get(filename), sampleObject.toJSONString().getBytes());
    }
    public static void js_write(user USER) throws IOException {
        FileOutputStream fos = new FileOutputStream("temp.json");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(USER);
        oos.flush();
        oos.close();
    }
    public static void js_read_del() throws Exception {
        FileInputStream fis = new FileInputStream("temp.json");
        ObjectInputStream oin = new ObjectInputStream(fis);
        user ts = (user) oin.readObject();
        System.out.println("name="+ts.name+" id="+ts.id);
        oin.close();
    }


}
