package com.stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) {
        try (Stream<String> stringStream = Files.lines(Paths.get(".\\\\src\\\\data\\\\"))) {
            List<Company> listOfCompanies = stringStream.map(line -> {
                String[] x = line.split(",");
                return new Company(x[0], x[1], LocalDate.parse(x[2]), x[3], LocalDate.parse(x[4]),
                        Integer.parseInt(x[5]), x[6], x[7], x[8], x[9], x[10], x[11]);
            }).collect(Collectors.toList());

            Map<String, List<Company>> uniqueMap = listOfCompanies.stream().distinct()
                    .filter(c -> c.getWebsiteAddress().length() <= 10)
                    .collect(Collectors.groupingBy(Company::getAuditor, Collectors.toList()));

            uniqueMap.forEach((key, value) -> System.out.println(key + " " + value));

            List<Double> numbers = Arrays.asList(85.324, 4.345436, 21.54645, -324.1, -3214325.4, 0.343, 100.7777);

            Double result = numbers.stream()
                    .reduce((num1, num2) -> (num1 % num2) + Math.random() * 10)
                    .orElse(0.0);

            System.out.println("Result: " + result);
            Double goldenNumber = numbers.stream()
                    .filter(num -> Math.sqrt(Math.abs(num)) > 10)
                    .findFirst()
                    .orElse(-1.0);

            System.out.println("Golden number: " + goldenNumber);
            System.out.println("All of the elements match: " + numbers.stream().allMatch(goldenNumber::equals));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
