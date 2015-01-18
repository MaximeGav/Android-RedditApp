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
    private boolean mDualPane;
    private JSONParser jsonParser;
    private OnPostSelectedListener onPostSelectedListener;

    public MainFragment() {
        posts = new ArrayList<Post>();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                onPostSelectedListener.onPostSelected((Post) adapterView.getItemAtPosition(position), mDualPane);
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
            try {
                jsonParser = new JSONParser(result);
                posts = jsonParser.parsePosts();
                myAdapter.addAll(posts);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
