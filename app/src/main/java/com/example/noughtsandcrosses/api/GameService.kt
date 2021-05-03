package com.example.noughtsandcrosses.api

import android.app.Application
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.noughtsandcrosses.App
import com.example.noughtsandcrosses.R
import com.example.noughtsandcrosses.api.GameService.context
import com.example.noughtsandcrosses.api.data.Game
import com.example.noughtsandcrosses.api.data.GameState
import com.google.gson.Gson
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONObject


typealias GameServiceCallback = (state: Game?, errorCode:Int? ) -> Unit

object GameService {

    //Connected to the App class in which I have supplied the ApplicationContext
    private val context = App.context

    //Queue for performing requests
    //Takes parameter method
    private val requestQue: RequestQueue = Volley.newRequestQueue(context)

    // An API endpoint is a point at which an application program interface
    // (API)--the code that allows two software programs to communicate with each other--
    // connects with the software program. APIs work by sending requests for information
    // from a web application or web server and receiving a response.
    // These are different endpoints for protocol, domain and base_path respectively.
    // Each refers to a different APIEndpoint in connection to the webserver of the app,
    // Each has its on URL


    private enum class APIEndpoints(val url: String) {
        CREATE_GAME(
            //Finn ut hva '"%1s%2s%3s".format' vil si
            "%1s%2s%3s".format(
                context.getString(R.string.protocol),   //protocol = using https as stated in strings.xml
                context.getString(R.string.domain),     //domain = using the generic-game-service.herokuapp.com domain, as stated in strings.xml
                context.getString(R.string.base_path)   //base_path = using path to game
            )
        )
    }


    //Function to create game
    fun createGame(playerId: String, state: GameState, callback: GameServiceCallback) {

        //An API endpoint is basically a fancy word for a URL of a server or service
        val url = APIEndpoints.CREATE_GAME.url

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state", JSONArray(state))

        val request = object : JsonObjectRequest(
            Request.Method.POST, url, requestData,
            {
                // Success game created. "val game" is this game instance.
                // Will probably use Gson().fromJson in later functions.. https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                println("$game")
                callback(game, null)
            }, {
                // Error creating new game.
                callback(null, it.networkResponse.statusCode)
            }) {

            // MutableMap
            // A modifiable collection that holds pairs of objects (keys and values)
            // and supports efficiently retrieving the value corresponding to each key.
            // Map keys are unique; the map holds only one value for each key.
            //
            // Parameters:
            // K - the type of map keys. The map is invariant in its key type.
            // V - the type of map values. The mutable map is invariant in its value type.
            override fun getHeaders(): MutableMap<String, String> {
                // HashMap is an implementation of the interface MutableMap.
                val headers = HashMap<String, String>()
                // Type of map key = json
                headers["Content-Type"] = "application/json"
                // Type of map value = game service key
                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }
        }

        requestQue.add(request)
    }

    fun joinGame(playerId: String, gameId: String, callback: GameServiceCallback) {

        val url = "https://generic-game-service.herokuapp.com/Game/$gameId/Join"

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("gameId", gameId)
        println(requestData)


        val request = object : JsonObjectRequest(
            Request.Method.POST, url, requestData,
            {
                // Success game joined. "val game" is this game instance.
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                callback(game, null)
                // To see if game instance is returned
                println("$game")
            }, {
                // Error joining game
                callback(null, it.networkResponse.statusCode)
            }) {


            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String, String>()

                headers["Content-Type"] = "application/json"

                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }

        }
        requestQue.add(request)
    }

    fun updateGame(gameId: String, gameState: GameState, callback: GameServiceCallback) {
        val url = "https://generic-game-service.herokuapp.com/Game/$gameId/Update"

        val requestData = JSONObject()
        requestData.put("gameId", gameId)
        requestData.put("gameState", gameState)

        val request = object : JsonObjectRequest(
            Request.Method.POST, url, requestData,
            {
                // Success game updated. "val game" is this game instance.
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                callback(game, null)
                println("$game")
            }, {
                callback(null, it.networkResponse.statusCode)
            }) {


            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String, String>()

                headers["Content-Type"] = "application/json"

                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }

        }
        requestQue.add(request)

    }

    fun pollGame(gameId: String, callback: GameServiceCallback) {

        val url = "https://generic-game-service.herokuapp.com/Game/$gameId/Poll"

        val requestData = JSONObject()
        requestData.put("gameId", gameId)

        val request = object : JsonObjectRequest(
            Request.Method.GET, url, requestData,
            {
                val game = Gson().fromJson(it.toString(0), Game::class.java)
                callback(game, null)
            }, {
                callback(null, it.networkResponse.statusCode)
            }) {


            override fun getHeaders(): MutableMap<String, String> {

                val headers = HashMap<String, String>()

                headers["Content-Type"] = "application/json"

                headers["Game-Service-Key"] = context.getString(R.string.game_service_key)
                return headers
            }

        }
        requestQue.add(request)
    }

}

