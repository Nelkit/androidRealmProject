package chakalon.com.formofflineexample.Helpers;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Utils {

    public static void bindUpButtonAsBack(final AppCompatActivity pActivity, Toolbar pTb){
        if (pTb != null) {
            pActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            pActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            pTb.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pActivity.onBackPressed();
                }
            });
        }
    }
}
