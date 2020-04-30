package com.martahrefs.toggledemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import es.dmoral.toasty.Toasty;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    private SwitchCompat mSwitchCompat;
    private IndicatorSeekBar mSeekBar;
    private RadioGroup mRadioGroup;
    private RadioButton mRadioYes;
    private RadioButton mRadioNo;
    private Spinner mSpinner;
    public static final String PREFS_NAME = "toggle_shared_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRadioGroup = findViewById(R.id.radio_group);
        mRadioYes = findViewById(R.id.button_yes);
        mRadioNo = findViewById(R.id.button_no);
        mSeekBar = findViewById(R.id.seekBar);
        mSwitchCompat = findViewById(R.id.switchCompat);
        mSpinner = findViewById(R.id.spinner_demo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.number_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        loadData();

        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Toast.makeText(MainActivity.this, "Switch ON!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Switch OFF!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               if(i==mRadioYes.getId()){
                   Toast.makeText(MainActivity.this, "Yes Kicks", Toast.LENGTH_SHORT).show();
               }else if(i==mRadioNo.getId()){
                   Toast.makeText(MainActivity.this,"No Way",Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void loadData() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        //Set the Switch state according to last save
        mSwitchCompat.setChecked(prefs.getBoolean("switch_check",false));
        mSeekBar.setProgress(prefs.getInt("final_value",0));
        mSpinner.setSelection(prefs.getInt("spinner_position",1));

        if(prefs.getInt("radio_id",0)==mRadioYes.getId()){
            mRadioYes.setChecked(true);
        }else if(prefs.getInt("radio_id",0)==mRadioNo.getId()){
            mRadioNo.setChecked(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveChanges();
    }

    private void saveChanges() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //Save the state of the Switch
        editor.putBoolean("switch_check",mSwitchCompat.isChecked());
        editor.putInt("final_value",mSeekBar.getProgress());
        editor.putInt("radio_id",mRadioGroup.getCheckedRadioButtonId());
        editor.putInt("spinner_position",mSpinner.getSelectedItemPosition());
        //Save changes Asynchronously
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                int position = mSpinner.getSelectedItemPosition();
                String stuff = mSpinner.getItemAtPosition(position).toString();
                Toast.makeText(this, stuff, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "World", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
