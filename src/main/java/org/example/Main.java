package org.example;

import com.google.common.io.Resources;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        readAndLoadConfig1();
        System.out.println("==========");
        readAndLoadConfig2();
        System.out.println("==========");
        readAndLoadConfig3();
        System.out.println("==========");
        readAndLoadConfig4();
    }

    private static void readAndLoadConfig4() throws IOException {
        URL resource = Resources.getResource("database.config");
        String content = Resources.toString(resource, StandardCharsets.UTF_8);
        System.out.println(content);
        System.out.println("------------");
        List<String> configs = Resources.readLines(resource, StandardCharsets.UTF_8);
        configs.forEach(System.out::println);
        System.out.println("------------");

        Properties properties = new Properties();
        configs.stream()
                .map(config -> config.split("="))
                .forEach(configArr -> properties.setProperty(configArr[0], configArr[1]));

        System.out.println(properties.toString());
    }

    private static void readAndLoadConfig3() throws URISyntaxException, IOException {
        URL systemResource = ClassLoader.getSystemResource("database.config");
        List<String> configs = Files.readAllLines(Path.of(systemResource.toURI()));
        configs.forEach(System.out::println);
        configs.stream()
                .map(config -> config.split("=")[1])
                .forEach(System.out::println);

        Properties properties = new Properties();
        configs.stream()
                .map(config -> config.split("="))
                .forEach(configArr -> properties.setProperty(configArr[0], configArr[1]));
        System.out.println(properties.toString());
    }

    private static void readAndLoadConfig2() throws IOException {
        Properties properties = new Properties();
        InputStream databaseConfigStream = ClassLoader.getSystemResourceAsStream("database.config");
        properties.load(databaseConfigStream);
        System.out.println(properties.getProperty("jdbc-url"));
        System.out.println(properties.getProperty("db-username"));
        System.out.println(properties.getProperty("db-password"));
    }

    private static void readAndLoadConfig1() throws IOException {
        Properties properties = new Properties();
        String filename = "src/main/resources/database.config";
        try (FileInputStream fis = new FileInputStream(filename)) {
            properties.load(fis);
        }

        System.out.println(properties.getProperty("jdbc-url"));
        System.out.println(properties.getProperty("db-username"));
        System.out.println(properties.getProperty("db-password"));
    }
}