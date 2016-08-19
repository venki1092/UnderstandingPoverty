package com.example.venki.up.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bala on 8/5/16.
 */
public class Singleton {
    private static Singleton instance;
    public static List<Post> postList;
    private Singleton(){}
    static
    {
        postList = new ArrayList<>();
    }
    public static Singleton getInstance()
    {
        if(instance == null) {
            synchronized (Singleton.class) {
                if (instance == null)
                    return new Singleton();
            }
        }
        return instance;
    }
}
