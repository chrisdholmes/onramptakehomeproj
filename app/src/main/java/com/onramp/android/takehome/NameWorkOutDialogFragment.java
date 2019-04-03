package com.onramp.android.takehome;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class NameWorkOutDialogFragment extends DialogFragment
{
    private NameWorkOutListener mListener;
    private TextInputEditText editText;
    private Button submitBtn;
    private OnDismissListener dismissListener;

    public static NameWorkOutDialogFragment newInstance()
    {
        NameWorkOutDialogFragment fragment = new NameWorkOutDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

       //setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);


    }

    @Override
    public void onStart()
    {
        super.onStart();
        // In order to inflate the fragment view to full screen
        // get instance of the Dialog
        Log.d("NameWorkOut: ", "name work out frag started");
        Dialog d = getDialog();
        if(d != null)
        {
            //Get width of this fragment's parent from layout parameters
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            //Get height of this fragment's parent from layout parameters
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setGravity(Gravity.CENTER);
            d.getWindow().setLayout(1000, 750);
            //Set layout parameters of this DialogFragment with retrieved values
            //d.getWindow().setLayout(width, height);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle b)
    {

        // instance of the fragment view
        View v = inflater.inflate(R.layout.fragment_name_workout, container, false);

        NewWorkOutFragment fragment = NewWorkOutFragment.newInstance();


        editText = v.findViewById(R.id.workOutName);
        submitBtn = v.findViewById(R.id.submitBtn);


        submitBtn.setOnClickListener((onClickView) ->
        {
            String workOutName = editText.getText().toString();
            Log.d("NameWorkOut: ", "submit clicked");
            Bundle bun = new Bundle();
            bun.putString("name", workOutName);
            setArguments(bun);

            dismiss();
        });


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof NewWorkOutFragment.NewWorkOutListener)
        {
            mListener = (NameWorkOutDialogFragment.NameWorkOutListener) context;
        } else
        {
            throw new RuntimeException(context.toString()
                    + " must implement NewWorkOutListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface NameWorkOutListener
    {

        void nameWorkOutInteraction(String workOutName);
    }

    public interface OnDismissListener {
        void onDismiss(NameWorkOutDialogFragment myDialogFragment);
    }

    @Override
    public void onDismiss(DialogInterface dialog)
    {
        super.onDismiss(dialog);

        if (dismissListener != null)
        {
            dismissListener.onDismiss(this);
        }
    }


    public void setDissmissListener(OnDismissListener dissmissListener) {
        this.dismissListener = dissmissListener;
    }


}
