package com.example.myapplication;

import java.util.List;
import java.util.Random;

public class Randomize {
    private static Random rand = new Random();

    public static Integer[] randomizeArray(List<Integer> list){
        list = doubleList(list);
        Integer[] arr = new Integer[list.size()];
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

    private static List<Integer> doubleList(List<Integer> list){
        int size = list.size();
        for(int i=0; i<size; i++){
            list.add(list.get(i));
        }
        return list;
    }
}
