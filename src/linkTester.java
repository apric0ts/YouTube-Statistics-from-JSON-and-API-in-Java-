/*
Names: Austin, Shane, Alec
Date: 6/3/22
Assignment: Independent Study Project
Description: Pulling from YouTube API
API KEY: AIzaSyC1xfopW7werwQQa0qOP-C-JJv7VJ5YLG0
Random Stuff:
*/


//package com.readjson;// import JSON->Java interpreter


//JSON intepreter
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

//reading off website
import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;

//accessing files
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//scanner and arraylist (normal java stuff)
import java.util.Scanner;
import java.util.ArrayList;

public class linkTester {
    public static void main(String[] args) throws InterruptedException {
        //ask user for input

        Scanner input = new Scanner(System.in);
        System.out.print("Enter a search term: ");
        String searchQuery = input.nextLine();
        String ytLink = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=";
        String apiKey = "AIzaSyC1xfopW7werwQQa0qOP-C-JJv7VJ5YLG0";

        //concatenates search query+%20 with the youtube link and API key
        for (int i = 0; i < searchQuery.length(); i++) {
            if (searchQuery.charAt(i) == ' ') {
                searchQuery = searchQuery.substring(0, i) + "%20" + searchQuery.substring(i + 1); //replaces space with %20
            }
        }
        System.out.print("This is the link for the JSON file where we see the search results: ");
        String searchLink = ytLink + searchQuery + "&key=" + apiKey;
        System.out.println(searchLink); //generates the API link with the search query

        //----------------------------------------------------------------------------------------------




        //take the JSON file and get the video IDs
        int vidNum = 0;
        DownloadWebPage(searchLink,vidNum);
        Thread.sleep(2000);
        String jsonFile = "/Users/apric/IdeaProjects/CSA Project/videos"+vidNum+".json"; //turns the JSON file into String
        try {
            ArrayList<Link> links = new ArrayList<Link>();

            String contents = new String((Files.readAllBytes(Paths.get(jsonFile)))); //reads the file
            JSONObject jsonObject = new JSONObject(contents); //creates an object with the json file
            JSONArray itemsArray = jsonObject.getJSONArray("items"); //creates items array
            JSONObject idObject = itemsArray.getJSONObject(0); //creates idObject from id array
            Object id = idObject.get("id"); //object id is created

            System.out.println(itemsArray.getJSONObject(0).toString());
            String idString = id.toString();
            idString = getVidId(idString); //static method at the bottom of this class in order to cut the videoId out of the toString object
            System.out.println(idString);



            //display the video that is found by searching


            //ask the user if they would like to add this video to the playlist
            System.out.print("Would you like to add this song to the playlist? (yes/no): ");
            String accept = input.nextLine();

            if(accept.equals("yes".toLowerCase())) {
                //add this to the links
                links.add(new Link (idString));
                System.out.println(links.get(0).getVideoID());
            } else if (accept.equals("no".toLowerCase())){
                //do something when they say no
            }


            //next we have to add each ytID into the statistics link thing
            //this should give all json files for each individual video
            for (int i = 0; i < links.size(); i++) {
                System.out.printf("This is the link for the JSON file where we pull statistics from: " +
                        "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&id=%s&key=%s", links.get(i).getVideoID(), apiKey);
                //gives JSON file for individual videos
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static String getVidId(String old) {
        String newS = "";
        for (int i =0; i<old.length()-8; i++) {
            if((old.substring(i,i+7)).equals("videoId")) {
                newS = old.substring(i+10);
            }
        }
        return newS.substring(0,newS.length()-2);
    }

    public static void DownloadWebPage(String webpage, int vNum)
    {
        try {

            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter("videos"+vNum+".json"));

            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }

            readr.close();
            writer.close();
            System.out.println("Successfully Downloaded.");
        }

        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Malformed URL Exception raised");
        }
        catch (IOException ie) {
            System.out.println("IOException raised");
        }
    }

}