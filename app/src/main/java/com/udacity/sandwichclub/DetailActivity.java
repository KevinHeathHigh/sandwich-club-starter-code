package com.udacity.sandwichclub;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //Create the binding variable
    ActivityDetailBinding mDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Create the binding to the View
        mDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //Update populateUI with Sandwich class parameter
        populateUI(sandwich);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    //Added Sandwich class parameter
    private void populateUI(Sandwich sandwich) {
        //Use Binding to update DetailActivity views from passed Sandwich Class
        mDetailBinding.descriptionTv.setText(sandwich.getDescription());
        mDetailBinding.originTv.setText(sandwich.getPlaceOfOrigin());

        //Want each ingredient to be on it's own line (more readable)
        String ingredients = "";
        for (String ingredient : sandwich.getIngredients()) {
            ingredients += ingredient + "\n";
        }
        mDetailBinding.ingredientsTv.setText(ingredients);

        //Want each alias to be on it's own line (more readable)
        String aliases = "";
        for (String alias : sandwich.getAlsoKnownAs()) {
            aliases += alias + "\n";
        }
        mDetailBinding.alsoKnownTv.setText(aliases);
    }
}
