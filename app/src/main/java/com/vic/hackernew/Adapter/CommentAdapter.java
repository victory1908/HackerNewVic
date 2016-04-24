package com.vic.hackernew.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vic.hackernew.Model.Comment;
import com.vic.hackernew.Model.TopStory;
import com.vic.hackernew.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by vic on 22-Apr-16.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    Context context;
    List<Comment> comments;


    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.author.setText(comment.getAuthor());
        holder.content.setText(Html.fromHtml(comment.getText()));

        holder.time.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").
                format(new Date(comment.getTime() * 1000)));


//        holder.time.setText(new DateFormat.getDateTimeInstance(new Date(comment.getTime() * 1000)));

//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mTwoPane) {
//                    Bundle arguments = new Bundle();
//                    arguments.putInt(ItemDetailFragment.ARG_ITEM_ID, holder.comment.getId());
//                    ItemDetailFragment fragment = new ItemDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.item_detail_container, fragment)
//                            .commit();
//                } else {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, ItemDetailActivity.class);
//                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.comment.getId());
//
//                    context.startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView author;
        public final TextView content;
        public final TextView time;
        public final Button url;
        public final View comment_View;
        public TopStory topStory;

        public ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.author);
            content = (TextView) view.findViewById(R.id.content);
            time = (TextView) view.findViewById(R.id.time);
            url = (Button) view.findViewById(R.id.url);

            comment_View = view.findViewById(R.id.comment_View);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }
}
