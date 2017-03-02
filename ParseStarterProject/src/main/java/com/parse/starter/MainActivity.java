/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class MainActivity extends AppCompatActivity {

  //Button buttonlogin;
  //Boolean Activitytodo;

  public void redirectActivity(){
    if(ParseUser.getCurrentUser().get("riderOrDriver").equals("RiderActivity")){
      startActivity(new Intent(MainActivity.this,RiderActivity.class));
    }else{
      startActivity(new Intent(MainActivity.this,ViewRequestsActivity.class));
    }
  }
  public void getStarted(View view){
    Switch switch1 = (Switch) findViewById(R.id.switchlogin);

    Log.i("Switch Value",String.valueOf(switch1.isChecked()));
    String userType = "rider";
    if(switch1.isChecked()){
        userType="driver";
    }
    ParseUser.getCurrentUser().put("riderOrDriver",userType);
    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
        redirectActivity();
        }
      });

  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportActionBar().hide();
    if(ParseUser.getCurrentUser() ==null){
      ParseAnonymousUtils.logIn(new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {
          if(e==null){
            Log.i("Info ","Login Successfull");
          }
          else{
            Log.i("Info","Anonymous Login failed");
          }
        }
      });
    }else{
      if(ParseUser.getCurrentUser().get("riderOrDriver") != null){
        Log.i("Info","Redirecting as" + ParseUser.getCurrentUser().get("riderOrDriver"));
        redirectActivity();
      }
    }

    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }
}