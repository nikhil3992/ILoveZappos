package com.example.nikhil.zapposapp;

import android.content.Context;
import android.databinding.DataBindingUtil;

import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;


import com.example.nikhil.zapposapp.databinding.FragmentProductBinding;

import java.util.ArrayList;


public class ProductFragment extends Fragment  {

    ArrayList<Long> checkoutList;
    Animation zoomOut,zoomIn;
    long productID;
    FragmentProductBinding binding;

    private OnFragmentInteractionListener mListener;

    public ProductFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product,container,false);
        View view = binding.getRoot();
        checkoutList = new ArrayList<>();
        zoomIn = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
        zoomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_out);

        MyHandlers handlers = new MyHandlers();
        binding.setHandler(handlers);
        binding.fragmentOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        zoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(checkoutList.contains(productID)) {
                    binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_done_white_24dp));
                } else {
                    binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_add_shopping_cart_white_24dp));
                }
                binding.floatingActionButton.startAnimation(zoomIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        return view;
    }

    public class MyHandlers {

        public void onClickFAB(View view) {

            if(checkoutList.contains(productID)) {
                checkoutList.remove(productID);
                Toast.makeText(getActivity(),"Product removed from cart",Toast.LENGTH_SHORT).show();
                binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_add_shopping_cart_white_24dp));
            } else {
                checkoutList.add(productID);
                Toast.makeText(getActivity(),"Product added to cart",Toast.LENGTH_SHORT).show();
                binding.floatingActionButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_done_white_24dp));
            }
            binding.floatingActionButton.startAnimation(zoomOut);
        }

    }

    public void updateUI(Result result ,boolean status) {

        binding.setProduct(result);
        productID = Long.parseLong(result.getProductId());
        if(status) {
            binding.floatingActionButton.startAnimation(zoomOut);
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
        void onFragmentInteraction(Uri uri);
    }
}

