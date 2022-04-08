package com.company;

public class Request { // информация о запросе (прямой индекс)

    String text; // текст запроса
    int price; // вес запроса | рейтинг | цена товара и тп

    public Request(String text, int price) {
        this.text = text;
        this.price = price;
    }

    public Request(String text) {
        this.text = text;
    }
}
