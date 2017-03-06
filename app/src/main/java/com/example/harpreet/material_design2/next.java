package com.example.harpreet.material_design2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.harpreet.material_design2.Volley.vollySingleton;

import static com.example.harpreet.material_design2.Volley.vollySingleton.imageLoader;

public class next extends AppCompatActivity {

    ImageView cover;
    TextView overview, title, date;
    RatingBar rating;

    vollySingleton volly;

    String transPlot, transTitle, transDate, transImageurl;
    float transRating;
    Intent intent;
    //int position;

    // private movie movie;
    //ArrayList<movie> movie_list;


    public next() {
        //this.movie = movie;
        volly = vollySingleton.getInstance();

    }


    @Override
    protected void onCreate(Bundle bundle1) {
        super.onCreate(bundle1);
        setContentView(R.layout.singlepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


       //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);

        cover = (ImageView) findViewById(R.id.imageView3);
        overview = (TextView) findViewById(R.id.overview);
        title = (TextView) findViewById(R.id.title1);
        date = (TextView) findViewById(R.id.date);
        rating = (RatingBar) findViewById(R.id.ratingBar);
       // intent = new Intent();
        bundle1 = getIntent().getExtras();
      //  bundle1.get("overview");
        //   bundle=new Bundle();

        transPlot = bundle1.getString("overview");
        transTitle = bundle1.getString("title");
        transImageurl = bundle1.getString("image");
        transDate = bundle1.getString("date");
        transRating = bundle1.getFloat("rating", 0);


    //    String hello=getIntent().getStringExtra("hello");
      //  System.out.println(hello);
       // Toast.makeText(this, hello, Toast.LENGTH_SHORT).show();
       displayJsonData();
    }

    public void displayJsonData() {

        // movie=movie_list.get(position);
        if (transImageurl != null) {

            imageLoader.get(transImageurl, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                    cover.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {


                    cover.setImageResource(R.drawable.place);
                }
            });
        }

        overview.setText("PLOT: \n"+transPlot);
        title.setText("TITLE: \n"+transTitle);
        date.setText("Release Date \n "+transDate);
        rating.setRating(transRating);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show();
        } else if (id == android.R.id.home) {

            startActivity(new Intent(this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
