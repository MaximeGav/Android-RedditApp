package school.reddittestvoorexamen1.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

import school.reddittestvoorexamen1.OnPostSelectedListener;
import school.reddittestvoorexamen1.Post;
import school.reddittestvoorexamen1.R;
import school.reddittestvoorexamen1.fragments.DetailsFragment;
import school.reddittestvoorexamen1.fragments.MainFragment;

public class MainActivity extends ActionBarActivity implements OnPostSelectedListener {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            Toast.makeText(this, "Push twice to close the app.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public void onPostSelected(Post post, boolean dualPane) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedPost", post);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        if (dualPane) {
            //So splitscreen with 2 fragments
            fragmentTransaction.replace(R.id.details, detailsFragment, "PostDetails");
        } else {
            fragmentTransaction.replace(R.id.content_frame, detailsFragment, "PostDetails");
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
