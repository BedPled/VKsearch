package com.company;

import java.util.*;

public class InvertedIndex {
    char value;
    List<InvertedIndex> children;
    List<Integer> ids;

    public InvertedIndex(char value) {
        this.value = value;
    }

    public void insert(String data, Integer id) {
        if (data.length() == 0) {
            return;
        }
        if (ids == null) {
            ids = new ArrayList<>();
        }
        if (children == null) {
            children = new ArrayList<>();
        }

        char c = data.charAt(0);
        InvertedIndex child = findNodeByChar(c);
        if (child == null) {
            child = new InvertedIndex(c);
            child.ids = new ArrayList<>();
            children.add(child);
        }
        if(!child.ids.contains(id)) child.ids.add(id);
        child.insert(data.substring(1), id);
    }

    private InvertedIndex findNodeByChar(char c) {
        if (children != null) {
            for(InvertedIndex node: children) {
                if (node.value == c) {
                    return node;
                }
            }
        }
        return null;
    }

    InvertedIndex containSubString(String str) {
        str = str.toLowerCase(Locale.ROOT);
        InvertedIndex current = this;
        for (int i = 0; i < str.length(); i++) {
            current = current.findNodeByChar(str.charAt(i));
            if (current == null) {
                return null;
            }
        }
        return current;
    }
}










