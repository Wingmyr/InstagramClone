package com.example.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private String allKickBoxers;
    private Button btnSave, btnGetAllData, btnTransition;
    private EditText edtName, edtPunchSpeed, edtPunchPower, edtKickSpeed, edtKickPower;
    private TextView txtGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtGetData = findViewById(R.id.txtGetData);
        btnSave = findViewById(R.id.btnSave);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        btnSave.setOnClickListener(SignUp.this);

        edtName = findViewById(R.id.edtName);
        edtPunchSpeed = findViewById(R.id.edtPunchSpeed);
        edtPunchPower = findViewById(R.id.edtPunchPower);
        edtKickSpeed = findViewById(R.id.edtKickSpeed);
        edtKickPower = findViewById(R.id.edtKickPower);
        btnTransition = findViewById(R.id.btnGetNextActivity);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("jFpGmE9JTq",
                        new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (object != null && e == null) {
                                    txtGetData.setText(object.get("name") + " - " +
                                            "Punch power: " + object.get("punch_power"));
                               }
                            }
                        });

            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allKickBoxers = "";
                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");

                    //queryAll.whereGreaterThan("punch_power", 500);
                //queryAll.whereGreaterThanOrEqualTo("punch_power", 300);
                queryAll.setLimit(1);

                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            for(ParseObject kickBoxer : objects) {
                                allKickBoxers = allKickBoxers + kickBoxer.get("name") + "\n";
                            }
                            if (objects.size() > 0) {
                                FancyToast.makeText(SignUp.this,  allKickBoxers,
                                        FancyToast.LENGTH_LONG, FancyToast.SUCCESS,
                                        true).show();
                            } else {
                                FancyToast.makeText(SignUp.this, e.getMessage(),
                                        FancyToast.LENGTH_LONG, FancyToast.ERROR,
                                        true).show();
                                //Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
        btnTransition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Här startar vi en annan aktivitet (Skärm)
                Intent intent = new Intent(SignUp.this,
                        SignUpLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

        try {
            final ParseObject kickBoxer = new ParseObject("KickBoxer");

            kickBoxer.put("name", edtName.getText().toString());
            kickBoxer.put("punch_speed",
                    Integer.parseInt(edtPunchSpeed.getText().toString()));
            kickBoxer.put("punch_power",
                    Integer.parseInt(edtPunchPower.getText().toString()));
            kickBoxer.put("Kick_speed",
                    Integer.parseInt(edtKickSpeed.getText().toString()));
            kickBoxer.put("Kick_power",
                    Integer.parseInt(edtKickPower.getText().toString()));


            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        FancyToast.makeText(SignUp.this, kickBoxer.get("name") + " object saved ok",
                                FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        //Toast.makeText(SignUp.this, kickBoxer.get("name") +
                        //        " object saved ok", Toast.LENGTH_SHORT).show();
                    } else {
                        FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
                        //Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            FancyToast.makeText(SignUp.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
        }
    }


}

