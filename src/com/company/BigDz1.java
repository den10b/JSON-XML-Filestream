package com.company;

import org.json.simple.JSONObject;

import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.*;
import java.nio.file.*;

public class BigDz1 {

    private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");


    public static List<Map<String, String>> getInfo() {

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        File [] roots = File.listRoots (); // Получить список разделов диска
        for (File file : roots) {
            Map<String, String> map = new HashMap<String, String>();

            long freeSpace=file.getFreeSpace();
            long totalSpace=file.getTotalSpace();
            long usableSpace=totalSpace-freeSpace;

            map.put("path", file.getPath());
            map.put ("freeSpace", freeSpace / 1024/1024/1024 + "G"); // Свободное пространство
            map.put ("usableSpace", usableSpace / 1024/1024/1024 + "G"); // Доступное пространство
            map.put ("totalSpace", totalSpace / 1024/1024/1024 + "G"); // общее пространство
            map.put ("проценты", DECIMALFORMAT.format (((double) usableSpace / (double) totalSpace) * 100) + "%"); // общее пространство

            list.add(map);
        }

        return list;
    }
    public static void printinfo() {
        File[] roots = File.listRoots();
        FileSystemView fsv = FileSystemView.getFileSystemView();

        for (File root : roots) {
            try {

                BasicFileAttributeView basicFileAttributeView = Files.getFileAttributeView(root.toPath(), BasicFileAttributeView.class);

                BasicFileAttributes attributes = basicFileAttributeView.readAttributes();

                System.out.println("File Key: " + attributes.fileKey());
                System.out.println("Is Regular File: " + attributes.isRegularFile());
                System.out.println("Is Other: " + attributes.isOther());
                System.out.println("Is SymbolicLink: " + attributes.isSymbolicLink());
                System.out.println("Is Directory: " + attributes.isDirectory());

                System.out.println("Drive Name: " + root);
                System.out.println("Description: " + fsv.getSystemTypeDescription(root));
                System.out.println("Is Drive: " + fsv.isDrive(root));
                System.out.println("Is File System: " + fsv.isFileSystem(root));
                System.out.println("Is File System Root: " + fsv.isFileSystemRoot(root));
                System.out.println("Is Floppy Drive: " + fsv.isFloppyDrive(root));
                System.out.println("Is Hidden File: " + fsv.isHiddenFile(root));
                System.out.println("Is Traversable: " + fsv.isTraversable(root));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public static void in_txt_file(String text) {

        try(FileWriter writer = new FileWriter("notes3.txt", false))
        {
            // запись всей строки
            writer.write(text);
            // запись по символам
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
    public static void out_delete_txt() {

        try(FileReader reader = new FileReader("notes3.txt"))
        {
            // читаем посимвольно
            int c;
            while((c=reader.read())!=-1){

                System.out.print((char)c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        delfile("notes3.txt");

    }
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
        delfile("temp.json");
    }
    public static void XmlWrite(String filename)
    {
        try {
            File file = new File(filename);
            JAXBContext context = JAXBContext.newInstance(dog.class);
            Marshaller marshaller = context.createMarshaller();
            dog dog2 = new dog(7, "dog_name");
            marshaller.marshal(dog2, file);

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }
    public static void XmlRead(String filename)
    {
    try {
        File file = new File(filename);
        JAXBContext context = JAXBContext.newInstance(dog.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        dog dog3 = (dog) unmarshaller.unmarshal(file);
        System.out.println(dog3);

    } catch (JAXBException ex) {
        ex.printStackTrace();
    }}
    public static void zipdo() throws IOException{
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        Path path = Paths.get("test.zip");
        URI uri = URI.create("jar:" + path.toUri());
        try (FileSystem fs = FileSystems.newFileSystem(uri, env))
        {
            Path nf = fs.getPath("new.txt");
            try (Writer writer = Files.newBufferedWriter(nf, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {
                writer.write("hello");
            }
        }
    }
    public static void delfile(String filename)
    {

        File file = new File(filename);
        if(file.delete()){
            System.out.println("Файл был удален из корневой папки проекта");
        }else System.out.println("Файл не был найден в корневой папке проекта");

    }

    public static void main(String[] args) throws Exception {

        System.out.println(getInfo());
        printinfo();
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        in_txt_file(string);
        out_delete_txt();
        String filen= "kek.json";
        writeJsonSimpleDemo(filen);
        user User1=new user("username",41231);
        js_write(User1);
        js_read_del();
        XmlWrite("xmlDogFile.xml");
        XmlRead("xmlDogFile.xml");
    }

}