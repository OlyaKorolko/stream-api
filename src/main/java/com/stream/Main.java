package com.stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) {
        try (Stream<String> stringStream = Files.lines(Paths.get(".\\\\src\\\\data\\\\"))) {
            List<Company> listOfCompanies = Processor.writeToList(stringStream);
            processRequest(listOfCompanies);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void printMenu() {
        System.out.println("1. Limit by employee number bounds && group by auditors");
        System.out.println("2. Find company by phone number pattern");
        System.out.println("3. Find companies by location");
        System.out.println("4. Find newest companies");
    }

    public static void processRequest(List<Company> listOfCompanies) {
        try (Scanner scan = new Scanner(System.in)) {
            while (true) {
                printMenu();
                switch (scan.nextInt()) {
                    case 1:
                        System.out.println("Enter the lower bound for employee number:");
                        int lowerNumOfEmployees = scan.nextInt();
                        System.out.println("Enter the upper bound for employee number:");
                        int upperNumOfEmployees = scan.nextInt();
                        Map<String, List<Company>> uniqueMap = Processor.filterByEmployeesGroupByAuditor(lowerNumOfEmployees,
                                upperNumOfEmployees, listOfCompanies);
                        System.out.println("Grouping by auditor.");
                        uniqueMap.forEach((key, value) -> System.out.println(key + " " + value));
                        break;
                    case 2:
                        System.out.println("1st company to have a 1-... phone number pattern:");
                        Company company = Processor.findByPhoneNumber(listOfCompanies);
                        System.out.println(company);
                        break;
                    case 3:
                        System.out.println("Enter the location to see if any of the companies are located there: ");
                        scan.nextLine();
                        String location = scan.nextLine();
                        if (Processor.findAnyCompanyByLocation(listOfCompanies, location)) {
                            System.out.println("They do!");
                        } else {
                            System.out.println("We couldnt find any, your turn!");
                        }
                        break;
                    case 4:
                        System.out.println("Lets find n newest companies. Enter n:");
                        int n = scan.nextInt();
                        List<Company> newestCompanies = Processor.findNewestNCompanies(listOfCompanies, n);
                        newestCompanies.forEach(System.out::println);
                        break;
                    default:
                        return;
                }
            }
        }
    }
}
