package com.example.preppal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class Ingredient_search : AppCompatActivity() {
    private lateinit var out: TextView
    private lateinit var add_db: Button
    private var arr = arrayOf<String>()
    private var db_visible = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_search)
        supportActionBar?.hide()

        val ingredient_in = findViewById<EditText>(R.id.name_in)
        val retrieve = findViewById<Button>(R.id.add)
        add_db = findViewById(R.id.add_db)
        if (db_visible == false) {
            add_db.visibility = View.INVISIBLE
        } else {
            add_db.visibility = View.VISIBLE
        }
        out = findViewById(R.id.text_out)
        out.text = " "

        add_db.setOnClickListener {
            if (arr.isEmpty()) {
                Toast.makeText(
                    this,
                    "already added search for a new ingredient",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                db_add(this)
            }
        }


        retrieve.setOnClickListener {
            if (ingredient_in.text.isEmpty()) {
                Toast.makeText(this, "Enter an ingredient to continue", Toast.LENGTH_SHORT).show()
            } else {
                if (ingredient_in.text.toString().toIntOrNull() == null){
                    Toast.makeText(this, "Results for ${ingredient_in.text}", Toast.LENGTH_SHORT).show()
                    out.text = "Fetching meals from API..."
                    fetch_details(ingredient_in, this)
                }
                else{
                    Toast.makeText(this, "Enter a valid ingredient to continue", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("meals", out.text.toString())
        Log.d("activity", "Size: ${arr.size}")
        outState.putStringArray("meals_list", arr)
        outState.putBoolean("btn_visible", db_visible)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        out.append(savedInstanceState.getString("meals"))
        val new_arr = savedInstanceState.getStringArray("meals_list")
        if (new_arr != null) {
            arr = new_arr
        }
        Log.d("activity", "size ${arr.size}")
        db_visible = savedInstanceState.getBoolean("btn_visible")
        if (db_visible == false) {
            add_db.visibility = View.INVISIBLE
        } else {
            add_db.visibility = View.VISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetch_details(ingredient_in: EditText, context: Context) {
        out.text = " "
        val url_string = "https://themealdb.com/api/json/v1/1/filter.php?i=${ingredient_in.text}"
        Log.d("activity",url_string)
        val url = URL(url_string)
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    con.inputStream.bufferedReader().use { it.readText() }
                }
                parseJSON_with_id(response)
            } catch (e: Exception) {
                Log.d("activity", "error")
                Toast.makeText(context, "Ingredient not found", Toast.LENGTH_SHORT).show()
                out.text = "Error fetching data..."
            } finally {
                con.disconnect()
            }
        }

    }

    fun parseJSON_with_id(response: String) {
        val json = JSONObject(response)
        val jsonArray: JSONArray = json.getJSONArray("meals")
        for (i in 0 until jsonArray.length()) {
            val meal: JSONObject = jsonArray[i] as JSONObject
            val mealid = meal["idMeal"]
            get_meal_with_id(mealid as String)
        }
    }

    fun parseJSON_get_meals(response: String) {
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
                arr += mealId
                arr += mealName
                arr += drinkAlternate
                arr += category
                arr += area
                arr += instructions
                arr += mealThumb
                arr += tags
                arr += youtubeLink
                arr += ingredient1
                arr += ingredient2
                arr += ingredient3
                arr += ingredient4
                arr += ingredient5
                arr += ingredient6
                arr += ingredient7
                arr += ingredient8
                arr += ingredient9
                arr += ingredient10
                arr += ingredient11
                arr += ingredient12
                arr += ingredient13
                arr += ingredient14
                arr += ingredient15
                arr += ingredient16
                arr += ingredient17
                arr += ingredient18
                arr += ingredient19
                arr += ingredient20
                arr += measure1
                arr += measure2
                arr += measure3
                arr += measure4
                arr += measure5
                arr += measure6
                arr += measure7
                arr += measure8
                arr += measure9
                arr += measure10
                arr += measure11
                arr += measure12
                arr += measure13
                arr += measure14
                arr += measure15
                arr += measure16
                arr += measure17
                arr += measure18
                arr += measure19
                arr += measure20
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
                add_db.visibility = View.VISIBLE
                db_visible = true
            }
        }
    }

    private fun get_meal_with_id(mealId: String) {
        val url_string = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=${mealId}"
        Log.d("activity",url_string)
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

    private fun db_add(context: Context) {
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "MealDatabase").build()
        val mealDao = db.mealDao()
        runBlocking {
            launch {
                val count = arr.size / 49
                Log.d("activity", "the value is: $count")
                var count2 = 0
                for (i in 1..count) {
                    Log.d("activity", "Meal id: ${arr[count2]} meal name: ${arr[count2+1]}")
                    Log.d("activity", "====================================")
                    try {
                        mealDao.insertUsers(
                            Meals(
                                arr[0 + count2].toInt(),
                                arr[1 + count2].lowercase(),
                                arr[2 + count2],
                                arr[3 + count2],
                                arr[4 + count2],
                                arr[5 + count2],
                                arr[6 + count2],
                                arr[7 + count2],
                                arr[8 + count2],
                                arr[9 + count2].lowercase(),
                                arr[10 + count2].lowercase(),
                                arr[11 + count2].lowercase(),
                                arr[12 + count2].lowercase(),
                                arr[13 + count2].lowercase(),
                                arr[14 + count2].lowercase(),
                                arr[15 + count2].lowercase(),
                                arr[16 + count2].lowercase(),
                                arr[17 + count2].lowercase(),
                                arr[18 + count2].lowercase(),
                                arr[19 + count2].lowercase(),
                                arr[20 + count2].lowercase(),
                                arr[21 + count2].lowercase(),
                                arr[22 + count2].lowercase(),
                                arr[23 + count2].lowercase(),
                                arr[24 + count2].lowercase(),
                                arr[25 + count2].lowercase(),
                                arr[26 + count2].lowercase(),
                                arr[27 + count2].lowercase(),
                                arr[28 + count2].lowercase(),
                                arr[29 + count2],
                                arr[30 + count2],
                                arr[31 + count2],
                                arr[32 + count2],
                                arr[33 + count2],
                                arr[34 + count2],
                                arr[35 + count2],
                                arr[36 + count2],
                                arr[37 + count2],
                                arr[38 + count2],
                                arr[39 + count2],
                                arr[40 + count2],
                                arr[41 + count2],
                                arr[42 + count2],
                                arr[43 + count2],
                                arr[44 + count2],
                                arr[45 + count2],
                                arr[46 + count2],
                                arr[47 + count2],
                                arr[48 + count2],
                            )
                        )
                        Log.d("activity", "added")
                        Toast.makeText(context, "Added ${arr[1 + count2]}", Toast.LENGTH_SHORT)
                            .show()
                    } catch (e: Exception) {
                        Log.d("activity", "error")
                        Toast.makeText(
                            context,
                            "error adding ${arr[1 + count2]}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    count2 += 49
                }
            }
        }
        arr = emptyArray()
    }


}