package com.example.cs408lab3;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String onScreen = "";
    private String currentNum = "";
    private String chosenOperator = "";
    private BigDecimal leftNumber = null;
    private BigDecimal rightNumber = null;
    private BigDecimal result;
    private boolean hasTwoOperands = false;
    private boolean hasDecimal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void numClicked(View v){
        String buttonText = ((Button) v).getText().toString();
        onScreen = onScreen.concat(buttonText);
        currentNum = currentNum.concat(buttonText);
        TextView t = (TextView) findViewById(R.id.textView);
        t.setText(onScreen);
        if (leftNumber != null) {
            hasTwoOperands = true;
        }
    }

    public void operatorClicked(View v){
        if (chosenOperator.equals("") && !currentNum.equals("")) {
            TextView t = (TextView) findViewById(R.id.textView);
            String buttonText = ((Button) v).getText().toString();
            chosenOperator = buttonText;
            if (chosenOperator.equals("\u221A")){
                if (leftNumber == null) {
                    leftNumber = new BigDecimal(currentNum);
                }
                double temp = leftNumber.doubleValue();
                temp = Math.pow(temp, 0.5);
                leftNumber = new BigDecimal(temp);
                currentNum = leftNumber.toString();

                onScreen = leftNumber.toString();
                t.setText(onScreen);
                chosenOperator = "";
            }
            else {
                onScreen = onScreen.concat(" " + buttonText + " ");
                if (leftNumber == null) {
                    leftNumber = new BigDecimal(currentNum);
                }
                currentNum = "";
                hasDecimal = false;
                t.setText(onScreen);
            }
        }
        else{
            Toast toast= Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void clearClicked(View v){
        TextView t = (TextView) findViewById(R.id.textView);
        onScreen = "";
        t.setText(onScreen);
        leftNumber = null;
        rightNumber = null;
        currentNum = "";
        chosenOperator = "";
        hasTwoOperands = false;
        hasDecimal = false;
    }

    public void decimalClicked(View v){
        if (!hasDecimal) {
            hasDecimal = true;
            TextView t = (TextView) findViewById(R.id.textView);
            currentNum = currentNum.concat(".");
            onScreen = onScreen.concat(".");
        }
        else{
            Toast toast= Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void signClicked(View v){
        BigDecimal x = new BigDecimal(currentNum);
        x = x.negate();
        currentNum = x.toString();
    }

    public void equalsClicked(View v){
        if (hasTwoOperands && chosenOperator != null){
            TextView t = (TextView) findViewById(R.id.textView);
            rightNumber = new BigDecimal(currentNum);

            switch(chosenOperator){
                case "+":
                    result = leftNumber.add(rightNumber);
                    break;
                case "-":
                    result = leftNumber.subtract(rightNumber);
                    break;
                case "x":
                    result = leftNumber.multiply(rightNumber);
                    break;
                case "÷":
                    result = leftNumber.divide(rightNumber);
                    break;
                case "%":
                    result = leftNumber.remainder(rightNumber);
                    break;
                default:
                    result = BigDecimal.ZERO;
                    Toast toast= Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT);
                    toast.show();
            }
            leftNumber = result;
            onScreen = leftNumber.toString();
            hasTwoOperands = false;
            hasDecimal = false;
            currentNum = leftNumber.toString();
            chosenOperator = "";
            t.setText(onScreen);

        }
        else{
            Toast toast= Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
