package com.aggarwalcode.onlinestore.ecomexampleb;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsListFragment extends Fragment implements AddToCart.OnFragmentInteractionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    public RecyclerView recyclerViewProducts;
    public DatabaseReference mDatabaseRef;
    public ProductsAdapter mAdapter;
    public ProductsHolder product;
    public List<ProductsHolder> mProducts = new ArrayList <ProductsHolder>();

    // TODO: Rename and change types of parameters
    public String mParam1;
    public String mParam2;
    public static final String TAG = "ProductsListFragment";
    public OnFragmentInteractionListener mListener;

    public ProductsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsListFragment newInstance(String param1, String param2) {
        ProductsListFragment fragment = new ProductsListFragment();
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
        final View view = inflater.inflate(R.layout.fragment_products_list, container, false);
        // Inflate the layout for this fragment
        recyclerViewProducts = (RecyclerView) view;
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerViewProducts.setHasFixedSize(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Products");
        mProducts.clear();
        //Change categoty1 to mParam1
        mDatabaseRef.orderByChild("other").startAt(mParam1).endAt(mParam1).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //mProducts.clear();
                product = dataSnapshot.getValue(ProductsHolder.class);
                product.key = dataSnapshot.getKey();
                mProducts.add(product);
                mAdapter = new ProductsAdapter(getActivity(),mProducts);
                recyclerViewProducts.setAdapter(mAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    // Call AddToCart Fragment Here
        recyclerViewProducts.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerViewProducts ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        AddToCart frag = AddToCart.newInstance(mProducts.get(position).getKey(),"Param Two");
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.container,frag,"AddToCart");
                        transaction.commit();
                        transaction.addToBackStack(null);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        /*recyclerViewProducts.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                RecyclerView rvt=rv;
                AddToCart frag = new AddToCart();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.container,frag,"Test Fragment");
                transaction.commit();
                transaction.addToBackStack("null");
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });*/
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
