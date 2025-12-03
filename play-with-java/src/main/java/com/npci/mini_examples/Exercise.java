package com.npci.mini_examples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Exercise {
    public static void main(String[] args) {

        String[] csvReport = {
                "Name,Department,Salary",
                "Alice,Engineering,70000",
                "Bob,Marketing,50000",
                "Charlie,Engineering,80000",
                "David,HR,60000",
                "Eve,Marketing,55000"
        };

        // Req: Find the total salary of each department
        // Note : department names are not known in advance

        // way-1 : through traditional for loop
        Map<String, Double> departmentSalaryMap = new java.util.HashMap<>();
        for (String csvLine : csvReport) {
            // skip header line
            if (csvLine.startsWith("Name")) {
                continue;
            }
            String[] parts = csvLine.split(",");
            String department = parts[1];
            String salaryStr = parts[2];
            double salary = Double.parseDouble(salaryStr);

//            if(!departmentSalaryMap.containsKey(department)){
//                departmentSalaryMap.put(department, salary);
//            }else{
//                double existingSalary = departmentSalaryMap.get(department);
//                double newSalary = existingSalary + salary;
//                departmentSalaryMap.put(department, newSalary);
//            }

            departmentSalaryMap.put(department,
                    departmentSalaryMap.getOrDefault(department, 0.0) + salary);
        }
        System.out.println("Department Salary Map (Traditional Loop): " + departmentSalaryMap);


        // way-2 : through Streams API

        // a. collect
        Arrays.stream(csvReport)
                .skip(1)
                .map(csvLine -> csvLine.split(","))
                .collect(HashMap::new, (m, parts) -> {
                    String department = parts[1];
                    double salary = Double.parseDouble(parts[2]);
                    m.put(department,
                            departmentSalaryMap.getOrDefault(department, 0.0) + salary);

                }, HashMap::putAll);

        // b. groupingBy
        Map<String, Double> departmentSalaryMap2 = Arrays.stream(csvReport)
                .skip(1)
                .map(csvLine -> csvLine.split(","))
                .collect(java.util.stream.Collectors.groupingBy(
                        parts -> parts[1],
                        java.util.stream.Collectors.summingDouble(parts -> Double.parseDouble(parts[2]))
                ));
        System.out.println("Department Salary Map (Streams API): " + departmentSalaryMap2);

    }
}
