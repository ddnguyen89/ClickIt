package nguyen.clickit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class PlayActivity extends AppCompatActivity implements OnClickListener {

    //define widget variables
    private TextView scoreTV, timeTV;
    private Button greenButton, redButton, blueButton, yellowButton;
    private ImageButton quitButton;

    private int simon;
    private int user;

    private int score = 0;
    private int highscore;

    private String color = "#00FFFF";

    //countdowntimer with timelimit for game
    CountDownTimer timer;
    private int timeLimit = 12000;

    //creating sharedpreferences to get savedbackground and store savedscores
    private SharedPreferences savedBackground;
    private SharedPreferences savedScores;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //getting reference to the widget
        scoreTV = (TextView) findViewById(R.id.scoreTV);
        timeTV = (TextView) findViewById(R.id.timeTV);

        greenButton = (Button) findViewById(R.id.greenButton);
        redButton = (Button) findViewById(R.id.redButton);
        blueButton = (Button) findViewById(R.id.blueButton);
        yellowButton = (Button) findViewById(R.id.yellowButton);

        quitButton = (ImageButton) findViewById(R.id.quitButton);

        //setting onClickListener for buttons
        greenButton.setOnClickListener(this);
        redButton.setOnClickListener(this);
        blueButton.setOnClickListener(this);
        yellowButton.setOnClickListener(this);

        quitButton.setOnClickListener(this);

        playGame();

        //getting background color value from sharedpreference
        savedBackground = getSharedPreferences("savedBackground", MODE_PRIVATE);
        color = savedBackground.getString("background", "#00FFFF");

        //setting stored value into current layout, activity_main
        LinearLayout play_view = (LinearLayout) findViewById(R.id.activity_play);
        play_view.setBackgroundColor(Color.parseColor(color));



        //setting savedscores from sharedpreferences with userscore of 0;
        savedScores = getSharedPreferences("savedScores", MODE_PRIVATE);
        editor = savedScores.edit();
        editor.putInt("userScore", 0).commit();

        highscore = savedScores.getInt("highScore", 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //if user hits greenbutton, user is set to 1, check for match
            case R.id.greenButton:
                user = 1;
                checked();
                break;

            //if user hits redbuttoon, user is set to 2, check for match
            case R.id.redButton:
                user = 2;
                checked();
                break;

            //if user hits bluebutton, user is set to 3, check for match
            case R.id.blueButton:
                user = 3;
                checked();
                break;

            //if user hits yellowbutton, user is set to 4, check for match
            case R.id.yellowButton:
                user = 4;
                checked();
                break;

            //if user hits quitbutton, cancel countdowntimer
            //starts new mainintent, mainactivity.class
            case R.id.quitButton:
                timer.cancel();
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }

    //function to match user action with simon
    public boolean checkMatch(int simon, int user) {
        boolean matchStatement;

        //if user input matches simon's input
        //add 1 to score, update score
        //if userscore is greater than highscore, update highscore
        //store scores in savedvaluse
        if (simon == user) {
            matchStatement = true;
            score += 1;
            scoreTV.setText(""+score);

            editor = savedScores.edit();
            editor.putInt("userScore", score);
            editor.putInt("highScore", highscore);

            if(score > highscore) {
                highscore = score;
                Log.d("userScore", ""+score);
                Log.d("Highscore", ""+highscore);
                editor.putInt("highScore", score);
            }
            editor.commit();

        } else {
            matchStatement = false;
        }

        return matchStatement;
    }

    //function to create and add random number from 1-4 to simon
    public void randomNumber() {
        final int min = 1;
        final int max = 4;

        int randomNumber = new Random().nextInt((max - min) + 1) + min;

        //if the random number created was the same as the previous number,
        //create one until it is not the same
        while (randomNumber == simon) {
            randomNumber = new Random().nextInt((max - min) + 1) + min;
        }

        //make simon equal to the random number
        simon = randomNumber;
    }

    //start the game
    public void playGame() {

        //first add random number to simon
        randomNumber();

        //make the simon light up the button
        simonFlash(simon);

        //create countdowntimer for the game
        timer = new CountDownTimer(timeLimit,100) {

            //update the time textview to show user how much time they have left
            @Override
            public void onTick(long millisUntilFinished) {
                timeTV.setText(""+millisUntilFinished/100);
            }

            //when time is finished,
            //save the scores with sharedpreferences
            //cancel the timer
            //starts new highscoreintent, highscoreactivity.class
            @Override
            public void onFinish() {
                editor = savedScores.edit();
                editor.putInt("userScore", score);
                editor.putInt("highScore", highscore);

                if(score > highscore) {
                    highscore = score;
                    Log.d("userScore", ""+score);
                    Log.d("Highscore", ""+highscore);
                    editor.putInt("highScore", score);
                }

                editor.commit();

                if(isFinishing())
                    timer.cancel();

                Intent highscoreIntent = new Intent(getApplicationContext(), HighscoreActivity.class);
                startActivity(highscoreIntent);
            }
        }.start();
    }

    //if the user matches simon's action, give simon a new number and light it up
    //if not, cancel the timer and start new highscoreintent, highscoreactivity.class
    public void checked() {
        if (checkMatch(simon, user) == true) {
            randomNumber();
            simonFlash(simon);
        } else {
            timer.cancel();

            Intent highscoreIntent = new Intent(this, HighscoreActivity.class);
            startActivity(highscoreIntent);
        }
    }

    //lights up the buttons depending on what number simon is
    public void simonFlash(int simon) {
        if (simon == 1) {
            greenButton.setPressed(true);
        } else if (simon == 2) {
            redButton.setPressed(true);
        } else if (simon == 3) {
            blueButton.setPressed(true);
        } else if (simon == 4) {
            yellowButton.setPressed(true);
        }
    }
}

