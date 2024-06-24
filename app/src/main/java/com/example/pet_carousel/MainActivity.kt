package com.example.pet_carousel

import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.OnGestureListener
import android.view.MotionEvent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    private lateinit var dogImageView: ImageView
    private lateinit var gestureDetector: GestureDetector
    private lateinit var currentImageUrl: String
    private lateinit var catImageView: ImageView
    private lateinit var catApiService: CatApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dogImageView = findViewById(R.id.petImageView)
        catImageView = findViewById(R.id.petImageView)
        gestureDetector = GestureDetector(this, this)

        // Fetch and display a random dog image on app start
        fetchRandomImage()
    }

    private fun fetchRandomImage() {
        val service = RetrofitInstance.retrofitdog.create(DogApiService::class.java)
        catApiService = RetrofitInstance.retrofitcat.create(CatApiService::class.java)
        val call = service.getRandomDogImage()
        val randomInt = Random.nextInt(0,10)

        if (randomInt > 5)
            call.enqueue(object : Callback<DogResponse> {
                override fun onResponse(call: Call<DogResponse>, response: Response<DogResponse>) {
                    if (response.isSuccessful) {
                        val dogResponse = response.body()
                        if (dogResponse != null) {
                            currentImageUrl = dogResponse.message
                            Glide.with(this@MainActivity).load(currentImageUrl).into(dogImageView)
                        }
                    } else {
                        // Handle unsuccessful response
                    }
                }
                override fun onFailure(call: Call<DogResponse>, t: Throwable) {
                    // Handle failure
                }
            })
        else
            catApiService.getRandomCatImage().enqueue(object : Callback<List<CatImage>> {
                override fun onResponse(call: Call<List<CatImage>>, response: Response<List<CatImage>>) {
                    if (response.isSuccessful) {
                        response.body()?.firstOrNull()?.let { catImage ->
                            Glide.with(this@MainActivity)
                                .load(catImage.url)
                                .into(catImageView)
                        }
                    }
                }

                override fun onFailure(call: Call<List<CatImage>>, t: Throwable) {
                    // Handle failure
                }
            })

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {
        // Handle down motion event
        return true
    }

    override fun onFling(
        downEvent: MotionEvent?,
        moveEvent: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        val diffY = downEvent?.let { moveEvent.y.minus(it.y) } ?: 0f
        if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
            if (diffY > 0) {
                // Swipe down
                fetchRandomImage()
            }
        }
        return true
    }

    // Implement other required methods of GestureDetector.OnGestureListener
    override fun onLongPress(e: MotionEvent) {
        // Handle long press gesture
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        // Handle scroll gesture
        return true
    }

    override fun onShowPress(e: MotionEvent) {
        // Handle show press gesture
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        // Handle single tap up gesture
        return true
    }

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }
}



