package nguyen.clickit;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabActivity extends ActivityGroup {

    //defining widget tabhost
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);

        //getting widget variable tabhost and calling its setup
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup(this.getLocalActivityManager());

        //creating a new tabspec for the tabhost
        TabHost.TabSpec howToPlayTab = tabHost.newTabSpec("How To Play");
        TabHost.TabSpec aboutTab = tabHost.newTabSpec("About");

        //setting the indicator and content for the howtoplaytab and abouttab
        howToPlayTab.setIndicator("How To Play");
        howToPlayTab.setContent(new Intent(this, HowToPlayActivity.class));

        aboutTab.setIndicator("About");
        aboutTab.setContent(new Intent(this, AboutActivity.class));

        //adding the two tabspec tabs (howtoplaytab and abouttab) to tabhost
        tabHost.addTab(howToPlayTab);
        tabHost.addTab(aboutTab);
    }
}
