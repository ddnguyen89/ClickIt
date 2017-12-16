package nguyen.clickit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    //define widget variables
    private ImageButton playButton, highscoreButton;

    //color variables for background
    private String white = "#FFFFFF";
    private String green = "#96FF8C";
    private String red = "#D73C3C";
    private String blue = "#7391FF";
    private String yellow = "#EFE373";
    private String colorDefault = "#00FFFF";

    private String color = "#00FFFF";

    //creating sharedpreference to store background values
    private SharedPreferences savedBackground;
    Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getting reference to widget
        playButton = (ImageButton) findViewById(R.id.playButton);
        highscoreButton = (ImageButton) findViewById(R.id.highscoreButton);

        //setting onClickListener for buttons
        playButton.setOnClickListener(this);
        highscoreButton.setOnClickListener(this);

        //getting background color value from sharedpreference
        savedBackground = getSharedPreferences("savedBackground", MODE_PRIVATE);
        color = savedBackground.getString("background", colorDefault);

        //setting stored value into current layout, activity_main
        LinearLayout main_view = (LinearLayout) findViewById(R.id.activity_main);
        main_view.setBackgroundColor(Color.parseColor(color));

    }

    //onClick method to display new intent
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //playbutton starts new playintent, playactivity.class
            case R.id.playButton:
                Intent playIntent = new Intent(this, PlayActivity.class);
                startActivity(playIntent);
                break;

            //highscorebutton starts new playintent, highscoreactivity.class
            case R.id.highscoreButton:
                Intent highscoreIntent = new Intent(this, HighscoreActivity.class);
                startActivity(highscoreIntent);
                break;
        }
    }

    //inflating menu and adding items to the action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //overflow menu items to change background color
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LinearLayout main_view = (LinearLayout) findViewById(R.id.activity_main);

        //for which ever item is selected, background color is changed
        //color will then be stored in sharedpreference
        //color will be pulled from other windows to set background
        switch(item.getItemId()) {
            case R.id.menu_white:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                main_view.setBackgroundColor(Color.parseColor(white));
                editor = savedBackground.edit();
                editor.putString("background", white).apply();
                return true;

            case R.id.menu_green:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                main_view.setBackgroundColor(Color.parseColor(green));
                editor = savedBackground.edit();
                editor.putString("background", green).apply();
                return true;

            case R.id.menu_red:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                main_view.setBackgroundColor(Color.parseColor(red));
                editor = savedBackground.edit();
                editor.putString("background", red).apply();
                return true;

            case R.id.menu_blue:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                main_view.setBackgroundColor(Color.parseColor(blue));
                editor = savedBackground.edit();
                editor.putString("background", blue).apply();
                return true;

            case R.id.menu_yellow:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                main_view.setBackgroundColor(Color.parseColor(yellow));
                editor = savedBackground.edit();
                editor.putString("background", yellow).apply();
                return true;

            case R.id.menu_default:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                main_view.setBackgroundColor(Color.parseColor(colorDefault));
                editor = savedBackground.edit();
                editor.putString("background", colorDefault).apply();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
