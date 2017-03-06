package com.example.harpreet.material_design2.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.harpreet.material_design2.Pojo.movie;
import com.example.harpreet.material_design2.R;
import com.example.harpreet.material_design2.Volley.MyApplication;
import com.example.harpreet.material_design2.Volley.vollySingleton;
import com.example.harpreet.material_design2.next;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.harpreet.material_design2.Pojo.Movies_Args.Movie_image;
import static com.example.harpreet.material_design2.Pojo.Movies_Args.Movie_story;
import static com.example.harpreet.material_design2.Pojo.Movies_Args.Movie_title;
import static com.example.harpreet.material_design2.Pojo.Movies_Args.Movie_votes;
import static com.example.harpreet.material_design2.Pojo.Movies_Args.releasedate;
import static com.example.harpreet.material_design2.Volley.vollySingleton.imageLoader;


/**
 * A simple {@link Fragment} subclass.
 */
public class Movie_Details extends Fragment {

    protected vollySingleton volly;  //Not initialise
    private RequestQueue requestQueue;
    // private ImageLoader imageLoader;  //Not initialise
    private String imageUrl;
    movie latestMovie;

    private String title, releaseDate, image, story;
    private String imageBaseUrl = "http://image.tmdb.org/t/p/w342/";
    private int votes;
    private RecyclerView recycler;
    private ArrayList<movie> list;
    Parcelable parcelMovie;

    public Movie_Details() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        volly = vollySingleton.getInstance();

        requestQueue = volly.getRequestQueue();
//vollySingleton single=vollySingleton.getInstance();

