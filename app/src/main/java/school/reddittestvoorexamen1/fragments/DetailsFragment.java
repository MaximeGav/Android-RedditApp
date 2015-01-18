package school.reddittestvoorexamen1.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import school.reddittestvoorexamen1.Post;
import school.reddittestvoorexamen1.R;
import school.reddittestvoorexamen1.utils.tasks.ImageDownloadTask;

public class DetailsFragment extends Fragment {

    private ImageView imageView;
    private TextView tvTitle;
    private Post p;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_details, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.iv_detail_icon);
        tvTitle = (TextView) rootView.findViewById(R.id.tv_detail_title);

        p = (Post) getArguments().getSerializable("selectedPost");

        if (p == null && savedInstanceState != null) {
            p = (Post) savedInstanceState.getSerializable("selectedPost");
        }

        new ImageDownloadTask(imageView).execute(p.getFullImg());
        tvTitle.setText(p.getTitle());

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("selectedPost", p);
    }
}
