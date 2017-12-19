package nguyen.clickit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

public class PlayActivity extends AppCompatActivity implements OnClickListener {

    //define widget variables
    private TextView userScoreTV, timeTV;
    private Button greenButton, redButton, blueButton, yellowButton;
    private ImageButton quitButton;

    //variables used to store inputs of simon and user
    private int simon;
    private int user;

    //variables to store score and highscore
    private int userScore = 0;
    private int highScore;

    //variable color with a default color value
    private String color = "#00FFFF";

    //countdowntimer with timelimit for game
    CountDownTimer timer;
    private long timeLimit = 12000;

    //creating a handler to set a delay for when the game starts
    private Handler setDelay = new Handler();
    private Runnable startDelay;

    //creating sharedpreferences to get savedbackground and store savedValues
    private SharedPreferences savedValues;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //getting reference to the widget
        userScoreTV = (TextView) findViewById(R.id.scoreTV);
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

        //setting savedValues from sharedpreferences with userscore of 0;
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);

        //setting stored value into current layout, activity_play
        LinearLayout play_view = (LinearLayout) findViewById(R.id.activity_play);
        play_view.setBackgroundColor(Color.parseColor(color));

        //setting userscore value in sharedpreferences back to 0
        editor = savedValues.edit();
        editor.putInt("userScore", 0).apply();

        //setting highscore value from sharedpreferences
        highScore = savedValues.getInt("highScore", 0);

        //setting all buttons enabled to false
        //prevents click during delay
        greenButton.setEnabled(false);
        redButton.setEnabled(false);
        blueButton.setEnabled(false);
        yellowButton.setEnabled(false);
        quitButton.setEnabled(false);

        //create a new delay for when the game starts
        //after delay is complete, start game and set buttons enabled to true
        startDelay = new Runnable() {
            @Override
            public void run() {
                playGame();
                greenButton.setEnabled(true);
                redButton.setEnabled(true);
                blueButton.setEnabled(true);
                yellowButton.setEnabled(true);
                quitButton.setEnabled(true);
            }
        };
        setDelay.postDelayed(startDelay, 700);
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
                if(timer != null) {
                    timer.cancel();
                }
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
        //if userscore is greater than highScore, update highScore
        //store scores in savedvaluse
        if (simon == user) {
            matchStatement = true;
            userScore += 1;
            userScoreTV.setText("" + userScore);

            saveScores();
        } else {
            saveScores();
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
        timer = new CountDownTimer(timeLimit, 100) {

            //update the time textview to show user how much time they have left
            @Override
            public void onTick(long millisUntilFinished) {
                timeTV.setText("" + millisUntilFinished / 100);
            }

            //when time is finished,
            //save the scores with sharedpreferences
            //cancel the timer
            //starts new highScoreintent, highScoreactivity.class
            @Override
            public void onFinish() {
                //call savescores function
                saveScores();

            if(isFinishing())
                if(timer != null) {
                    timer.cancel();
                }
                Intent highScoreIntent = new Intent(getApplicationContext(), HighscoreActivity.class);
                startActivity(highScoreIntent);
            }
        }.start();
    }

    //save score values for shared preferences
    public void saveScores(){
        editor = savedValues.edit();
        editor.putInt("userScore", userScore);
        editor.putInt("highScore", highScore);

        //if userscore is greater than highscore, set highscore to userscore and save it
        if(userScore > highScore) {
            highScore = userScore;
            editor.putInt("highScore", highScore);
        }
        editor.apply();
    }

    //if the user matches simon's action, give simon a new number and light it up
    //if not, cancel the timer and start new highScoreintent, highScoreactivity.class
    public void checked() {
        if (checkMatch(simon, user) == true) {
            randomNumber();
            simonFlash(simon);
        } else {
            if(timer != null) {
                timer.cancel();
            }
            Intent highScoreIntent = new Intent(this, HighscoreActivity.class);
            startActivity(highScoreIntent);
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

    @Override
    protected void onPause() {
        super.onPause();

        //if there is a timer, cancel it
        if(timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //getting background color value from sharedpreference
        color = savedValues.getString("background", "#00FFFF");

        //setting stored value into current layout, activity_play
        LinearLayout play_view = (LinearLayout) findViewById(R.id.activity_play);
        play_view.setBackgroundColor(Color.parseColor(color));

        //onresume, if there is a timer, cancel it and go to highscoreactivity
        if(timer != null) {
            timer.cancel();

            Intent highScoreIntent = new Intent(this, HighscoreActivity.class);
            startActivity(highScoreIntent);
        }
    }
}

