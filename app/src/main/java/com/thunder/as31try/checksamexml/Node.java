package com.thunder.as31try.checksamexml;

import android.net.ConnectivityManager;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;

class Node {
    /**
     * 节点名称
     */
    String name;

    ArrayList<Integer> lines = new ArrayList<>();

    boolean isDuplicated;

    public Node(String name) {
        this.name = name;
    }

    public void addNewLine(int line) {
        if (!lines.contains(line)) {
            lines.add(line);
        }
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public ArrayList<Integer> getLines() {
        return lines;
    }

    public void setLines(ArrayList<Integer> lines) {
        this.lines = lines;
    }

    public boolean isDuplicated() {
        return isDuplicated;
    }

    public void setDuplicated(boolean duplicated) {
        isDuplicated = duplicated;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", isDuplicated=" + isDuplicated +
                ", lines=" + lines +
                '}';
    }
}
