/*
Names: Austin, Shane, Alec
Date: 6/3/22
Assignment: Independent Study Project
Description: Pulling from YouTube API
API KEY: AIzaSyC1xfopW7werwQQa0qOP-C-JJv7VJ5YLG0

Random Stuff:

https://youtube.googleapis.com/youtube/v3/search?part=snippet&q=the%20weeknd&key=[YOUR_API_KEY]
*/
import org.json.JSONArray;

//package com.readjson;// import JSON->Java interpreter

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Scanner;
import java.util.ArrayList;

public class linkTester {
    public static void main(String[] args) {
        //ask user for input
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a search term: ");
        String searchQuery = input.nextLine();
        String ytLink = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&q=";
        // String singleVidYTLink = "https://www.googleapis.com/youtube/v3/videos?part={part}&id={video_id}&key={self.api_key}";
        String apiKey = "AIzaSyC1xfopW7werwQQa0qOP-C-JJv7VJ5YLG0";

        //concatenates search query+%20 with the youtube link and API key
        for (int i = 0; i < searchQuery.length(); i++) {
            if (searchQuery.charAt(i) == ' ') {
                searchQuery = searchQuery.substring(0, i) + "%20" + searchQuery.substring(i + 1); //replaces space with %20
            }
        }
        System.out.println(ytLink + searchQuery + "&key=" + apiKey); //generates the API link with the search query

        //----------------------------------------------------------------------------------------------
        String jsonFile = "/Users/apric/IdeaProjects/CSA Project/src/videos.json";
        //next we want to take the JSON file and get the video IDs and then use "singleVidYTLink" to get individual video statistics, so we need to instantiate a whole bcnh of video objects
        try {
            String contents = new String((Files.readAllBytes(Paths.get(jsonFile))));
            JSONObject o = new JSONObject(contents);
            JSONArray items = o.getJSONArray("items");
            ArrayList<Link> links = new ArrayList<Link>();
            for (int i = 0; i < items.length(); i++) { //goes thru JSON array and adds each videoID to a new arraylist, items array is in json file
                //for each ID in the JSON  file, add to the array of yt IDs
                String str; //json file goes here
                JSONObject obj = new JSONObject(contents);
                String videoId = obj.getString("videoId");//should grab videoId
                String videoTitle = obj.getString("title");
                String channelID = obj.getString("channelId");

                links.add(new Link(videoId, videoTitle, channelID));//adds the links into the arraylist
            }
            //next we have to add each ytID into the statistics link thing
            //this should give all json files for each individual video
            for (int i = 0; i < links.size(); i++) {
                System.out.printf("https://youtube.googleapis.com/youtube/v3/videos?part=statistics&id=%s&key=\"%s\"", links.get(i), apiKey); //gives JSON file for individual videos

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}