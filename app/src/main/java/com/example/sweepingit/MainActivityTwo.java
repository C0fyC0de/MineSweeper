package com.example.sweepingit;

import android.content.Intent;
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
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivityTwo extends Activity {

    static class ButtonData {
        String pos; //slovo
        int value; //jeli bomba ili broj
        int index; // index od 0 do 127
        boolean pressed; //button up = false, button pressed = true
        ButtonData(String pos, int value, int index, boolean pressed)
        {
            this.pos = pos;
            this.value = value;
            this.index = index;
            this.pressed = pressed;
        }
    }
    void recursiveDiscovery(int index, Drawable[] spriteArray, GridLayout gridLayout, AtomicInteger pressedTiles) {
        Button btn = (Button) gridLayout.getChildAt(index);
        ButtonData tags = (ButtonData) btn.getTag();
        if(!tags.pressed) {
            pressedTiles.getAndDecrement();
            Log.d("PRITISNUTE TIPKE", pressedTiles + " ");
            btn.setBackground(null);
            //Toast.makeText(this, "Slovo: " + tags.pos + ", Vrijednost: " + tags.value + ", Index: " + tags.index + ", Boolean: " + tags.pressed, Toast.LENGTH_SHORT).show();
            btn.setBackground(spriteArray[tags.value]);
            tags.pressed = true;
            if(tags.value == 0)
            {
                switch(tags.pos) {
                    case "N":
                        recursiveDiscovery(index-9, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-7, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+7, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+9, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "G":
                        recursiveDiscovery(index-1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+7, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+9, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "D":
                        recursiveDiscovery(index-1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-7, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-9, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "L":
                        recursiveDiscovery(index-8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-7, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+9, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "R":
                        recursiveDiscovery(index+8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index+7, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(index-9, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "KGL":
                        recursiveDiscovery(1, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(8, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(9, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "KGD":
                        recursiveDiscovery(6, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(14, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(15, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "KDL":
                        recursiveDiscovery(112, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(113, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(121, spriteArray, gridLayout, pressedTiles);
                        break;
                    case "KDD":
                        recursiveDiscovery(118, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(119, spriteArray, gridLayout, pressedTiles);
                        recursiveDiscovery(126, spriteArray, gridLayout, pressedTiles);
                        break;
                }
            }
            else if(tags.value == 9) {
                tags.pos = "B";
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    Button btn1 = (Button) gridLayout.getChildAt(i);
                    ButtonData data = (ButtonData) btn1.getTag();
                    data.pressed = true;
                    if("BOMB".equals(data.pos)) {
                        //Toast.makeText(this, "Slovo: " + data.pos, Toast.LENGTH_SHORT).show();
                        btn1.setBackground(ContextCompat.getDrawable(this, R.drawable.tilebomb));
                    }
                }
            }
            else if(pressedTiles.get() < 1) {
                for (int i = 0; i < gridLayout.getChildCount(); i++) {
                    Button btn1 = (Button) gridLayout.getChildAt(i);
                    ButtonData data = (ButtonData) btn1.getTag();
                    data.pressed = true;
                    if("BOMB".equals(data.pos)) {
                        //Toast.makeText(this, "Slovo: " + data.pos, Toast.LENGTH_SHORT).show();
                        btn1.setBackground(ContextCompat.getDrawable(this, R.drawable.flaggedmine));
                    }
                }
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitytwo_main);
        int bombCounter = 0;
        AtomicInteger pressedTiles = new AtomicInteger(128);
        //cache textures
        Drawable[] spriteArray = {ContextCompat.getDrawable(this, R.drawable.tilejpgclicked), ContextCompat.getDrawable(this, R.drawable.tilejpg1), ContextCompat.getDrawable(this, R.drawable.tilejpg2), ContextCompat.getDrawable(this, R.drawable.tilejpg3), ContextCompat.getDrawable(this, R.drawable.tilejpg4), ContextCompat.getDrawable(this, R.drawable.tilejpg5), ContextCompat.getDrawable(this, R.drawable.tilejpg6), ContextCompat.getDrawable(this, R.drawable.tilejpg7), ContextCompat.getDrawable(this, R.drawable.tilejpg8), ContextCompat.getDrawable(this, R.drawable.tilebombexploded)};
        // Reference the GridLayout
        GridLayout gridLayout = findViewById(R.id.gridLayout);

        Button smileybtn = findViewById(R.id.button2);
        smileybtn.setBackground(ContextCompat.getDrawable(this, R.drawable.tilesmiley));

        Button flagbtn = findViewById(R.id.button3);
        flagbtn.setBackground(ContextCompat.getDrawable(this, R.drawable.flagtilenotclicked));
        flagbtn.setTag(0);
        Drawable backgroundCheck = ContextCompat.getDrawable(this, R.drawable.flag);
        Drawable backgroundCheck2 = ContextCompat.getDrawable(this, R.drawable.tilejpg);

        Button cogbtn = findViewById(R.id.button4);
        cogbtn.setBackground(ContextCompat.getDrawable(this, R.drawable.tilecog));

        // Ensure no padding in GridLayout
        gridLayout.setPadding(0, 0, 0, 0);

        flagbtn.setOnClickListener(v -> {
            if((int) flagbtn.getTag() == 0) {
                flagbtn.setBackground(null);
                flagbtn.setBackground(ContextCompat.getDrawable(this, R.drawable.flagtileclicked));
                flagbtn.setTag(1);
            }
            else if((int) flagbtn.getTag() == 1) {
                flagbtn.setBackground(null);
                flagbtn.setBackground(ContextCompat.getDrawable(this, R.drawable.flagtilenotclicked));
                flagbtn.setTag(0);
            }
        });

        smileybtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivityTwo.class);
            finish(); // Finish the current activity
            startActivity(intent); // Start the activity again
        });

        int[] randomNumbers = generateUniqueRandomNumbers(19, 0, 127);
        Arrays.sort(randomNumbers);
        // Print the generated array
        for (int number : randomNumbers) {
            Log.d("activityTwo", number + " ");
        }

        bombCounter = randomNumbers.length;
        pressedTiles.set(pressedTiles.get() - bombCounter);
        // Set the total number of buttons
        for (int i = 0; i < 128; i++) {
            Button button = new Button(this);
            button.setTag(new ButtonData("N", i, i, false));
            for (int number : randomNumbers) {
                if (i == number) {
                    //button.setText(String.valueOf("BOMB"));  // label buttons
                    //button.setTag("BOMB");
                    button.setTag(new ButtonData("BOMB", 9, i, false));
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
                if(tags != null) {
                    //Toast.makeText(this, "Slovo: " + tags.pos + ", Vrijednost: " + tags.value + ", Index: " + tags.index + ", Boolean: " + tags.pressed, Toast.LENGTH_SHORT).show();
                    // Handle the tile logic based on the tag
                    if((int) flagbtn.getTag() == 1) {
                        if(tags.pressed && backgroundCheck.getConstantState().equals(btn.getBackground().getConstantState())) {
                            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.tilejpg));
                            tags.pressed = false;
                        }
                        else if(!tags.pressed && backgroundCheck2.getConstantState().equals(btn.getBackground().getConstantState())){
                            btn.setBackground(ContextCompat.getDrawable(this, R.drawable.flag));
                            tags.pressed = true;
                        }
                    }
                    else {
                        if(!tags.pressed) {
                            if(tags.value == 0) {
                                smileybtn.setBackground(ContextCompat.getDrawable(this, R.drawable.tilesmiley));
                            }
                            else if (tags.value != 9) {
                                smileybtn.setBackground(ContextCompat.getDrawable(this, R.drawable.tilenervoussmiley));
                            }
                            else {
                                smileybtn.setBackground(ContextCompat.getDrawable(this, R.drawable.tiledeath2));
                            }
                        }
                        recursiveDiscovery(tags.index, spriteArray, gridLayout, pressedTiles);
                    }
                    if (pressedTiles.get() < 1) {
                        smileybtn.setBackground(ContextCompat.getDrawable(this, R.drawable.smiley_cool));
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
                btn.setTag(new ButtonData("N", i, i, false));
                //oznaci ljevi zid
                if(i % 8 == 0) {
                    btn.setTag(new ButtonData("L", i, i, false));
                    //btn.setText(String.valueOf("L")); //debug
                }
                //oznaci desni zid
                if(!"L".equals(data.pos)) {
                    if((i - 7) % 8 == 0) {
                        btn.setTag(new ButtonData("R", i, i, false));
                        //btn.setText(String.valueOf("R")); //debug
                    }
                }
                //oznaci gornji zid
                if(i > 0 && i < 7 && !"BOMB".equals(data.pos)) {
                    btn.setTag(new ButtonData("G", i, i, false));
                    //btn.setText(String.valueOf("G")); //debug
                }
                //oznaci gornji zid
                if(i > 120 && i < 127 && !"BOMB".equals(data.pos)) {
                    btn.setTag(new ButtonData("D", i, i, false));
                    //btn.setText(String.valueOf("D")); //debug
                }

                //oznaci kuteve
                switch(i) {
                    case 0:
                        btn.setTag(new ButtonData("KGL", i, i, false));
                        break;
                    case 7:
                        btn.setTag(new ButtonData("KGD", i, i, false));
                        break;
                    case 120:
                        btn.setTag(new ButtonData("KDL", i, i, false));
                        break;
                    case 127:
                        btn.setTag(new ButtonData("KDD", i, i, false));
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
                        btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                        btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                        btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                        btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                    btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                    btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                    btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                    btn.setTag(new ButtonData(data.pos, counter, i, false));
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
                    btn.setTag(new ButtonData(data.pos, counter, i, false));
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
