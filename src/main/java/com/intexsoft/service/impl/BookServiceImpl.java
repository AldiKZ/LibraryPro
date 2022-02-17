package com.intexsoft.service.impl;


import com.intexsoft.entity.Book;
import com.intexsoft.service.BookService;

import java.io.*;
import java.util.*;

public class BookServiceImpl implements BookService {

    @Override
    public List<Book> find(Map<String, String> paramsMap) {
        List<Book> books = new ArrayList<>();
        File dir = new File("C:\\Users\\user\\Documents\\LibraryPro project\\src\\resource\\Libraries");
        File[] libs = dir.listFiles();
        if (libs != null) {
            books  = readAllFiles(paramsMap, libs);
            printResult(books);
        }
        return books;
    }

    @Override
    public void order(Map<String, String> paramsMap){
        File file  = searchFile(paramsMap);
        modifyFile(file, paramsMap);
    }

    @Override
    public void reBook(Map<String, String> paramsMap) {
        File file = searchFile(paramsMap);
        reFile(file, paramsMap);
    }

    private void reFile(File file, Map<String, String> paramsMap) {

        if (file.getAbsolutePath().contains(".csv")) {
            reCSVFile(file, paramsMap);
        }
        if (file.getAbsolutePath().contains(".properties")) {
            rePropFile(file, paramsMap);
        }
    }

