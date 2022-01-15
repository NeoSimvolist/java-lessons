package com.company.lesson3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        wordsExample();
        phoneDictionaryExample();
    }

    public static void wordsExample() {
        System.out.println(System.lineSeparator());
        System.out.println("List of unique words:");
        for (String uniqueWord : Words.getUniqueWords(Words.exampleNotUniqueWords)) {
            System.out.println(uniqueWord);
        }

        System.out.println(System.lineSeparator());
        System.out.println("Word usage statistics:");
        for (Map.Entry<String, Integer> entry : Words.getWordUsageStatistic(Words.exampleNotUniqueWords).entrySet()) {
            System.out.printf("%s - %d%n", entry.getKey(), entry.getValue());
        }
    }

    public static void phoneDictionaryExample() {
        System.out.println(System.lineSeparator());
        System.out.println("PhoneDictionary:");

        PhoneDictionary phoneDictionary = new PhoneDictionary();
        phoneDictionary.add("Никифоров", "+71111119007");
        phoneDictionary.add("Никифоров", "+71111119007");
        phoneDictionary.add("Никифоров", "+71111119001");
        phoneDictionary.add("Куликова", "+71111119000");

        String surnameWithMultiplePhoneNumbers = "Никифоров";
        String surnameWithOnePhoneNumber = "Куликова";
        String nonExistentSurname = "Бабаев";
        String[] tests = {surnameWithMultiplePhoneNumbers, surnameWithOnePhoneNumber, nonExistentSurname};
        for (String test : tests) {
            System.out.printf("Find by %s: %s%n", test, phoneDictionary.get(test));
        }
    }
}
