package nguyen.clickit;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class TabActivity extends ActivityGroup {

    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_host);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup(this.getLocalActivityManager());

        TabHost.TabSpec howToPlayTab = tabHost.newTabSpec("How To Play");
        TabHost.TabSpec aboutTab = tabHost.newTabSpec("About");

        howToPlayTab.setIndicator("How To Play");
        howToPlayTab.setContent(new Intent(this, HowToPlayActivity.class));

        aboutTab.setIndicator("About");
        aboutTab.setContent(new Intent(this, AboutActivity.class));

        tabHost.addTab(howToPlayTab);
        tabHost.addTab(aboutTab);
    }
}
