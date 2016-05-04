package com.vic.hackernew.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vic.hackernew.CommentDetailFragment;
import com.vic.hackernew.Model.Comment;
import com.vic.hackernew.R;
import com.vic.hackernew.Utils.Constant;
import com.vic.hackernew.Utils.DateTimeFunction;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.author.setText(comment.getBy());
        holder.content.setText(Html.fromHtml(comment.getText()));
        holder.time.setText(DateTimeFunction.formatDateTime(comment.getTime()));

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        comments.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Comment> list) {
        comments.addAll(list);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView author, content, time;
        Button reply;
        View comment_View;


        public ViewHolder(View view) {
            super(view);
            author = (TextView) view.findViewById(R.id.by);
            content = (TextView) view.findViewById(R.id.content);
            time = (TextView) view.findViewById(R.id.time);
            reply = (Button) view.findViewById(R.id.reply);

            comment_View = view.findViewById(R.id.comment_View);

            reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                        Comment comment = comments.get(getLayoutPosition());
                        if (comment.getKids() != null) {

                            Bundle arguments = new Bundle();
                            arguments.putParcelable(Constant.TAG_COMMENT, comment);

                            CommentDetailFragment fragment = new CommentDetailFragment();
                            fragment.setArguments(arguments);

                            ((FragmentActivity) context).getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.topStory_detail, fragment, "commentDetail").addToBackStack(null)
                                    .commit();

                        } else {
                            Toast.makeText(context, "No reply for this comment", Toast.LENGTH_SHORT).show();
                        }

                }
            });


        }

        @Override
        public String toString() {
            return super.toString() + " '" + author.getText() + "'";
        }
    }

}
