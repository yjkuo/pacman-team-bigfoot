package edu.rice.comp504.controller;

import com.google.gson.Gson;
import edu.rice.comp504.adapter.DispatchAdapter;

import java.util.concurrent.ConcurrentHashMap;

import static spark.Spark.*;
import static spark.Spark.post;

public class PacmanController {
    /**
     * Main entry point into the program.
     * @param args Program arguments normally passed to main on the command line
     */
    public static void main(String[] args) {
        staticFiles.location("/public");
        port(getHerokuAssignedPort());
        Gson gson = new Gson();
        ConcurrentHashMap<String, DispatchAdapter> map = new ConcurrentHashMap<>();
        DispatchAdapter da = new DispatchAdapter();

        get("/initialize", (request, response) -> {
            return gson.toJson(da.initializeGame(1, Integer.parseInt(request.queryParams("numberOfGhosts")), Integer.parseInt(request.queryParams("lives"))));
        });

        post("/update", (request, response) -> {
            return gson.toJson(da.updateStore(Integer.parseInt(request.queryParams("direction"))));
        });

//        get("/clear", (request, response) -> {
//            //TODO
//            return gson.toJson("Clear the pacman world");
//        });
//
//
//        post("/setGameParameters", (request, response) -> {
//            //TODO
//            return gson.toJson("Set the game parameters");
//        });
    }

    /**
     * Get the Heroku assigned port number.
     * @return  Heroku assigned port number
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
