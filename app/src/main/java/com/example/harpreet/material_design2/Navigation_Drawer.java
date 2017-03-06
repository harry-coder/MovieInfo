package com.example.harpreet.material_design2;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.harpreet.material_design2.Volley.vollySingleton;

import java.util.ArrayList;
import java.util.List;


public class Navigation_Drawer extends Fragment {

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    static String filename = "drawerfile";
    String prefname = "drawerExits";
    View view;

    private RecyclerView recyclerView;

    boolean firstTimeDrawer;
    boolean savedInstance;
    myAdapter adapter;

    public Navigation_Drawer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstTimeDrawer = Boolean.parseBoolean(ReadPreferences(prefname, "false"));

        if (savedInstanceState != null) {
            savedInstance = true;
        }


    }

    public List<information> getData() {
        List<information> data = new ArrayList<>();
        int image[] = {R.drawable.check, R.drawable.check2, R.drawable.check3, R.drawable.check4,};
        String title[] = {"Info", "Contact-us", "Email", "Location"};

        information info;
        for (int i = 0; i < image.length && i < title.length; i++) {
            data.add(new information(image[i], title[i]));
            /*info=new information();
            info.text=title[i];
            info.image=image[i];
            data.add(info);*/

        }

        return data;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_navigation__drawer, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);

        adapter = new myAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecycleItemTouch(getActivity(), recyclerView, new RecycleItemTouch.ClickListener() {
            @Override
            public void OnSingleClick(View v, int position) {

                Toast.makeText(getActivity(), "Single Click " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnLongClick(View v, int position) {
                Toast.makeText(getActivity(), "Long Click " + position, Toast.LENGTH_SHORT).show();
            }
        }));

       /* RequestQueue request= vollySingleton.getInstance().getRequestQueue();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, "http://www.bollywoodnews.org/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error has occured", Toast.LENGTH_SHORT).show();

            }
        });

        request.add(stringRequest);

    }*/
        return view;}


    public void setup(int fragid, final DrawerLayout drawerLayout, final Toolbar toolbar) {

        view = getActivity().findViewById(fragid);
        this.drawerLayout = drawerLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.opened, R.string.closed) {


            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (firstTimeDrawer) {
                    firstTimeDrawer = true;
                    savepreferences(getContext(), prefname, firstTimeDrawer + "");
                }

                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }


            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (slideOffset < 0.5)
                    toolbar.setAlpha(1 - slideOffset);

            }
        };
        if (!firstTimeDrawer && !savedInstance) {
            drawerLayout.openDrawer(view);
        }
        drawerLayout.setDrawerListener(drawerToggle);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {

                drawerToggle.syncState();
                //  drawerLayout.bringToFront();
            }
        });


    }

    public static void savepreferences(Context context, String prefName, String value) {
        SharedPreferences preferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(prefName, value);
        editor.apply();
    }

    public String ReadPreferences(String prefName, String defaultValue) {
        SharedPreferences preferences = getContext().getSharedPreferences(filename, Context.MODE_PRIVATE);

        return preferences.getString(prefName, defaultValue);


    }

    static class RecycleItemTouch implements RecyclerView.OnItemTouchListener {

        Context context;
        GestureDetector gestureDetector;
        ClickListener clicklistener;

        RecycleItemTouch(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clicklistener = clickListener;

            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {


                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                    clickListener.OnSingleClick(child,recyclerView.getChildAdapterPosition(child));

                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.OnLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }


                }




            });

        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            gestureDetector.onTouchEvent(e);
           /*if (rv != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                clicklistener.OnSingleClick(child, rv.getChildAdapterPosition(child));
            }*/
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {


            Toast.makeText(context, "this is on touch event guys", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {


        }


        public interface ClickListener {

            public void OnSingleClick(View v, int positon);

            public void OnLongClick(View v, int positon);
        }
    }

}
