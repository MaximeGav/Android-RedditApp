package school.reddittestvoorexamen1.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import school.reddittestvoorexamen1.Post;
import school.reddittestvoorexamen1.R;
import school.reddittestvoorexamen1.utils.tasks.ImageDownloadTask;

public class MyAdapter extends ArrayAdapter<Post> {

    public MyAdapter(Context context, ArrayList<Post> values) {
        super(context, R.layout.lv_row, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View v = inflater.inflate(R.layout.lv_row, parent, false);

        Post post = getItem(position);

        String shortTitle = post.getTitle();
        int maxChars = 50;

        //Slechts x aantal karakters uit titel tonen
        if (post.getTitle().length() > maxChars) {
            shortTitle = shortTitle.substring(0,maxChars) + "...";
        }

        ImageView thumbnail = (ImageView) v.findViewById(R.id.iv_icon);
        TextView firstLine = (TextView) v.findViewById(R.id.tv_firstLine);
        TextView secondLine = (TextView) v.findViewById(R.id.tv_secondLine);
        TextView score = (TextView) v.findViewById(R.id.tv_score);

        new ImageDownloadTask(thumbnail).execute(post.getThumbnail());
        //firstLine.setText(post.getTitle());
        firstLine.setText(shortTitle);
        secondLine.setText(post.getSecondLine());
        score.setText(post.getScore() + "");

        return v;
    }
}