        // this.token = getToken();
        // getSessionId();
    }

   /* public String getSessionId() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, accessUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    sessionId = response.getString("session_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return this.sessionId;
    }

    public String getToken() {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, authenticateUrl, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    token = response.getString("request_token");

                    Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
        return token;
    }*/


    public ArrayList<movie> sendRequest() {
        //  Toast.makeText(getContext(), "Inside sendrequest", Toast.LENGTH_SHORT).show();
        list = new ArrayList();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, MyApplication.url(1, "upcoming"), null, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                try {
                    if (response == null || response.length() == 0) {
                        //Toast.makeText(getActivity(), "Inside null Response", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        //  Toast.makeText(getActivity(), "Inside request", Toast.LENGTH_SHORT).show();

                        JSONArray result_Array = response.getJSONArray("results");
                        for (int i = 0; i < result_Array.length(); i++) {

                            JSONObject currentMovie = result_Array.getJSONObject(i);

                            title = currentMovie.getString(Movie_title);
                            releaseDate = currentMovie.getString(releasedate);

                            story = currentMovie.getString(Movie_story);

                            if (currentMovie.has(Movie_image)) {
                                image = currentMovie.getString(Movie_image);
                            } else {
                                image = "";
                            }
                            votes = currentMovie.getInt(Movie_votes);

                            latestMovie = new movie();
                            latestMovie.setTitle(title);
                            latestMovie.setPoster(imageBaseUrl + image);
                            latestMovie.setPlot(story);
                            latestMovie.setReleaseDate(releaseDate);

                            latestMovie.setRating((votes / 10f));


                            list.add(latestMovie);


                        }

                        //    System.out.println(list.toString());
                        //      Toast.makeText(getActivity(), list.toString(), Toast.LENGTH_SHORT).show();


                    }
                    parcelMovie=Parcels.wrap(latestMovie);
                }


                catch (Exception e) {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   System.out.println(error.toString());

                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(objectRequest);

        return list;
    }
   /* public static  movie getMovieList(int position)
    {
        ArrayList<movie> movie_list=getMovieList()
        return
    }
    public ArrayList<movie> getMovieList(ArrayList<movie> movies)
    {
        return movies;
    }
*/

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_song_a, container, false);
        //  Toast.makeText(getActivity(), view.toString(), Toast.LENGTH_SHORT).show();
        recycler = (RecyclerView) view.findViewById(R.id.recycle12);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(new myAdapter(sendRequest(), getActivity()));

        recycler.addOnItemTouchListener(new myHolder.RecycleTouch(getActivity(), recycler, new myHolder.RecycleTouch.clickListener() {
            @Override
            public void onSingleClick(View view, int position,ArrayList <movie> movie_list) {

              latestMovie  =movie_list.get(position);
                Intent intent=new Intent(getActivity(),next.class);
               Bundle savedInstanceState=new Bundle();

                savedInstanceState.putString("overview",latestMovie.getPlot());
                savedInstanceState.putString("title",latestMovie.getTitle());
                savedInstanceState.putString("date",latestMovie.getReleaseDate());
                savedInstanceState.putString("image",latestMovie.getPoster());
                savedInstanceState.putFloat("rating", (float) latestMovie.getRating());
                intent.putExtras(savedInstanceState);

               // intent.putExtra("hello","this is me");

                Toast.makeText(getActivity(), ""+savedInstanceState.get("overview"), Toast.LENGTH_SHORT).show();

               /* intent.putExtra("overview",latestMovie.getPlot());

                intent.putExtra("title",latestMovie.getTitle());
                intent.putExtra("date",latestMovie.getReleaseDate());
                intent.putExtra("image",latestMovie.getPoster());
                intent.putExtra("rating",latestMovie.getRating());*/
                startActivity(intent);

                // parcelMovie= Parcels.wrap(latestMovie);
               // intent.putExtra("position",position);
                //view.setTag(movie_list);
             //   getMovieList(movie_list);
               // intent.putExtra("movieObject", (Serializable) latestMovie);
                //intent.putExtra("movieObject", parcelMovie);

               // next nx=new next(latestMovie);
                /*if(parcelMovie!=null) {

                    startActivity(intent);
                }*/
            }



            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(getActivity(), "Long press is performed", Toast.LENGTH_SHORT).show();
            }
        }));

        //  Toast.makeText(getActivity(), "about to send the request", Toast.LENGTH_SHORT).show();


        return view;

    }

    class myAdapter extends RecyclerView.Adapter<myHolder> {
        ArrayList<movie> movie_list = new ArrayList<>();
        Context context;
        LayoutInflater inflater;


        public void getMoviewList(ArrayList<movie> list) {
            this.movie_list = list;
            notifyItemRangeChanged(0, movie_list.size());
        }

        public myAdapter(ArrayList<movie> list, Context context) {
            this.movie_list = list;
            this.context = context;
            Toast.makeText(context, "Inside adapter", Toast.LENGTH_SHORT).show();
        }


        @Override
        public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.upcoming_movie, parent, false);

            myHolder holder = new myHolder(view, context,movie_list);

            return holder;

        }


        @Override
        public void onBindViewHolder(final myHolder holder, int position) {

            movie movie = movie_list.get(position);
            // holder.title.setText(movie.getTitle());

            holder.rating.setRating((float) movie.getRating());


            // Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
            // holder.picture.setImageResource(Integer.parseInt(movie.getPoster()));

            //  YoYo.with(Techniques.Bounce).duration(1000).repeat(200).playOn(title);
//            YoYo.with(Techniques.Swing).duration(3000).repeat(200).playOn(holder.enter);
            //   YoYo.with(Techniques.BounceIn).duration(1000).playOn(holder.picture);

            if ((imageUrl = movie.getPoster()) != null) {

                imageLoader.get(movie.getPoster(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        holder.picture.setImageBitmap(response.getBitmap());

                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        holder.picture.setImageResource(R.drawable.place);
                    }
                });
            }
        }


        @Override
        public int getItemCount() {
            return movie_list.size();
        }
    }


}

     class  myHolder extends RecyclerView.ViewHolder {
    ImageView picture;
    TextView title;
    Button enter;
    RatingBar rating;
  static  ArrayList<movie>movie_list=new ArrayList<>();

    Context context;

    public myHolder(View itemView, final Context context,ArrayList<movie> movie_list) {
        super(itemView);
        this.context = context;
        this.movie_list=movie_list;
        picture = (ImageView) itemView.findViewById(R.id.pic);
        title = (TextView) itemView.findViewById(R.id.tv);
        enter = (Button) itemView.findViewById(R.id.button);
        rating = (RatingBar) itemView.findViewById(R.id.ratingBar);


        YoYo.with(Techniques.Swing).duration(3000).repeat(200).playOn(enter);
        // YoYo.with(Techniques.BounceIn).duration(1000).playOn(picture);


       /* new Thread(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.Swing).duration(3000).repeat(200).playOn(enter);

            }
        }).start();
*/
    }

    static class RecycleTouch implements RecyclerView.OnItemTouchListener {

        Context context; RecyclerView view;
        clickListener listener;
        View Child;
        public RecycleTouch(Context context, RecyclerView view, clickListener listener) {

            this.context=context;
            this.view=view;
            this.listener=listener;
        }

        GestureDetector gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
               Child= view.findChildViewUnder(e.getX(),e.getY());

                listener.onSingleClick(Child,view.getChildAdapterPosition(Child),movie_list);

                return true;

            }

            @Override
            public void onLongPress(MotionEvent e) {

              Child=view.findChildViewUnder(e.getX(),e.getY());
             listener.onLongClick(Child,view.getChildAdapterPosition(Child));


            }
        });

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            gestureDetector.onTouchEvent(e);

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        public interface clickListener
        {
            public void onSingleClick(View view,int position,ArrayList<movie>movie_list);
            public void onLongClick(View view,int position);
        }


    }



}



