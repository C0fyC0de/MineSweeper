package com.example.sweepingit;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.app.Activity;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivityTwo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytwo_main);

        // Reference the GridLayout
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        // Ensure no padding in GridLayout
        gridLayout.setPadding(0, 0, 0, 0);



        int[] randomNumbers = generateUniqueRandomNumbers(19, 0, 128);
        Arrays.sort(randomNumbers);
        // Print the generated array
        for (int number : randomNumbers) {
            Log.d("activityTwo", number + " ");
        }


        // Set the total number of buttons
        for (int i = 0; i < 128; i++) {
            Button button = new Button(this);
            for (int number : randomNumbers) {
                if (i == number) {
                    button.setText(String.valueOf("BOMB"));  // label buttons
                    button.setTag("BOMB");
                }
            }

            // Set button background to borderless drawable
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.tile));

            // Set button layout parameters with no margin
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setGravity(Gravity.FILL);

            button.setLayoutParams(params);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click here
                    button.setBackground(ContextCompat.getDrawable(MainActivityTwo.this, R.drawable.tileclicked));
                    Toast.makeText(MainActivityTwo.this, "Button " + button.getTag() + " clicked!", Toast.LENGTH_SHORT).show();
                }
            });

            // Add button to the GridLayout
            gridLayout.addView(button);
        }
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);

            if (!"BOMB".equals(btn.getTag())) {
                btn.setTag(i);
                if(i == (Integer) btn.getTag()) {  //debug statement
                    btn.setText(String.valueOf(i));
                }

                //oznaci ljevi zid
                if(i % 8 == 0) {
                    btn.setTag("L");
                    //btn.setText(String.valueOf("L")); //debug
                }
                //oznaci desni zid
                if(!"L".equals(btn.getTag())) {
                    if((i - 7) % 8 == 0) {
                        btn.setTag("R");
                        //btn.setText(String.valueOf("R")); //debug
                    }
                }
                //oznaci gornji zid
                if(i > 0 && i < 7 && !"BOMB".equals(btn.getTag())) {
                    btn.setTag("G");
                    //btn.setText(String.valueOf("G")); //debug
                }
                //oznaci gornji zid
                if(i > 120 && i < 127 && !"BOMB".equals(btn.getTag())) {
                    btn.setTag("D");
                    //btn.setText(String.valueOf("D")); //debug
                }

                //oznaci kuteve
                switch(i) {
                    case 0:
                        btn.setTag("KGL");
                        break;
                    case 7:
                        btn.setTag("KGD");
                        break;
                    case 120:
                        btn.setTag("KDL");
                        break;
                    case 127:
                        btn.setTag("KDD");
                        break;
                }

            }

        }
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            //btn.setText(String.valueOf(i));
            switch(i) {
                case 0:
                    int counter = 0;
                    if("BOMB".equals(((Button) gridLayout.getChildAt(1)).getTag())) {
                        counter++;
                    }
                    if("BOMB".equals(((Button) gridLayout.getChildAt(8)).getTag())) {
                        counter++;
                    }
                    if("BOMB".equals(((Button) gridLayout.getChildAt(9)).getTag())) {
                        counter++;
                    }
                    btn.setTag(counter);
                    break;
                case 7:
                    btn.setTag("KGD");
                    break;
                case 120:
                    btn.setTag("KDL");
                    break;
                case 127:
                    btn.setTag("KDD");
                    break;
            }
        }
    }

    private static int[] generateUniqueRandomNumbers(int count, int min, int max) {
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();

        // Generate unique random numbers
        while (uniqueNumbers.size() < count) {
            int randomNumber = random.nextInt(max - min + 1) + min;  // inclusive of both min and max
            uniqueNumbers.add(randomNumber);
        }

        // Convert Set to Array
        int[] result = new int[count];
        int i = 0;
        for (int number : uniqueNumbers) {
            result[i++] = number;
        }

        return result;
    }
}
