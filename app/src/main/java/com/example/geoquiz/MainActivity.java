package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Button mButtonTrue ;
    private Button mButtonFalse ;
    private TextView mFirstQuestionTxt;




    /**************************** ONCREATE *****************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        setListener();

    }
    /*************************** FIND VIEW **********************************************/

    @SuppressLint("WrongViewCast")
    private void findView() {
        mButtonFalse=findViewById(R.id.btn_false);
        mButtonTrue=findViewById(R.id.btn_true);
        mFirstQuestionTxt=findViewById(R.id.first_question);


    }

    /*********************** SET LISTENER **********************************************/
    private void setListener() {
        mButtonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this,
                        R.string.toast_true, Toast.LENGTH_LONG);

                View toastView = toast.getView();

                TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                toastMessage.setTextSize(18);
                toastMessage.setTextColor(Color.GREEN);
               // toastMessage.setCompoundDrawablePadding(16);
                mFirstQuestionTxt.setTextColor(Color.GREEN);
                toast.show();



                /*Toast toast=new Toast(MainActivity.this);
                mToastTxtTrue.setText(R.string.toast_true);
                mToastTxtTrue.setTextColor(Color.GREEN);
                toast.setView(mToastTxtTrue);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
*/


            }
        });

        mButtonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Toast toast=new Toast(MainActivity.this);
                mToastTxtFalse.setText(R.string.toast_false);
                mToastTxtFalse.setTextColor(Color.RED);
                toast.setView(mToastTxtFalse);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();*/
                //Toast aa = Toast.makeText(getBaseContext(), "OPEN",Toast.LENGTH_SHORT);

               // aa.show();
                Toast toast = Toast.makeText(MainActivity.this,
                        R.string.toast_false, Toast.LENGTH_LONG);

                toast.setGravity(Gravity.TOP,0,60);
                View toastView = toast.getView();

                TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
                toastMessage.setTextSize(18);
                toastMessage.setTextColor(Color.RED);
               // toastMessage.setCompoundDrawablePadding(16);
                mFirstQuestionTxt.setTextColor(Color.RED);
                toast.show();

            }
        });
    }
}