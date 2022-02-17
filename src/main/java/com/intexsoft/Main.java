package com.intexsoft;

import com.intexsoft.service.impl.BookServiceImpl;
import com.intexsoft.util.StringUtils;

import java.util.Map;
import java.util.Scanner;

public class Main {

    final static BookServiceImpl bookService = new BookServiceImpl();

    public static void main(String[] args) {
        System.out.println("Что вы хотите сделать? \n 1. FIND \n 2. ORDER \n 3. RETURN \n 4. EXIT");
        Scanner scanner = new Scanner(System.in);
        String chosenMenu = scanner.nextLine();
        switch (chosenMenu) {
            case "1":
                processFindMenu();
                break;
            case "2":
                processOrderMenu();
                break;
            case "3":
                processReturnMenu();
                break;
            case "4":
                scanner.close();
            default:
                scanner.close();
        }
    }

    private static void processReturnMenu() {
        Scanner scanUserInput = new Scanner(System.in);
        System.out.println("id=Id");
        String userInput = scanUserInput.nextLine();
        Map<String, String> paramsMap = StringUtils.obtainParamsMap(userInput);
        bookService.reBook(paramsMap);
        scanUserInput.close();
    }

    private static void processOrderMenu() {
        Scanner scanUserInput = new Scanner(System.in);
        System.out.println("id=Id user=UserName");
        String userInput = scanUserInput.nextLine();
        Map<String, String> paramsMap = StringUtils.obtainParamsMap(userInput);
        bookService.order(paramsMap);
        scanUserInput.close();
    }

    private static void processFindMenu() {
        Scanner scanUserInput = new Scanner(System.in);
        System.out.println("author=Author name=Name");
        String userInput = scanUserInput.nextLine();
        Map<String, String> paramsMap = StringUtils.obtainParamsMap(userInput);
        bookService.find(paramsMap);
        scanUserInput.close();
    }
}
