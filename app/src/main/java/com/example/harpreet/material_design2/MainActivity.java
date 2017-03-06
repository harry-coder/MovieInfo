package com.example.harpreet.material_design2;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.harpreet.material_design2.Fragments.Movie_Details;
import com.example.harpreet.material_design2.Fragments.Song_A;
import com.example.harpreet.material_design2.Fragments.Song_B;
import com.example.harpreet.material_design2.Fragments.Song_C;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    TabLayout tabLayout;
    LayoutInflater inflater;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       /* inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.fragment_song_a, null, false);
        View view2 = inflater.inflate(R.layout.fragment_b, null, false);
        View view3 = inflater.inflate(R.layout.fragment_c, null, false);*/


        Navigation_Drawer drawer = (Navigation_Drawer) getSupportFragmentManager().findFragmentById(R.id.frag1);
        drawer.setup(R.id.frag1, (DrawerLayout) findViewById(R.id.drawer), toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        pager = (ViewPager) findViewById(R.id.pager);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        final int image[] = {R.drawable.check, R.drawable.check2, R.drawable.check3,};
      /*  final TabLayout.Tab facebook=tabLayout.newTab();
        final TabLayout.Tab youtube=tabLayout.newTab();
        final TabLayout.Tab Twitter=tabLayout.newTab();*/

        pager.setAdapter(new myAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(pager, true);


        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.selected_color));
        tabLayout.setTabTextColors(getResources().getColor(R.color.initial_color), getResources().getColor(R.color.selected_color));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tabLayout.getSelectedTabPosition();
                tab.setIcon(image[position]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                tab.setIcon(null);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //   facebook.setCustomView(R.layout.fragment_song_a);
        //  youtube.setCustomView(R.layout.fragment_b);
        // Twitter.setCustomView(R.layout.fragment_c);

        // tabLayout.addTab(facebook,0);
        //tabLayout.addTab(youtube,1);
        // tabLayout.addTab(Twitter,2);

      /*  TabLayout.Tab b=   youtube.setCustomView(view2);
        TabLayout.Tab c= Twitter.setCustomView(view3);
        TabLayout.Tab a= facebook.setCustomView(view1);
*/

        //      tabLayout.addTab(a,0);
        //    tabLayout.addTab(b,1);
        ///   tabLayout.addTab(c,2);
        /* final TabLayout.Tab facebook=tabLayout.newTab();
        final TabLayout.Tab youtube=tabLayout.newTab();
        final TabLayout.Tab Twitter=tabLayout.newTab();
        facebook.setText("Facebook");
        youtube.setText("Youtube");
        Twitter.setText("Twitter");*/


        //tabLayout.addView(view1,0);
        // tabLayout.addView(view2,1);
        // tabLayout.addView(view3,2);
        //tabLayout.addTab(facebook,0);
        //tabLayout.addTab(youtube,1);
        // tabLayout.addTab(Twitter,2);

        // pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


    @Override
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
        } else if (id == R.id.navigate) {
            startActivity(new Intent(this, next.class));
        } else if (id == R.id.Latest_Movies) {


        }

        return super.onOptionsItemSelected(item);
    }

    class myAdapter extends FragmentStatePagerAdapter {

        public myAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Movie_Details details=null;
            Fragment fragment = null;
            if (position == 0) {

                Toast.makeText(MainActivity.this, "Inside "+position , Toast.LENGTH_SHORT).show();
                 return details=new Movie_Details();
                 //fragment = new Song_A();


            }
            if (position == 1) {
                return fragment = new Song_B();
            }
            if (position == 2) {
                return fragment = new Song_C();
            }
            return fragment;

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Facebook";
            }
            if (position == 1) {
                return "Twitter";
            }
            if (position == 2) {
                return "Youtube";
            } else return null;
        }
    }
}
