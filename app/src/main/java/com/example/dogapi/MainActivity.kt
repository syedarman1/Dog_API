package com.example.dogapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {
    private lateinit var petImageURL: String
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        imageView = findViewById<ImageView>(R.id.imageView)

        getNextImage(button, imageView)

        Log.d("petImageURL", "Pet image URL")
    }

    private fun getNextImage(button: Button, imageView: ImageView) {
        button.setOnClickListener {
            getDogImageURL()
        }
    }

    private fun getDogImageURL() {
        val client = AsyncHttpClient()
        client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers?, json: JsonHttpResponseHandler.JSON) {
                petImageURL = json.jsonObject.getString("message")


                Glide.with(this@MainActivity)
                    .load(petImageURL)
                    .fitCenter()
                    .into(imageView)

                Log.d("Dog", "Response successful: $json")
                Log.d("petImageURL", "Pet image URL set to: $petImageURL")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String?,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse ?: "Error response is null")
            }
        }]
    }
}
