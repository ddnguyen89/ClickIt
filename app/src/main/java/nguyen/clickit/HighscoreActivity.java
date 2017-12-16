package nguyen.clickit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HighscoreActivity extends AppCompatActivity implements OnClickListener {

    //define widget variables
    private TextView yourScoreTV, highscoreTV;
    private ImageButton playAgainButton, quitButton, resetScoreButton;

    //variables to store and get score and highscore
    private int userScore;
    private int highScore;

    //variable color with a default color value
    private String color = "#00FFFF";

    //creating sharedpreferences to get savedscores
    private SharedPreferences savedBackground;
    private SharedPreferences savedScores;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        //getting reference to the widger
        yourScoreTV = (TextView) findViewById(R.id.scoreTV);
        highscoreTV = (TextView) findViewById(R.id.highscoreTV);

        playAgainButton = (ImageButton) findViewById(R.id.playAgainButton);
        quitButton = (ImageButton) findViewById(R.id.quitButton);
        resetScoreButton = (ImageButton) findViewById(R.id.resetScoreButton);

        //setting onClickListener for buttons
        playAgainButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
        resetScoreButton.setOnClickListener(this);

        //getting savedscores from sharedpreferences
        savedScores = getSharedPreferences("savedScores", MODE_PRIVATE);

        userScore = savedScores.getInt("userScore", 0);
        highScore = savedScores.getInt("highScore", 0);

        //setting userscore and highscore textview from savedscores
        yourScoreTV.setText(""+userScore);
        highscoreTV.setText(""+highScore);

        //getting background color value from sharedpreference
        savedBackground = getSharedPreferences("savedBackground", MODE_PRIVATE);
        color = savedBackground.getString("background", "#00FFFF");

        //setting stored value into current layout, activity_highscore
        LinearLayout highscore_view = (LinearLayout) findViewById(R.id.activity_highscore);
        highscore_view.setBackgroundColor(Color.parseColor(color));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //playagainbutton starts new playintent, playactivity.class
            case R.id.playAgainButton:
                Intent playIntent = new Intent(this, PlayActivity.class);
                startActivity(playIntent);
                break;

            //quitbutton starts new mainintent, mainactivity.class
            case R.id.quitButton:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;

            //resetscorebutton resets the userscore and highscore
            case R.id.resetScoreButton:
                //updating values for userscore and highscore
                editor = savedScores.edit();
                editor.putInt("userScore", 0);
                editor.putInt("highScore", 0);

                editor.commit();

                userScore = savedScores.getInt("userScore", 0);
                highScore = savedScores.getInt("highScore", 0);

                //setting userscore and highscore textview from savedscores
                yourScoreTV.setText(""+userScore);
                highscoreTV.setText(""+highScore);

                break;
        }

    }
}
