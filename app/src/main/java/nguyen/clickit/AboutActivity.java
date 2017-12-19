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

public class AboutActivity extends AppCompatActivity implements OnClickListener {

    //define widget variables
    private ImageButton menuButton;

    //variables to store score and highscore
    private int score = 0;
    private int highscore;

    //variable color with a default color value
    private String color = "#00FFFF";

    //creating sharedpreferences to get savedValues and store savedscores
    private SharedPreferences savedValues;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //getting reference to widget
        menuButton = (ImageButton) findViewById(R.id.menuButton);

        //setting onClickListener for menubutton
        menuButton.setOnClickListener(this);

        //getting background color value from sharedpreference
        savedValues = getSharedPreferences("savedValues", MODE_PRIVATE);

        //setting stored value into current layout, activity_how_to_play
        LinearLayout about_view = (LinearLayout) findViewById(R.id.activity_about);
        about_view.setBackgroundColor(Color.parseColor(color));
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

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getting background color value from sharedpreference
        color = savedValues.getString("background", "#00FFFF");

        //setting stored value into current layout, activity_about
        LinearLayout about_view = (LinearLayout) findViewById(R.id.activity_about);
        about_view.setBackgroundColor(Color.parseColor(color));
    }
}
