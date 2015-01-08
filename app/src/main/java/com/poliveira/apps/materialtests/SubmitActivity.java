package com.poliveira.apps.materialtests;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.parse.ParseObject;

import java.io.File;


public class SubmitActivity extends ActionBarActivity {


    private Uri mImageCaptureUri;
    private ImageView mImageView;

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        final String [] items           = new String [] {"From Camera", "From SD Card"};
        ArrayAdapter<String> adapter  = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder     = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int item ) {
                if (item == 0) {
                    Intent intent    = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file        = new File(Environment.getExternalStorageDirectory(),
                            "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
                    mImageCaptureUri = Uri.fromFile(file);

                    try {
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    dialog.cancel();
                } else {
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        } );

        final AlertDialog dialog = builder.create();

        mImageView = (ImageView) findViewById(R.id.PreviewImage);

        ((Button) findViewById(R.id.fakebutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
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
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void clickOnTextView(View v){
        TextView clickedTextView = (TextView) v;
        clickedTextView.setText("");
        clickedTextView.setTextColor(getResources().getColor(R.color.myTextPrimaryColor));
    }
    public void exit(View v){
        this.finish();
    }
    public void clickOnAddImage(View v){
        Button fakeButton1 = (Button) findViewById(R.id.fakebutton);
        fakeButton1.performClick();
    }
}
