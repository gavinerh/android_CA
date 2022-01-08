package com.example.myapplication;

import java.util.List;
import java.util.Random;

public class Randomize {
    private static Random rand = new Random();

    public static String[] randomizeArray(List<String> list){
        list = doubleList(list);
        String[] arr = new String[list.size()];
        for(int i=0; i<arr.length; i++){
            int position = randomNumGenerator(list.size());
            arr[i] = list.get(position);
            list.remove(position);
        }
        return arr;
    }

    private static int randomNumGenerator(int i){
        if(i == 0){
            return 0;
        }
        return rand.nextInt(i) + 0;
    }

    private static List<String> doubleList(List<String> list){
        int size = list.size();
        for(int i=0; i<size; i++){
            list.add(list.get(i));
        }
        return list;
    }
}
