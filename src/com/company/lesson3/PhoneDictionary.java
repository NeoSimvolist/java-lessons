package com.company.lesson3;

import java.util.HashMap;
import java.util.HashSet;

public class PhoneDictionary {

    private final HashMap<String, HashSet<String>> rows = new HashMap<>();

    /**
     * Добавляет Фамилию и телефон в справочник
     */
    public void add(String surname, String phone) {
        HashSet<String> phones;
        phones = this.rows.get(surname);
        if (phones == null) phones = new HashSet<>();
        phones.add(phone);
        this.rows.put(surname, phones);
    }

    /**
     * Возвращает телефоны по фамилии
     */
    public HashSet<String> get(String surname) {
        return this.rows.get(surname);
    }
}
