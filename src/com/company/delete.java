package com.company;

import java.io.File;

public class delete {
    public static void delfile(String filename)
    {

        File file = new File(filename);
        if(file.delete()){
            System.out.println("Файл был удален из корневой папки проекта");
        }else System.out.println("Файл не был найден в корневой папке проекта");

    }
}
