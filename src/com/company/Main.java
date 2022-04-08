package com.company;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Main {

    private static final int sizeOfTop = 5; // кол-во записей на выдачу
    private static ArrayList<Request> forwardIndex = new ArrayList<>(); // список запросов (прямой индекс)
    private static InvertedIndex invertedIndex = new InvertedIndex(' ');

    public static void parsIndex (String fileName /*, List<Integer> prisesOfRequests*/) { // построение прямого и обратного индекса

        System.out.println("Parsing from: " + fileName);

        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            int counter = 0;
            while (line != null) {

                if(line.length() == 0) {
                    line = reader.readLine();
                    continue;
                }

                line = line.toLowerCase(Locale.ROOT);
                forwardIndex.add(new Request(line, counter /* prisesOfRequests.get(counter) */)); // Передаём строку как вес запроса

                String[] subStr;
                subStr = line.split(" "); // Разделения строки

                for (String s : subStr) {
                    invertedIndex.insert(s, counter);
                }

                line = reader.readLine();
                counter++;
            }
            System.out.println("Add " + counter + " requests");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> listIntersection (List<Integer> one, List<Integer> two) { // пересечение двух списков
        if (one == null && two == null) {
            return new ArrayList<Integer>();
        }

        List<Integer> out = new ArrayList<>(one);
        out.retainAll(two);
        return out;
    }

    public static void search (String str) {
        String[] subStr;
        InvertedIndex requests;
        InvertedIndex requestsHelp;
        subStr = str.split(" "); // Разделения строки str с помощью метода split()

        if (subStr.length >= 1) {
            requests = invertedIndex.containSubString(subStr[0]);
        } else {
            return;
        }

        for (int i = 1; i < subStr.length; ++i) { // пересекаем полученные запросы (довольне не эффективно :( )
            requestsHelp = invertedIndex.containSubString(subStr[i]);
            if (requestsHelp != null) {
                requests.ids = listIntersection(requests.ids, requestsHelp.ids);
            }
        }

        System.out.println("Requests: |" + str + "|");
        // ранжируем запросы по цене запроса
        if(requests != null) {
            ranging(requests.ids);
        } else {
            System.out.println("Sorry, but nothing :(");
        }

    }

    public static void ranging (List<Integer> requests) {
        if (requests.size() == 0 ) {
            System.out.println("Sorry, but nothing :(");
            return;
        }
        for (int i = 0; i < (Math.min((requests.size() - 1), sizeOfTop)) ; ++i) {
            int maxPos = i;
            for (int j = i + 1; j < requests.size(); ++j) {
                if (forwardIndex.get(requests.get(j)).price > forwardIndex.get(requests.get(maxPos)).price) {
                    maxPos = j;
                }
            }

            Integer saveValue = requests.get(maxPos);
            requests.remove(maxPos);
            requests.add(maxPos, requests.get(i));
            requests.remove(i);
            requests.add(i, saveValue);
        }


        for (int i = 0; i < Math.min((requests.size()), sizeOfTop); ++i) {
            System.out.println((i+1) + ": " + forwardIndex.get(requests.get(i)).text);
            System.out.println("price of request: " + forwardIndex.get(requests.get(i)).price);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println();
        parsIndex("requests.txt");
        System.out.println();

        search("if");


    }
}



