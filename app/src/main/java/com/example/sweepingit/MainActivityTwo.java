package com.example.sweepingit;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

    static class ButtonData {
        String pos;
        int value;
        ButtonData(String pos, int value)
        {
            this.pos = pos;
            this.value = value;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytwo_main);

        //cache textures
        final Drawable tileClicked = ContextCompat.getDrawable(this, R.drawable.tilejpgclicked);
        final Drawable tile1 = ContextCompat.getDrawable(this, R.drawable.tilejpg1);
        final Drawable tile2 = ContextCompat.getDrawable(this, R.drawable.tilejpg2);
        final Drawable tile3 = ContextCompat.getDrawable(this, R.drawable.tilejpg3);
        final Drawable tile4 = ContextCompat.getDrawable(this, R.drawable.tilejpg4);
        final Drawable tile5 = ContextCompat.getDrawable(this, R.drawable.tilejpg5);
        final Drawable tile6 = ContextCompat.getDrawable(this, R.drawable.tilejpg6);
        final Drawable tile7 = ContextCompat.getDrawable(this, R.drawable.tilejpg7);
        final Drawable tile8 = ContextCompat.getDrawable(this, R.drawable.tilejpg8);
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
            button.setTag(new ButtonData("N", i));
            for (int number : randomNumbers) {
                if (i == number) {
                    button.setText(String.valueOf("BOMB"));  // label buttons
                    //button.setTag("BOMB");
                    button.setTag(new ButtonData("BOMB", 9));
                }
            }

            // Set button background to borderless drawable
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.tilejpg));

            // Set button layout parameters with no margin
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setGravity(Gravity.FILL);

            button.setLayoutParams(params);


            //game logic
            button.setOnClickListener(v -> {
                Button btn = (Button) v;

                ButtonData tags = (ButtonData) btn.getTag();
                btn.setBackground(null);
                if(tags != null) {
                    Toast.makeText(this, "Šta si?: " + tags.pos + ", Indeks: " + tags.value, Toast.LENGTH_SHORT).show();
                    // Handle the tile logic based on the tag
                    switch (tags.value) {
                        case 0:
                            btn.setBackground(tileClicked); // Example tile
                            break;
                        case 1:
                            btn.setBackground(tile1);
                            break;
                        case 2:
                            btn.setBackground(tile2);
                            break;
                        case 3:
                            btn.setBackground(tile3);
                            break;
                        case 4:
                            btn.setBackground(tile4);
                            break;
                        case 5:
                            btn.setBackground(tile5);
                            break;
                        case 6:
                            btn.setBackground(tile6);
                            break;
                        case 7:
                            btn.setBackground(tile7);
                            break;
                        case 8:
                            btn.setBackground(tile8);
                            break;
                    }
                }
            });


            // Add button to the GridLayout
            gridLayout.addView(button);
        }
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            ButtonData data = (ButtonData) btn.getTag();
            if (data != null && !"BOMB".equals(data.pos)) {
                btn.setTag(new ButtonData("N", i));
                //oznaci ljevi zid
                if(i % 8 == 0) {
                    btn.setTag(new ButtonData("L", i));
                    //btn.setText(String.valueOf("L")); //debug
                }
                //oznaci desni zid
                if(!"L".equals(data.pos)) {
                    if((i - 7) % 8 == 0) {
                        btn.setTag(new ButtonData("R", i));
                        //btn.setText(String.valueOf("R")); //debug
                    }
                }
                //oznaci gornji zid
                if(i > 0 && i < 7 && !"BOMB".equals(data.pos)) {
                    btn.setTag(new ButtonData("G", i));
                    //btn.setText(String.valueOf("G")); //debug
                }
                //oznaci gornji zid
                if(i > 120 && i < 127 && !"BOMB".equals(data.pos)) {
                    btn.setTag(new ButtonData("D", i));
                    //btn.setText(String.valueOf("D")); //debug
                }

                //oznaci kuteve
                switch(i) {
                    case 0:
                        btn.setTag(new ButtonData("KGL", i));
                        break;
                    case 7:
                        btn.setTag(new ButtonData("KGD", i));
                        break;
                    case 120:
                        btn.setTag(new ButtonData("KDL", i));
                        break;
                    case 127:
                        btn.setTag(new ButtonData("KDD", i));
                        break;
                }

            }

        }
        //postavi brojeve oko bombi
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            ButtonData data = (ButtonData) btn.getTag();
            //btn.setText(String.valueOf(i));
            if (data != null && !"BOMB".equals(data.pos)) {
                //postavi brojeve za kuteve
                int counter = 0;
                switch(i) {
                    case 0:
                        if (((ButtonData) ((Button) gridLayout.getChildAt(1)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(8)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(9)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        btn.setTag(new ButtonData(data.pos, counter));
                        counter = 0;
                        break;
                    case 7:
                        if (((ButtonData) ((Button) gridLayout.getChildAt(6)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(14)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(15)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        btn.setTag(new ButtonData(data.pos, counter));
                        counter = 0;
                        break;
                    case 120:
                        if (((ButtonData) ((Button) gridLayout.getChildAt(112)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(113)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(121)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        btn.setTag(new ButtonData(data.pos, counter));
                        counter = 0;
                        break;
                    case 127:
                        if (((ButtonData) ((Button) gridLayout.getChildAt(118)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(119)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        if (((ButtonData) ((Button) gridLayout.getChildAt(126)).getTag()).pos.equals("BOMB")) {
                            counter++;
                        }
                        btn.setTag(new ButtonData(data.pos, counter));
                        counter = 0;
                        break;
                }
                //postavi brojeve oko bombi uz zid
                if ("G".equals(data.pos)) {
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+7)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+9)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    btn.setTag(new ButtonData(data.pos, counter));
                    counter = 0;
                }
                if ("D".equals(data.pos)) {
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-7)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-9)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    btn.setTag(new ButtonData(data.pos, counter));
                    counter = 0;
                }
                if ("L".equals(data.pos)) {
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-7)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+9)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    btn.setTag(new ButtonData(data.pos, counter));
                    counter = 0;
                }
                if ("R".equals(data.pos)) {
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-9)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+7)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    btn.setTag(new ButtonData(data.pos, counter));
                    counter = 0;
                }
                if ("N".equals(data.pos)) {
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-9)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-7)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i-1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+1)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+7)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+8)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    if (((ButtonData) ((Button) gridLayout.getChildAt(i+9)).getTag()).pos.equals("BOMB")) {
                        counter++;
                    }
                    btn.setTag(new ButtonData(data.pos, counter));
                    counter = 0;
                }
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