    private void rePropFile(File file, Map<String, String> paramsMap) {
        try {
            String result =  fileToString(file);
            if (result.contains("Issued")) {
                deleteStringInPropFile(file, paramsMap);
            }
            else {
                System.out.println("ALREADYRETURNED");
//                Date date = new Date();
//                String newString = result+"Issued "+date+"\nIssuedto "+ paramsMap.get("user");
//                PrintWriter writer = new PrintWriter(file.getAbsolutePath());
//                writer.append(newString);
//                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("NOT FOUND");
        }
    }

    private void deleteStringInPropFile(File file, Map<String, String> paramsMap) {
        try {
            String result = deleteInFile(file);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result);
            writer.flush();
//            while (reader.ready() ) {
//                String readLine = reader.readLine();
//                if (readLine.contains("Issued") || readLine.contains("Issuedto")){
//                    StringBuffer modifiedString = new StringBuffer();
//                    modifiedString.replace(0,  readLine.length()-1, "");
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String deleteInFile(File file) {
        String input = null;
        Scanner sc = null;
        try {
            sc = new Scanner(new File(file.getAbsolutePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            sb.append(sc.nextLine()+"\n");
        }
        return sb.toString();
    }

    private void reCSVFile(File file, Map<String, String> paramsMap) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer result = new StringBuffer();
            while (reader.ready()) {
                String str = reader.readLine();
                if (str.contains(paramsMap.get("id"))){
                    String[] line = str.split(",");
                    for (int i = 0; i < 3; i++) {
                        result.append(line[i]+",");
                    }
                    result.append(",");
                }
                else {
                    result.append(str+"\n");
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(result.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File searchFile(Map<String, String> paramsMap) {
        File dir = new File("C:\\Users\\user\\Documents\\LibraryPro project\\src\\resource\\Libraries");
        List<File> files = new ArrayList<>();
        File[] libs = dir.listFiles();
        if (libs != null) {
            for (File lib : libs) {
                files.addAll(Arrays.asList(lib.listFiles()));
            }
        }
        for (File file : files) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                while (reader.ready()) {
                    String readLine = reader.readLine();
                    if (readLine.contains(paramsMap.get("id"))) {
                        return file;
                    }
                    else {

                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void modifyFile(File file, Map<String, String> paramsMap) {
        if (file.getAbsolutePath().contains(".csv")){
            modifyCSVFile(file, paramsMap);
        }
        else if (file.getAbsolutePath().contains(".properties")){
            modifyPropFile(file, paramsMap);
        }
    }

    private void modifyCSVFile(File file, Map<String, String> paramsMap) {
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuffer bf = new StringBuffer();
            while (reader.ready()){
                String readLine = reader.readLine();
                if (readLine.contains(paramsMap.get("id")) && readLine.contains(",,")) {
                    bf.append(modifiedStringInCSVFile(readLine, paramsMap, file));
                    System.out.println("OKAY, user "+ paramsMap.get("user"));
                }
                else {
                    bf.append(readLine+"\n");
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(bf.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println("NOT FOUND");
        }
    }

    private void modifyPropFile(File file, Map<String, String> paramsMap) {
        try {
            String result =  fileToString(file);
            if (result.contains("Issued")) {
                System.out.println("ORDERED");
            }
            else {
                Date date = new Date();
                String newString = result+"Issued "+date+"\nIssuedto "+ paramsMap.get("user");
                PrintWriter writer = new PrintWriter(file.getAbsolutePath());
                writer.append(newString);
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("NOT FOUND");
        }
    }

    private String fileToString(File file) throws FileNotFoundException {
        String input = null;
        Scanner scanner = new Scanner(new File(file.getAbsolutePath()));
        StringBuffer sb = new StringBuffer();
        while (scanner.hasNextLine()){
            input = scanner.nextLine()+"\n";
            sb.append(input);
        }
        return  sb.toString();
    }

    private String modifiedStringInCSVFile(String readLine, Map<String, String> paramsMap, File file) {
        Date date = new Date();
        String newString = readLine .substring(0, readLine.length()-1) + date + "," + paramsMap.get("user")+"\n";
        String result = newString;
        System.out.println("Result is "+result);
        return result;
    }

    private void printResult(List<Book> books) {
        if (books.size() == 0) {
            System.out.println("NOT FOUND");
        } else {
            for (Book book : books) {
                if (book.getDate() == null) {
                    System.out.println("FOUND " + book);
                } else {
                    System.out.println("FOUNDMISSING " + book);
                }
            }
        }
    }

    private List<Book> readAllFiles(Map<String, String> paramsMap, File[] libs) {

        List<Book> books = new ArrayList<>();
        List<File> files = new ArrayList<>();

        for (File file : libs) {
            files.addAll(Arrays.asList(file.listFiles()));
        }
        for (File file : files) {
            if (file.getName().contains(".csv")) {
                books.addAll(readCsvFile(paramsMap, file));
            } else if (file.getName().contains(".properties")) {
              books.addAll(readPropFile(paramsMap, file));
            }
        }
        return books;
    }

    public List<Book> readCsvFile(Map<String, String> params, File file) {
        List<Book> books = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String[] skeys = reader.readLine().split(",");
                for (String key : skeys) {
                    keys.add(key);
                }
                Book book = createBook(keys,file);
                checkBook(params, books, book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }

    public List<Book> readPropFile(Map<String, String> params, File file) {
        List<Book> books = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                String[] line = reader.readLine().split(" ");
                keys.add(line[1]);
            }
            Book book = createBook(keys, file);
            checkBook(params, books, book);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return books;
        }
    }

    private void checkBook(Map<String, String> params, List<Book> books, Book book) {
        Boolean containsAuthor = true;
        Boolean containsName = true;
        boolean containsId = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("author") && !book.getAuthor().equals(value)) {
                containsAuthor = false;
            }
            if (key.equals("name") && !book.getName().equals(value)) {
                containsName = false;
            }
            if (key.equals("id") && !book.getId().equals(value)) {
                containsId = false;
            }
        }

        if (containsAuthor && containsName) {
            books.add(book);
        }
    }

    private Book createBook(List<String> keys, File file) {
        Book book = new Book();
        book.setLibrary(file.getAbsolutePath());
        if (keys.size() <6){  // У CSV файла до 5  элементов, а у Prop. их от 6
            book.setId(Long.parseLong(keys.get(0)));
            book.setAuthor(keys.get(1));
            book.setName(keys.get(2));
            if (keys.size() > 3) {
                book.setDate(keys.get(3));
                book.setUser(keys.get(4));
            }
        }
        else {
            book.setId(Long.parseLong(keys.get(1)));
            book.setAuthor(keys.get(3));
            book.setName(keys.get(5));
            if (keys.size() > 6) {
                book.setDate(keys.get(7));
                book.setUser(keys.get(9));
            }
        }
        return book;
    }



}
