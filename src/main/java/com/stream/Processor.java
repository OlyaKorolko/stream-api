package com.stream;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Processor {
    public static List<Company> writeToList(Stream<String> inputLines) {
        return inputLines.map(line -> {
            String[] x = line.split(",");
            return new Company(x[0], x[1], LocalDate.parse(x[2]), x[3], LocalDate.parse(x[4]),
                    Integer.parseInt(x[5]), x[6], x[7], x[8], x[9], x[10], x[11]);
        }).collect(Collectors.toList());
    }

    public static Map<String, List<Company>> filterByEmployeesGroupByAuditor(int lowerNumOfEmployees, int upperNumOfEmployees,
                                                                      List<Company> listOfCompanies) {
        return listOfCompanies.stream()
                .distinct()
                .filter(c -> c.getNumberOfEmployees() >= lowerNumOfEmployees
                        && c.getNumberOfEmployees() <= upperNumOfEmployees)
                .collect(Collectors.groupingBy(Company::getAuditor, Collectors.toList()));

    }

    public static Company findByPhoneNumber(List<Company> listOfCompanies) {
        return listOfCompanies.stream()
                .filter(c -> c.getPhoneNumber().matches("1-\\d{1,4}-\\d{1,4}-\\d{1,4}"))
                .findFirst()
                .orElse(null);
    }

    public static boolean findAnyCompanyByLocation(List<Company> listOfCompanies, String location) {
        return listOfCompanies.stream().anyMatch(c -> c.getAddress().contains(location));
    }

    public static List<Company> findNewestNCompanies(List<Company> listOfCompanies, int n) {
        return listOfCompanies.stream()
                .sorted(Comparator.comparing(Company::getDateOfFoundation).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }
}
