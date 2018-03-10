package com.aggarwalcode.onlinestore.ecomexampleb;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainLandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ItemFragmentCat.OnListFragmentInteractionListener,
        ProductsListFragment.OnFragmentInteractionListener,
        AddToCart.OnFragmentInteractionListener,
        FragmentCart.OnFragmentInteractionListener {

    public static final String TAG = "EcomLanding";
    RecyclerView recyclerViewEcom;
    ImageAdapter mAdapter;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    Animation animSlidetoLeft;
    android.widget.SearchView searchView;
    ImageButton shopByCatBut;
    FragmentCart fragmentCart = FragmentCart.newInstance(null, null);
    TextView textCartItemCount;
    int mCartItemCount = 10;
    Toolbar toolbar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_landing_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Toolbar toolbarSecond = (Toolbar) findViewById(R.id.toolbarSecond);
        shopByCatBut = (ImageButton) findViewById(R.id.shopByCatBut);

        setSupportActionBar(toolbar);

        animSlidetoLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
        toolbarSecond.startAnimation(animSlidetoLeft);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Calling fragment Here
        shopByCatBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemFragmentCat frag = new ItemFragmentCat();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.container,frag,"ItemFragmentCat");
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });

        //Recyvler View Feeder
        recyclerViewEcom = (RecyclerView) findViewById(R.id.recyclerViewEcom);
        recyclerViewEcom.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEcom.setHasFixedSize(true);

        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(MainLandingPage.this,mUploads);
                recyclerViewEcom.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerViewEcom.addOnItemTouchListener(

                new RecyclerItemClickListener(this, recyclerViewEcom ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        ProductsListFragment frag = ProductsListFragment.newInstance(mUploads.get(position).getName(),"Param Two");
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.add(R.id.container,frag,"Test Fragment");
                        transaction.commit();
                        transaction.addToBackStack(null);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        mCartItemCount = FragmentCart.keysAddedToCart.size();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.ecom_landing, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_drawer_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        FragmentCart frag = new FragmentCart();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,frag,"FragmentCart");
        transaction.commit();
        transaction.addToBackStack(null);

        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
*/
        invalidateOptionsMenu();
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {
        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
            invalidateOptionsMenu();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_category) {

        } else if (id == R.id.nav_deals) {

        } else if (id == R.id.nav_orders) {

        }else if (id == R.id.nav_wishlist) {

        }else if (id == R.id.nav_account) {

        }else if (id == R.id.nav_sell_product) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(ShopByCat item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void homePage(View view) {
        //startActivity(new Intent(MainLandingPage.this, MainLandingPage.class));
    }
}
