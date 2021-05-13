package com.example.noughtsandcrosses.api

import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.noughtsandcrosses.App
import com.example.noughtsandcrosses.R
import com.example.noughtsandcrosses.api.data.Game
import com.example.noughtsandcrosses.api.data.GameState
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject


typealias GameServiceCallback = (state: Game?, errorCode:Int? ) -> Unit

object GameService {

    private val context = App.context

    private val requestQue: RequestQueue = Volley.newRequestQueue(context)

    private enum class APIEndpoints(val url: String) {
        CREATE_GAME(
            "%1s%2s%3s".format(
                context.getString(R.string.protocol),   //protocol = using https as stated in strings.xml
                context.getString(R.string.domain),     //domain = using the generic-game-service.herokuapp.com domain, as stated in strings.xml
                context.getString(R.string.base_path)   //base_path = using path to game
            )
        )
    }


    fun createGame(playerId: String, state: GameState, callback: GameServiceCallback) {

        val url = APIEndpoints.CREATE_GAME.url

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("state", JSONArray(state))

        val request = object : JsonObjectRequest(
            Method.POST, url, requestData,
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

    fun joinGame(playerId: String, gameId: String, callback: GameServiceCallback) {

        val url = "https://generic-game-service.herokuapp.com/Game/$gameId/Join"

        val requestData = JSONObject()
        requestData.put("player", playerId)
        requestData.put("gameId", gameId)
        println(requestData)


        val request = object : JsonObjectRequest(
            Method.POST, url, requestData,
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

    fun updateGame(gameId: String, state: GameState, callback: GameServiceCallback) {
        val url = "https://generic-game-service.herokuapp.com/Game/$gameId/Update"

        val requestData = JSONObject()
        requestData.put("gameId", gameId)
        requestData.put("state", JSONArray(state))

        val request = object : JsonObjectRequest(
            Method.POST, url, requestData,
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

    fun pollGame(gameId: String, callback: GameServiceCallback) {

        val url = "https://generic-game-service.herokuapp.com/Game/$gameId/Poll"

        val request = object : JsonObjectRequest(
            Method.GET, url, null,
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

