package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /*  View of JSON Object
     {
        "name":{
            "mainName":"Vada Pav",
            "alsoKnownAs":["Bombay Burger", "Vada Pao", "Wada Pav", "Wada Pao", "Pao Vada", "Pav Vada", "Pao Wada", "Pav Wada"]
        },
        "placeOfOrigin":"India",
        "description":"Vada Pav is a vegetarian fast food dish native to the Indian state of Maharashtra. The dish consists of a deep fried potato dumpling placed inside a bread bun (pav) sliced almost in half through the middle. It is generally accompanied with one or more chutneys and a green chilli pepper. It originated as cheap street food in Mumbai, but is now served in food stalls and restaurants across India. It is also called Bombay Burger in keeping with its origins and its resemblance in physical form to a burger.",
        "image":"https://upload.wikimedia.org/wikipedia/commons/1/15/Vada_Paav-The_Mumbai_Burger.jpg",
        "ingredients": ["Deep-fried mashed potato patty", "chilli peppers", "garlic", "ginger", "bread bun"]
    }
    */

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        //Parse the JSON String
        try {
            JSONObject sandwichInfo = new JSONObject(json);
            //Get the Nested "name" JSON
            JSONObject sandwichName = sandwichInfo.getJSONObject("name");
            sandwich.setMainName(sandwichName.getString("mainName"));
            JSONArray alsoKnownAs = sandwichName.getJSONArray("alsoKnownAs");
            //build List from JSONArray
            List<String> aliasesList = new ArrayList<String>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                aliasesList.add(alsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(aliasesList);

            //build List from JSONArray
            JSONArray sandwichIngredients = sandwichInfo.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<String>();
            for (int i = 0; i < sandwichIngredients.length(); i++) {
                ingredientsList.add(sandwichIngredients.getString(i));
            }
            sandwich.setIngredients(ingredientsList);

            sandwich.setPlaceOfOrigin(sandwichInfo.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichInfo.getString("description"));
            sandwich.setImage(sandwichInfo.getString("image"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
