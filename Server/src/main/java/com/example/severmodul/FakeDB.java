package com.example.severmodul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FakeDB {
    Map<String,String> userDB = new HashMap<>();
    Map<String,ArrayList> userSubscribed = new HashMap<>();

    public FakeDB() {
        ArrayList<String> topics = new ArrayList<>();
        topics.add("testuser");
        this.userDB.put("testuser","password");
        userSubscribed.put("testuser",topics);

        this.userDB.put("admin","admin");
        topics.add("topicAdmin");
        userSubscribed.put("admin",topics);

        this.userDB.put("potato69","potato69");
        topics.add("potatos blog");
        userSubscribed.put("testuser",topics);
    }
    int login(String user, String password){
        if(!userDB.containsKey(user)){
            return -1;
        }
        if(userDB.get(user).equals(password)){
            return 1;
        }
        return 0;
    }

    ArrayList getTopics(String user){
        return userSubscribed.get(user);
    }


}