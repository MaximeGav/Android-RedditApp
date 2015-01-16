package school.reddittestvoorexamen1.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

import school.reddittestvoorexamen1.R;
import school.reddittestvoorexamen1.fragments.MainFragment;


public class MainActivity extends ActionBarActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Alles voor fragmentmanager
        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        fragmentTransaction.replace(R.id.content_frame, mainFragment, "MainFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1 ){
            getFragmentManager().popBackStack();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Druk nogmaals op back om de app te sluiten.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

}
