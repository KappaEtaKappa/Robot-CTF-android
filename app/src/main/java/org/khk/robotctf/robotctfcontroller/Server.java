package org.khk.robotctf.robotctfcontroller;

import android.util.Log;

import com.parse.ParseObject;
import com.parse.*;

import java.util.List;

/**
 * Created by lucasmullens on 4/14/15.
 */
public class Server {
    private static String intToName(int i) {
        switch(i) {
            case 0:
                return "a";
            case 1:
                return "b";
            case 2:
                return "c";
            default:
                return "d";
        }
    }
    /**
     *
     * @param player The player number (0-3)
     */
    public static void die(final int player) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> res, ParseException e) {
                ParseObject game;
                if (e == null) {
                    game = res.get(0);
                    game.put(intToName(player), 0);
                    //code if you want to declare a winner if other team is killed
//                    if (game.get("a") == 0 && game.get("b") == 0) { //if team A is dead
//                        win(1);
//                    }
//                    if (game.get("c") == 0 && game.get("d") == 0) { //if team B is dead
//                        win(0);
//                    }
                } else {
                    Log.d("Parse", "Error: " + e.getMessage());
                }
            }
        });
    }
    public static void overYourBase(final int player) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> res, ParseException e) {
                ParseObject game;
                if (e == null) {
                    game = res.get(0);
                    if (game.get(intToName(player)) == 2) {
                        if (player <= 1) {
                            win(0);
                        } else {
                            win(1);
                        }
                    }
                    game.saveInBackground();
                } else {
                    Log.d("Parse", "Error: " + e.getMessage());
                }
            }
        });
    }
    public static void overTheirBase(final int player) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> res, ParseException e) {
                ParseObject game;
                if (e == null) {
                    game = res.get(0);
                    //get teammate
                    int teammate = 0;
                    if (player == 0)
                        teammate =  1;
                    if (player == 1)
                        teammate =  0;
                    if (player == 2)
                        teammate =  3;
                    if (player == 3)
                        teammate =  2;
                    //if teammate doesn't have the flag, grab it
                    if (game.get(intToName(teammate)) != 2)
                        game.put(intToName(player), 2);
                    game.saveInBackground();
                } else {
                    Log.d("Parse", "Error: " + e.getMessage());
                }
            }
        });
    }
    public static void start() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> res, ParseException e) {
                ParseObject game;
                if (e == null) {
                    game = res.get(0);
                    game.put("mode", 1); //1 is playing
                    game.put("a", 1); //1 is alive
                    game.put("b", 1);
                    game.put("c", 1);
                    game.put("d", 1);
                    game.saveInBackground();
                } else {
                    Log.d("Parse", "Error: " + e.getMessage());
                }
            }
        });
    }
    public static void win(final int winningTeam) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Game");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> res, ParseException e) {
                ParseObject game;
                if (e == null) {
                    game = res.get(0);
                    game.put("mode", 0); //1 is playing
                    game.put("winner", winningTeam); //1 is playing
                    game.saveInBackground();
                } else {
                    Log.d("Parse", "Error: " + e.getMessage());
                }
            }
        });
    }
}
