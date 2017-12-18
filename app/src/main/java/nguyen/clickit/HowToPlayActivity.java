package nguyen.clickit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class HowToPlayActivity extends AppCompatActivity implements OnClickListener{

    //define widget variables
    private Button greenFalseButton, greenTrueButton,
        redFalseButton, redTrueButton,
        blueFalseButton, blueTrueButton,
        yellowFalseButton, yellowTrueButton;

    private ImageButton menuButton;

    //variable color with a default color value
    private String color = "#00FFFF";

    //creating sharedpreferences to get savedbackground and store savedscores
    private SharedPreferences savedBackground;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        //getting reference to the widget
        greenFalseButton = (Button) findViewById(R.id.greenFalseButton);
        greenTrueButton = (Button) findViewById(R.id.greenTrueButton);
        redFalseButton = (Button) findViewById(R.id.redFalseButton);
        redTrueButton = (Button) findViewById(R.id.redTrueButton);
        blueFalseButton = (Button) findViewById(R.id.blueFalseButton);
        blueTrueButton = (Button) findViewById(R.id.blueTrueButton);
        yellowFalseButton = (Button) findViewById(R.id.yellowFalseButton);
        yellowTrueButton = (Button) findViewById(R.id.yellowTrueButton);

        menuButton = (ImageButton) findViewById(R.id.menuButton);

        //setting onClickListener for menubutton
        menuButton.setOnClickListener(this);

        //getting background color value from sharedpreference
        savedBackground = getSharedPreferences("savedBackground", MODE_PRIVATE);
        color = savedBackground.getString("background", "#00FFFF");

        //setting stored value into current layout, activity_how_to_play
        LinearLayout howToPlay_view = (LinearLayout) findViewById(R.id.activity_how_to_play);
        howToPlay_view.setBackgroundColor(Color.parseColor(color));

        //setting the color for the buttons to true
        //displays the button color to user
        greenTrueButton.setPressed(true);
        redTrueButton.setPressed(true);
        blueTrueButton.setPressed(true);
        yellowTrueButton.setPressed(true);

        //setting buttons to false to disable user interaction
        greenFalseButton.setEnabled(false);
        greenTrueButton.setEnabled(false);
        redFalseButton.setEnabled(false);
        redTrueButton.setEnabled(false);
        blueFalseButton.setEnabled(false);
        blueTrueButton.setEnabled(false);
        yellowFalseButton.setEnabled(false);
        yellowTrueButton.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //menubutton starts new mainintent, mainactivity.class
            case R.id.menuButton:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
        }
    }
}
