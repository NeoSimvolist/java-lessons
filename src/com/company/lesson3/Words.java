package com.company.lesson3;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Words {
    public static String[] exampleNotUniqueWords = {
            "Привет",
            "Мама",
            "Папа",
            "Баба",
            "Деда",
            "Дочь",
            "Сын",
            "Сын",
            "Дядя",
            "Тетя",
            "Дом",
            "Школа",
            "Сад",
            "Мама",
            "Папа",
            "Сад",
            "Сад",
            "Сад",
            "Мама",
            "Мама",
    };

    /**
     * Получает набор уникальных слов из массива неуникальных
     */
    public static Set<String> getUniqueWords(String[] notUniqueWords) {
        return getWordUsageStatistic(notUniqueWords).entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * Получает статистику повторного использования слов
     */
    public static HashMap<String, Integer> getWordUsageStatistic(String[] notUniqueWords) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        for (String word : notUniqueWords) {
            Integer count = hashMap.get(word);
            hashMap.put(word, (count == null) ? 1 : ++count);
        }
        return hashMap;
    }
}
