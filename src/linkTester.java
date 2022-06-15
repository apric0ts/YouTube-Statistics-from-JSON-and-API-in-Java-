
/*
Names: Austin, Shane, Alec
Date: 6/3/22
Assignment: Independent Study Project
Description: Pulling from YouTube API
API KEY: AIzaSyC1xfopW7werwQQa0qOP-C-JJv7VJ5YLG0
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
        import java.util.Collections;
        import java.util.Scanner;
        import java.util.ArrayList;

public class linkTester {
    public static void main(String[] args) throws InterruptedException {
        String ytLink = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&q=";
        String apiKey = "AIzaSyDTtCy6El1CxMRVhaNjZ0Js1013ZeK1Lk0"; //youtube API key (please do not share this, it is private :) ), if the API key is not here, this code won't work
        int vidNum = 0;
        int vidNum2 = 0; //these "vidNum" variables are for making sure that there are different files for each JSON file per vid.
        //ask user for input
        boolean cont = true; //this changes when the user says "no" to adding more videos to playlist.
        ArrayList<Link> links = new ArrayList<Link>(); //holds the links for all the videos in the playlist
        ArrayList<Video> videos = new ArrayList<Video>(); //holds the videos for all the videos in the playlist

        System.out.println("\nWelcome to the Java YouTube Data Analyzer.");
        while (cont) {
            Scanner input = new Scanner(System.in);

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

            DownloadWebPage(searchLink);
            Thread.sleep(2000);
            String jsonFile = "/Users/darkg/IdeaProjects/CSA Project/videos.json"; //turns the JSON file into String

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
                for(int i = 0; i < titleString.length()-5; i++){
                    if(titleString.substring(i,i+5).equalsIgnoreCase("&quot"))
                        titleString= (titleString.substring(0,i)+titleString.substring(i+6));
                }
                for(int i = 0; i < titleString.length()-5; i++){
                    if(titleString.substring(i,i+5).equalsIgnoreCase("&#39;"))
                        titleString= (titleString.substring(0,i+1)+"'"+titleString.substring(i+6));
                }
                //title printing is farther down



                links.add(new Link (idString,titleString)); //adds the link to the ArrayList which will be referenced in the yes/no and the statisticslink creator

                //next we have to add each ytID into the statistics link thing
                //this should give all json files for each individual video


                String statisticsLink = "https://youtube.googleapis.com/youtube/v3/videos?part=statistics&id="+links.get(vidNum2).getVideoID()+"&key="+apiKey; //creates link with statistics
                System.out.println("This is the link for the JSON file where we pull statistics from: " + statisticsLink);

                //gives JSON file with statistics for videos

                //downloads as file
                DownloadWebPage2(statisticsLink);

                String jsonFileForStats = "/Users/darkg/IdeaProjects/CSA Project/videostats.json"; //turns the JSON file into String
                String contents2 = new String((Files.readAllBytes(Paths.get(jsonFileForStats)))); //reads the file
                JSONObject jsonObjectForStats = new JSONObject(contents2); //creates object with the json file
                JSONArray itemsArrayForStats = jsonObjectForStats.getJSONArray("items"); //creates items array
                JSONObject statsObject = itemsArrayForStats.getJSONObject(0); //creates statsObject from items array
                Object stats = statsObject.get("statistics"); //object stats is created



                ////display the video that is found by searching
                String statistics = stats.toString();
                int likes = Integer.parseInt(getLikes(statistics)); //turns the string numbers into integers
                int views = Integer.parseInt(getViews(statistics));
                int comments = Integer.parseInt(getComments(statistics));

                videos.add(new Video(likes, views, comments, idString, titleString));


               // System.out.println("\n\nThe title of the video we found is: " + titleString); //prints title
                System.out.println("\n\nHere are some statistics on this video: \n" + videos.get(vidNum).toString()); //prints statistics


                //ask the user if they would like to add this video to the playlist
                System.out.print("\n\nWould you like to add this video to the database? (yes/no): ");
                String accept = input.nextLine();

                if(accept.equals("yes".toLowerCase())) {
                    //add this to the links
                } else if (accept.equals("no".toLowerCase())){
                    links.remove(links.size()-1);
                    videos.remove(videos.size()-1);
                    vidNum--;
                    vidNum2--;
                }

                //asks the user if they would like to add more videos
                System.out.print("\nSearch for more videos? (yes/no): ");
                String contAsk = input.nextLine();
                if (contAsk.equals("yes".toLowerCase())) {
                    cont = true;
                } else {
                    cont = false;
                }


                //changes the vid numbers, this should be at the end of the program
                System.out.println("There are " + (links.size()) + " videos in the database currently.\n");
                vidNum++;
                vidNum2++;



            } catch (IOException e) {
                e.printStackTrace();
            }




        }
        //WHILE LOOP ENDS, DISPLAYING ALL VIDEOS NOW:
        System.out.println("\n\nFinal Database: " + vidNum + " videos in the database.\n");
        for (int i = 0; i<videos.size();i++) {
            System.out.println(videos.get(i).toString() +"\n");
        }

        //SORT BY KEY STATISTICS
        System.out.print("\nWould you like to sort the videos in the database? (yes/no): ");
        Scanner input = new Scanner(System.in);
        String userInput = input.nextLine();
        if(userInput.equalsIgnoreCase("yes")){
            while(true) {
                System.out.print("Sort by (Views/Likes/Comments/Exit): ");
                String sortBy = input.nextLine();

                if (sortBy.equalsIgnoreCase("views")) {
                    Collections.sort(videos, Video.vidViewComparator);
                    System.out.print("\n");
                    for (int i = 0; i<videos.size();i++) {
                        System.out.println("#" + (i+1) + " views:" );
                        System.out.println(videos.get(i).toString() +"\n");
                    }

                } else if (sortBy.equalsIgnoreCase("likes")) {
                    Collections.sort(videos, Video.vidLikeComparator);
                    System.out.print("\n");
                    for (int i = 0; i<videos.size();i++) {
                        System.out.println("#" + (i+1) + " likes:" );
                        System.out.println(videos.get(i).toString() +"\n");
                    }

                } else if (sortBy.equalsIgnoreCase("comments")) {
                    Collections.sort(videos, Video.vidCommComparator);
                    System.out.print("\n");
                    for (int i = 0; i<videos.size();i++) {
                        System.out.println("#" + (i+1) + " comments:" );
                        System.out.println(videos.get(i).toString() +"\n");
                    }

                } else if (sortBy.equalsIgnoreCase("exit")) {
                    System.out.println("Alright, have a good day!");
                    System.exit(0);
                } else {
                    System.out.println("Not a valid sorting option, try again");
                }
            }
        }else if(userInput.equalsIgnoreCase("no")){
            System.out.println("Alright, have a good day!");
            System.exit(0);
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
        for (int i =0; i<old.length()-8; i++) {
            if((old.substring(i,i+5)).equals("title")) {
                newS = old.substring(i+8);
            }
        }


        for (int h=0; h<newS.length()-9; h++) {
            if((newS.substring(h,h+5).equals("thumb"))) {
                newS = newS.substring(0, (h-3));
            }
        }
        return newS;
    }
    public static String getLikes(String old){ //extracts likes from string
        String newS = "";
        for(int i = 0; i < old.length(); i++){
            if(old.charAt(i) == 'v'){
                newS = old.substring(14,i-3);
                break;
            }
        }
        return newS;
    }
    public static String getViews(String old){ //extracts views from string
        int firstIndex = 0;
        int lastIndex = 0;
        for(int i = 0; i < old.length(); i++){
            if(old.charAt(i) == 'v'){
                firstIndex = i+12;
                break;
            }
        }
        for(int i = 0; i < old.length(); i++){
            if(old.charAt(i) == 'f'){
                lastIndex = i-3;
                break;
            }
        }
        return old.substring(firstIndex,lastIndex);
    }
    public static String getComments(String old){ //extracts comments from string
        String newS = "";
        for(int i = 0; i < old.length(); i++){
            if(old.charAt(i) == 't'){
                newS = old.substring(i+4,old.length()-2);
            }
        }
        return newS;
    }


    //download webpage for searching
    public static void DownloadWebPage(String webpage) //downloads webpage into file (search json file)
    {
        try {

            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter("videos"+".json"));

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
    public static void DownloadWebPage2(String webpage) //downloads webpage into file (statistics json file)
    {
        try {

            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            BufferedWriter writer =
                    new BufferedWriter(new FileWriter("video"+"stats.json"));

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
