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
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;

public class linkTester {
    public static void main(String[] args) throws InterruptedException {
        String ytLink = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=";
        String apiKey = "AIzaSyC1xfopW7werwQQa0qOP-C-JJv7VJ5YLG0"; //youtube API key (please do not share this, it is private :) )
        int vidNum = 0;
        int vidNum2 = 0; //these "vidNum" variables are for making sure that there are different files for each JSON file per vid.
        //ask user for input
        boolean cont = true; //this changes when the user says "no" to adding more videos to playlist.
        ArrayList<Link> links = new ArrayList<Link>(); //holds the links for all the videos in the playlist

        while (cont) {
            Scanner input = new Scanner(System.in);
            System.out.println("\nWelcome to the YouTube Java searcher.");
            System.out.print("Enter a search term: ");
            String searchQuery = input.nextLine();
            System.out.println("\n");



            //we want to put this whole thing into a while loop, and when the condition is false (user does not want to add any more songs, break out of while loop)
            //concatenates search query+%20 with the youtube link and API key
            for (int i = 0; i < searchQuery.length(); i++) {
                if (searchQuery.charAt(i) == ' ') {
                    searchQuery = searchQuery.substring(0, i) + "%20" + searchQuery.substring(i + 1); //replaces space with %20
                }
            }

            System.out.print("This is the link for the JSON file where we see the search results: ");
            String searchLink = ytLink + searchQuery + "&key=" + apiKey; //takes to page with video ID
            System.out.println(searchLink); //generates the API link with the search query

            //----------------------------------------------------------------------------------------------




            //take the JSON file and get the video IDs

            DownloadWebPage(searchLink,vidNum);
            Thread.sleep(2000);
            String jsonFile = "/Users/apric/IdeaProjects/CSA Project/videos"+vidNum+".json"; //turns the JSON file into String

            try {


                String contents = new String((Files.readAllBytes(Paths.get(jsonFile)))); //reads the file
                JSONObject jsonObject = new JSONObject(contents); //creates an object with the json file
                JSONArray itemsArray = jsonObject.getJSONArray("items"); //creates items array
                JSONObject idObject = itemsArray.getJSONObject(0); //creates idObject from id array

                Object id = idObject.get("id"); //object id is created
                String idString = id.toString();
                idString = getVidId(idString); //static method at the bottom of this class in order to cut the videoId out of the toString object

                Object title = idObject.get("snippet"); //object id is created
                String titleString = title.toString();
                titleString = getTitle(titleString);
                //title printing is farther down



                links.add(new Link (idString)); //adds the link to the ArrayList which will be referenced in the yes/no and the statisticslink creator

                //next we have to add each ytID into the statistics link thing
                //this should give all json files for each individual video


                String statisticsLink = "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&id="+links.get(vidNum2).getVideoID()+"&key="+apiKey; //creates link with statistics
                System.out.println("This is the link for the JSON file where we pull statistics from: " + statisticsLink);
                //gives JSON file with statistics for videos

                //downloads as file
                DownloadWebPage2(statisticsLink,vidNum2);
                Thread.sleep(2000);

                String jsonFileForStats = "/Users/apric/IdeaProjects/CSA Project/videos"+vidNum2+"stats.json"; //turns the JSON file into String
                String contents2 = new String((Files.readAllBytes(Paths.get(jsonFileForStats)))); //reads the file
                JSONObject jsonObjectForStats = new JSONObject(contents2); //creates object with the json file
                JSONArray itemsArrayForStats = jsonObjectForStats.getJSONArray("items"); //creates items array
                JSONObject statsObject = itemsArrayForStats.getJSONObject(0); //creates statsObject from items array
                Object stats = statsObject.get("statistics"); //object stats is created



                ////display the video that is found by searching
                String statistics = stats.toString();
                System.out.println("\n\nThe title of the video we found is: " + titleString); //prints title
                System.out.println("Here are some statistics on this video: " + statistics); //prints statistics


                //ask the user if they would like to add this video to the playlist
                System.out.print("\n\nWould you like to add this song to the playlist? (yes/no): ");
                String accept = input.nextLine();

                if(accept.equals("yes".toLowerCase())) {
                    //add this to the links
                } else if (accept.equals("no".toLowerCase())){
                    links.remove(links.size()-1);
                }

                //asks the user if they would like to add more videos
                System.out.print("\n\nWould you like to add more songs to the playlist? (yes/no): ");
                String contAsk = input.nextLine();
                if (contAsk.equals("yes".toLowerCase())) {
                    cont = true;
                } else {
                    cont = false;
                }


                //changes the vid numbers, this should be at the end of the program
                System.out.println("There are " + (links.size()) + " videos in the playlist currently.");
                vidNum++;
                vidNum2++;

            /*
            for (int i=0; i<links.size(); i++) {
                //System.out.println("ID #"+i+": " + links.get(i).getVideoID());
            }
             */

            } catch (IOException e) {
                e.printStackTrace();
            }
        }





    }
    public static String getVidId(String old) { //extracts vidID from the ID object
        String newS = "";
        for (int i =0; i<old.length()-8; i++) {
            if((old.substring(i,i+7)).equals("videoId")) {
                newS = old.substring(i+10);
            }
        }
        return newS.substring(0,newS.length()-2);
    }

    public static String getTitle(String old) { //extracts title from the title object
        String newS = "";
        int j = 0;
        for (int i =0; i<old.length()-8; i++) {
            if((old.substring(i,i+5)).equals("title")) {
                newS = old.substring(i+8);
                j=i+7;
            }
        }


        for (int h=0; h<newS.length()-9; h++) {
            if((newS.substring(h,h+5).equals("thumb"))) {
                newS = newS.substring(0, (h-3));
            }
        }
        return newS;


    }


    //download webpage for searching
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


    //downloading webpages for individual statistics
    public static void DownloadWebPage2(String webpage, int vNum)
    {
        try {

            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter("videos"+vNum+"stats.json"));

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