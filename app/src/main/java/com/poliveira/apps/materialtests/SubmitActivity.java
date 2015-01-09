package com.poliveira.apps.materialtests;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.parse.Parse;

import com.parse.ParseFile;
import com.parse.ParseObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SubmitActivity extends ActionBarActivity {
    ImageView chosenImageView;
    static int timeMode = 0;
    static int dateMode = 0;
    private static TextView startTime1=null;
    private static TextView startDate1=null;

    String sendingYear;
    String sendingMonth;
    String sendingDay;
    String sendingHour;
    String sendingMinutes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        chosenImageView = (ImageView) this.findViewById(R.id.PreviewImage);
        startTime1= (TextView)findViewById(R.id.startTime);
        startDate1= (TextView)findViewById(R.id.startDate);
    }
    public void onClickPickImage(View v) {
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent, 0);
    }
    Bitmap bmp;
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            Uri imageFileUri = intent.getData();
            Display currentDisplay = getWindowManager().getDefaultDisplay();
            int dw = currentDisplay.getWidth();
            int dh = currentDisplay.getHeight() / 2 - 100;
            try {
                BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                bmpFactoryOptions.inJustDecodeBounds = true;
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);
                bmpFactoryOptions.inSampleSize = 2;
                bmpFactoryOptions.inJustDecodeBounds = false;
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(
                        imageFileUri), null, bmpFactoryOptions);
                chosenImageView.setImageBitmap(bmp);

            } catch (FileNotFoundException e) {
                Log.v("ERROR", e.toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPublishClick(View v) {

        ParseObject submitInfo = new ParseObject("Activities");


        String start = sendingYear + "-" + sendingMonth + "-" + sendingDay + "T" + sendingHour + ":" + sendingMinutes;
        System.out.println(start);

        submitInfo.saveInBackground();
        // Create a column named "ImageFile" and insert the image
        if(!bmp = null){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();
        ParseObject imgupload = new ParseObject("Activities");
        imgupload.put("Thumbnail.png", image);
        //imgupload.put("Begin", )

        imgupload.saveInBackground();




        // Create the class and the columns

/*        EditText Title = (EditText) findViewById(R.id.Title);
        EditText Address = (EditText) findViewById(R.id.Address);
        EditText StartTime = (EditText) findViewById(R.id.StartTime);
        EditText StartDate = (EditText) findViewById(R.id.StartDate);
        EditText EndTime = (EditText) findViewById(R.id.EndTime);
        EditText EndDate = (EditText) findViewById(R.id.EndDate);
        EditText Description = (EditText) findViewById(R.id.Description);
        CheckBox MemberExclusive = (CheckBox) findViewById(R.id.MemberExclusive);
        CheckBox Sport = (CheckBox) findViewById(R.id.SportEvent);
        CheckBox Fundraiser = (CheckBox) findViewById(R.id.Fundraiser);

        submitInfo.put("Title", Title.getText().toString());
        submitInfo.put("Address", Address.getText().toString());
        submitInfo.put("StartTime", StartTime.getText().toString());
        submitInfo.put("StartDate", StartDate.getText().toString());
        submitInfo.put("EndTime", EndTime.getText().toString());
        submitInfo.put("EndDate", EndDate.getText().toString());
        submitInfo.put("Description", Description.getText().toString());

        if (MemberExclusive.isChecked()) {
            submitInfo.put("MemberExclusive", true);
        } else {
            submitInfo.put("MemberExclusive", false);
        }
        if (Sport.isChecked()) {
            submitInfo.put("Sports", true);
        } else {
            submitInfo.put("Sports", false);
        }
        if (Fundraiser.isChecked()) {
            submitInfo.put("Fundraiser", true);
        } else {
            submitInfo.put("Fundraiser", false);
        }*/
        submitInfo.saveInBackground();
    }
    public void showStartTimePicker(View v) {
        timeMode = 0;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        TextView startTimeTextView = (TextView) findViewById(R.id.startTime);
        startTimeTextView.setTextColor(getResources().getColor(R.color.myTextSecondaryColor));
    }

    public void showStartDatePicker(View v) {
        dateMode = 0;
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        TextView startDateTextView = (TextView) findViewById(R.id.startDate);
        startDateTextView.setTextColor(getResources().getColor(R.color.myTextSecondaryColor));

    }
    public void clickOnEditText(View v){
        EditText clickedEditText = (EditText) v;
        clickedEditText.setText("");
        clickedEditText.setTextColor(getResources().getColor(R.color.myTextPrimaryColor));
    }
    public void exit(View v){
        this.finish();
    }

    public void onTimeRecieve(int hoursOfDay, int minute){
        String TimeSuffix;
        if(hoursOfDay > 12){
            hoursOfDay = hoursOfDay-12;
            TimeSuffix = "PM";
        }else{
            TimeSuffix = "AM";
        }

        if(hoursOfDay<10){
            sendingHour = "0" + hoursOfDay;
        }else {
        sendingHour = Integer.toString(hoursOfDay);

        }
        if(minute < 10){
            startTime1.setText(hoursOfDay + ":" + "0" + minute + " " + TimeSuffix);
            sendingMinutes = Integer.toString(minute);
            sendingMinutes = "0" + sendingMinutes;

        }else{
            startTime1.setText(hoursOfDay + ":" +  minute + " " + TimeSuffix);
            sendingMinutes = Integer.toString(minute);


        }
        System.out.println(sendingHour + ":" + sendingMinutes);



    }
    public void onDateReceive(int year, int month, int day){
        startDate1.setText((month+1) + "-" + day + "-" + year);
        if((month+1)<10)
        {
            sendingMonth = "0" + (month+1);
        }else {
            sendingMonth = Integer.toString(month + 1);
        }

        sendingYear = Integer.toString(year);

        if(day<10)
        {
            sendingDay = "0" + day;
        }else {
            sendingDay = Integer.toString(day);
        }
        System.out.println(sendingMonth + " " + sendingDay + " " + sendingYear);
    }
   }


