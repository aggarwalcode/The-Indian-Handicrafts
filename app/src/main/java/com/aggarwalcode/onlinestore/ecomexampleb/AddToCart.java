package com.aggarwalcode.onlinestore.ecomexampleb;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddToCart.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddToCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddToCart extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Button addToCart;

    // TODO: Rename and change types of parameters
    public String mParam1;
    private String mParam2;

    public List<String> allImages = new ArrayList<String>();
    //String[] stockArr;
    public String[] stringArray;
    ViewPagerAdapter adapter;
    ViewPager viewPager;
    public DatabaseReference mDatabaseRef;

    LinearLayout sliderDotPanel;
    private int dotCount;
    private ImageView[] dots;

    private OnFragmentInteractionListener mListener;

    public AddToCart() {
        // Required empty public constructor
    }

    public static AddToCart newInstance(String param1, String param2) {
        AddToCart fragment = new AddToCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_to_cart, container, false);

        addToCart = (Button) view.findViewById(R.id.addToCart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Arrays.asList(FragmentCart.prodBuyArray).contains(mParam1))){
                    FragmentCart.keysAddedToCart.add(mParam1);
                }
                else return;
            }
        });

        sliderDotPanel = (LinearLayout) view.findViewById(R.id.sliderDots);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        mDatabaseRef.child(mParam1).child("allimages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String upload = (String) postSnapshot.getValue();
                    allImages.add(upload);
                    //stockArr = allImages.toArray(stockArr);
                    stringArray = allImages.toArray(new String[0]);
                }
                viewPager = (ViewPager) view.findViewById(R.id.viewPager);

                adapter = new ViewPagerAdapter(getActivity(),stringArray);
                viewPager.setAdapter(adapter);
                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity());

                dotCount = allImages.size();
                dots = new ImageView[dotCount];
                for(int i = 0; i < dotCount; i++){

                    dots[i] = new ImageView(getActivity());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    sliderDotPanel.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        for(int i = 0; i< dotCount; i++){
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.nonactive_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
