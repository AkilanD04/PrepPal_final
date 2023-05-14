package com.example.preppal

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Search_more : AppCompatActivity() {
    private lateinit var out: TextView // Declaring a TextView variable 'out'

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    // Overriding the onCreate() function of the Activity class
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_more)
        supportActionBar?.hide()
        // Initializing 'out' with the TextView component with the id 'text_out'
        out = findViewById(R.id.text_out)
        // Setting the text of 'out' to an empty string
        out.text = " "
        val search_btn =
            findViewById<Button>(R.id.search_in) // Initializing a Button variable 'search_btn' with the component with the id 'search_in'
        val text_in =
            findViewById<EditText>(R.id.name_in) // Initializing an EditText variable 'text_in' with the component with the id 'name_in'
        search_btn.setOnClickListener {
            if (text_in.text.isNotEmpty()){
                if (text_in.text.toString().toIntOrNull() == null){
                    Toast.makeText(this, "Results for ${text_in.text}", Toast.LENGTH_SHORT).show()
                    out.text = "Fetching meals from API..."
                    // Calling the 'show' function with 'text_in' and the context 'this'
                    show(text_in, this)
                }else{
                    Toast.makeText(this, "Enter a valid name", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Enter a name to continue", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("meals_list", out.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        out.append(savedInstanceState.getString("meals_list"))
    }

    @SuppressLint("SetTextI18n")
    private fun show(text_in: EditText, context: Context) {
        out.text = " "
        val url_string = "https://www.themealdb.com/api/json/v1/1/search.php?s=${text_in.text}"
        Log.d("activity",url_string)
        val url = URL(url_string)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    con.inputStream.bufferedReader().use { it.readText() }
                }
                parseJSON(response)
            } catch (e: Exception) {
                Log.d("activity", "error")
                Toast.makeText(context, "Meal not found", Toast.LENGTH_SHORT).show()
                out.text = "Error fetching data..."
            } finally {
                con.disconnect()
            }
        }
    }

    fun parseJSON(response: String) {
        val json = JSONObject(response)
        val jsonArray: JSONArray = json.getJSONArray("meals")
        for (i in 0 until jsonArray.length()) {
            val meal: JSONObject = jsonArray[i] as JSONObject
            val mealId = meal["idMeal"] as String
            get_meal_with_id(mealId)
        }
    }

    private fun get_meal_with_id(mealId: String) {
        val url_string = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=${mealId}"
        val url = URL(url_string)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    con.inputStream.bufferedReader().use { it.readText() }
                }
                parseJSON_get_meals(response)
            } catch (e: Exception) {
                Log.d("activity", "error")
            } finally {
                con.disconnect()
            }
        }
    }

    private fun parseJSON_get_meals(response: String) {
        val json = JSONObject(response)
        val jsonArray: JSONArray = json.getJSONArray("meals")
        for (i in 0 until jsonArray.length()) {
            val meal: JSONObject = jsonArray[i] as JSONObject
            //Extract the values you need from the meal object and store them in variables
            val mealId = meal["idMeal"].toString()
            val mealName = meal["strMeal"].toString()
            val drinkAlternate = meal["strDrinkAlternate"].toString()
            val category = meal["strCategory"].toString()
            val area = meal["strArea"].toString()
            val instructions =
                meal["strInstructions"].toString()
            val mealThumb = meal["strMealThumb"].toString()
            val tags = meal["strTags"].toString()
            val youtubeLink = meal["strYoutube"].toString()
            val ingredient1 =
                if (meal.has("strIngredient1")) {
                    val text = meal["strIngredient1"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient2 =
                if (meal.has("strIngredient2")) {
                    val text = meal["strIngredient2"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient3 =
                if (meal.has("strIngredient3")) {
                    val text = meal["strIngredient3"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient4 =
                if (meal.has("strIngredient4")) {
                    val text = meal["strIngredient4"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient5 =
                if (meal.has("strIngredient4")) {
                    val text = meal["strIngredient4"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient6 =
                if (meal.has("strIngredient4")) {
                    val text = meal["strIngredient4"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient7 =
                if (meal.has("strIngredient5")) {
                    val text = meal["strIngredient5"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient8 =
                if (meal.has("strIngredient8")) {
                    val text = meal["strIngredient8"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient9 =
                if (meal.has("strIngredient9")) {
                    val text = meal["strIngredient9"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient10 =
                if (meal.has("strIngredient10")) {
                    val text = meal["strIngredient10"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient11 =
                if (meal.has("strIngredient11")) {
                    val text = meal["strIngredient11"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient12 =
                if (meal.has("strIngredient12")) {
                    val text = meal["strIngredient12"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient13 =
                if (meal.has("strIngredient13")) {
                    val text = meal["strIngredient13"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient14 =
                if (meal.has("strIngredient14")) {
                    val text = meal["strIngredient14"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient15 =
                if (meal.has("strIngredient15")) {
                    val text = meal["strIngredient15"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient16 =
                if (meal.has("strIngredient16")) {
                    val text = meal["strIngredient16"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient17 =
                if (meal.has("strIngredient17")) {
                    val text = meal["strIngredient17"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient18 =
                if (meal.has("strIngredient18")) {
                    val text = meal["strIngredient18"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient19 =
                if (meal.has("strIngredient19")) {
                    val text = meal["strIngredient19"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val ingredient20 =
                if (meal.has("strIngredient20")) {
                    val text = meal["strIngredient20"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure1 =
                if (meal.has("strMeasure1")) {
                    val text = meal["strMeasure1"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure2 =
                if (meal.has("strMeasure2")) {
                    val text = meal["strMeasure2"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure3 =
                if (meal.has("strMeasure3")) {
                    val text = meal["strMeasure3"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure4 =
                if (meal.has("strMeasure4")) {
                    val text = meal["strMeasure4"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure5 =
                if (meal.has("strMeasure5")) {
                    val text = meal["strMeasure5"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure6 =
                if (meal.has("strMeasure6")) {
                    val text = meal["strMeasure6"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure7 =
                if (meal.has("strMeasure7")) {
                    val text = meal["strMeasure7"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure8 =
                if (meal.has("strMeasure8")) {
                    val text = meal["strMeasure8"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure9 =
                if (meal.has("strMeasure9")) {
                    val text = meal["strMeasure9"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure10 =
                if (meal.has("strMeasure10")) {
                    val text = meal["strMeasure10"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure11 =
                if (meal.has("strMeasure11")) {
                    val text = meal["strMeasure11"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure12 =
                if (meal.has("strMeasure12")) {
                    val text = meal["strMeasure12"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure13 =
                if (meal.has("strMeasure13")) {
                    val text = meal["strMeasure13"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure14 =
                if (meal.has("strMeasure14")) {
                    val text = meal["strMeasure14"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure15 =
                if (meal.has("strMeasure15")) {
                    val text = meal["strMeasure15"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure16 =
                if (meal.has("strMeasure16")) {
                    val text = meal["strMeasure16"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure17 =
                if (meal.has("strMeasure17")) {
                    val text = meal["strMeasure17"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure18 =
                if (meal.has("strMeasure18")) {
                    val text = meal["strMeasure18"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure19 =
                if (meal.has("strMeasure19")) {
                    val text = meal["strMeasure19"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            val measure20 =
                if (meal.has("strMeasure20")) {
                    val text = meal["strMeasure20"].toString()
                    if (text.trim().isEmpty()) {
                        "null"
                    } else {
                        text
                    }
                } else {
                    "null"
                }
            runOnUiThread {
                out.append(
                    "\nmeal name: $mealName \nDrinkAlternate: $drinkAlternate \nCategory: $category " +
                            "\narea: $area \ninstructions: $instructions \nmealThumb: $mealThumb \ntags: $tags " +
                            "\nyoutubeLink: $youtubeLink \ningredient1: $ingredient1 \ningredient2: $ingredient2 " +
                            "\ningredient3: $ingredient3 \ningredient4: $ingredient4 \ningredient5: $ingredient5 " +
                            "\ningredient6: $ingredient6 \ningredient7: $ingredient7 \ningredient8: $ingredient8 " +
                            "\ningredient9: $ingredient9 \ningredient10: $ingredient10 \ningredient11: $ingredient11 " +
                            "\ningredient12: $ingredient12 \ningredient13: $ingredient13 \ningredient14: $ingredient14 " +
                            "\ningredient15: $ingredient15 \ningredient16: $ingredient16 \ningredient17: $ingredient17 " +
                            "\ningredient18: $ingredient18 \ningredient19: $ingredient19 \ningredient20: $ingredient20 " +
                            "\nmeasure1: $measure1 \nmeasure2: $measure2 \nmeasure3: $measure3 \nmeasure4: $measure4" +
                            "\nmeasure5: $measure5 \nmeasure6: $measure6 \nmeasure7: $measure7 \nmeasure8: $measure8" +
                            "\nmeasure9: $measure9 \nmeasure10: $measure10 \nmeasure11: $measure11 \nmeasure12: $measure12" +
                            "\nmeasure13: $measure13 \nmeasure14: $measure14 \nmeasure15: $measure15 \nmeasure16: $measure16" +
                            "\nmeasure17: $measure17 \nmeasure18: $measure18 \nmeasure19: $measure19 \nmeasure20: $measure20"
                )
                out.append("\n\n")
            }
        }
    }
}