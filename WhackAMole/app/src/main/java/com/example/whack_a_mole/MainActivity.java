package com.example.whack_a_mole;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button[] buttons;
    private TextView counterText;
    private TextView catcher;
    private Button endButton;
    private int score = 0;
    private Handler handler;
    private boolean isGameRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[9];
        buttons[0] = findViewById(R.id.button);
        buttons[1] = findViewById(R.id.button2);
        buttons[2] = findViewById(R.id.button3);
        buttons[3] = findViewById(R.id.button4);
        buttons[4] = findViewById(R.id.button5);
        buttons[5] = findViewById(R.id.button6);
        buttons[6] = findViewById(R.id.button7);
        buttons[7] = findViewById(R.id.button8);
        buttons[8] = findViewById(R.id.button9);

        endButton = findViewById(R.id.button10);
        counterText = findViewById(R.id.textView);
        catcher = findViewById(R.id.moleCatcher);
        handler = new Handler();

        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isGameRunning) {
                        Button clickedButton = (Button) view;
                        if (clickedButton.getText().equals("*")) {
                            score++;
                            counterText.setText(String.valueOf(score));
                            catcher.setText("You caught the mole!");
                            clickedButton.setText("");
                        } else {
                            catcher.setText("Too bad!");
                        }
                    }
                }
            });
        }

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isGameRunning = false;
                if (score == 0) {
                    counterText.setText("0");
                    endButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    endButton.setText("Would you like to play again?");
                    askToPlayAgain();
                } else {
                    Toast.makeText(MainActivity.this, "Game over! Your score: " + score, Toast.LENGTH_SHORT).show();
                }
                score = 0;
            }
        });

        startGame();
    }

    private void startGame() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isGameRunning) {
                    resetButtons();
                    showMole();
                    handler.postDelayed(this, 1000); // Change the delay as needed for the speed of the game
                }
            }
        }, 1000); // Change the initial delay as needed
    }

    private void resetButtons() {
        for (Button button : buttons) {
            button.setText("");
        }
    }

    private void showMole() {
        Random random = new Random();
        int randomButtonIndex = random.nextInt(9);
        buttons[randomButtonIndex].setText("*");
    }

    private void askToPlayAgain() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Would you like to play again?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        isGameRunning = true;
                        endButton.setBackgroundColor(getResources().getColor(android.R.color.holo_purple)); // Set back to the original color
                        endButton.setText("End game");
                        startGame();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}