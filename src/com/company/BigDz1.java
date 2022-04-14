package com.company;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Дисковые инструменты
 *
 * @author gu
 * @date 22 декабря 2017 г.
 */
public class BigDz1 {

    private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.##");

    /**
     * Получить информацию об использовании диска
     *
     * @return
     */
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
        File file = new File("notes3.txt");
        if(file.delete()){
            System.out.println("notes3.txt файл был удален из корневой папки проекта");
        }else System.out.println("Файл notes3.txt не был найден в корневой папке проекта");

    }
    public void jsonwrite() {
        try {
            // create a writer
            BufferedWriter writer = Files.newBufferedWriter(Paths.get("customer.json"));

            // create customer object
            JSONObject customer = new JSONObject();
            customer.put("id", 1);
            customer.put("name", "John Doe");
            customer.put("email", "john.doe@example.com");
            customer.put("age", 32);

            // create address object
            JSONObject address = new JSONObject();
            address.put("street", "155 Middleville Road");
            address.put("city", "New York");
            address.put("state", "New York");
            address.put("zipCode", 10045);

            // add address to customer
            customer.put("address", address);

            // add customer payment methods
            JSONArray pm = new JSONArray();
            pm.addAll(Arrays.asList("PayPal", "Stripe"));
            customer.put("paymentMethods", pm);

            // create projects
            JSONArray projects = new JSONArray();

            // create 1st project
            JSONObject p1 = new JSONObject();
            p1.put("title", "Business Website");
            p1.put("budget", 4500);

            // create 2nd project
            JSONObject p2 = new JSONObject();
            p2.put("title", "Sales Dashboard");
            p2.put("budget", 8500);

            // add projects
            projects.addAll(Arrays.asList(p1, p2));

            // add projects to customer
            customer.put("projects", projects);

            // write JSON to file
            Jsoner.serialize(customer, writer);


            //close the writer
            writer.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println(getInfo());
        printinfo();
        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        in_txt_file(string);
        out_delete_txt();



    }

}