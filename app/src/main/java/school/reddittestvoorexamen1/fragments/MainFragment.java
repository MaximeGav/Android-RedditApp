package school.reddittestvoorexamen1.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

import school.reddittestvoorexamen1.OnPostSelectedListener;
import school.reddittestvoorexamen1.adapters.MyAdapter;
import school.reddittestvoorexamen1.OnPostListener;
import school.reddittestvoorexamen1.Post;
import school.reddittestvoorexamen1.R;
import school.reddittestvoorexamen1.utils.constants.AppConstants;
import school.reddittestvoorexamen1.utils.json.JSONParser;
import school.reddittestvoorexamen1.utils.tasks.DownloadTask;

public class MainFragment extends Fragment implements OnPostListener {

    private ArrayList<Post> posts;
    private MyAdapter myAdapter;
    //private FragmentManager fragmentManager;
    private boolean mDualPane;
    private JSONParser jsonParser;
    private OnPostSelectedListener onPostSelectedListener;

    public MainFragment() {
        //Posts mag nie null zijn anders ebde een error in uw oncreateview
        posts = new ArrayList<Post>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Eerst gaan we de JSON data ophalen met de posts in
        //Als de data gedownload is zetten we een observer pattern die hier terugkomt vanuit de
        //Downloadtask
        DownloadTask task = new DownloadTask(getActivity());
        task.setOnPostListener(this);
        task.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        myAdapter = new MyAdapter(this.getActivity(), posts); // <-- hier dus
        ListView lv_posts = (ListView) rootView.findViewById(R.id.lv_posts);
        lv_posts.setAdapter(myAdapter);

        View detailsFrame = getActivity().findViewById(R.id.details);
        mDualPane = (detailsFrame != null) && (detailsFrame.getVisibility() == View.VISIBLE);

        lv_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Post selected = (Post) adapterView.getItemAtPosition(position);
                onPostSelectedListener.onPostSelected((Post) adapterView.getItemAtPosition(position), mDualPane);

                /*Bundle bundle = new Bundle();
                bundle.putSerializable("selectedPost", selected);

                //Stuur data door
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);

                //Open de andere fragment
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (mDualPane) {
                    //Dus 2 fragmenten zichtbaar
                    fragmentTransaction.replace(R.id.details, detailsFragment, "PostDetails");
                } else {
                    fragmentTransaction.replace(R.id.content_frame, detailsFragment, "PostDetails");
                }

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onPostSelectedListener = (OnPostSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnPostSelectedListener");
        }
    }

    @Override
    public void onPostDownloading() {

    }

    @Override
    public void onPostReceived(boolean success, String result) {
        if (success) {
            //Als de GET voltooid is , bevat result een json object in string formaat
            //Die gaan we nu parsen naar de objecten
            //Hier alle posts ophalen
            try {
                jsonParser = new JSONParser(result);
                posts = jsonParser.parsePosts();
                //ArrayList is dynamisch dus kunnen we via de adapter posts toevoegen
                //Array is een fixed size, arraylist niet
                myAdapter.addAll(posts);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
