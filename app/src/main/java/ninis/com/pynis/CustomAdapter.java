package ninis.com.pynis;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import ninis.com.pynis.data.ClienPostData;
import ninis.com.pynis.viewholder.ClienPostViewHolder;

/**
 * Created by gypark on 2015. 12. 16..
 */
public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ClienPostData.ClienPostItem> items;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public void setData(ClienPostData data) {
        if( items == null )
            items = new ArrayList<>();

        if( !items.isEmpty() )
            items.clear();

        items = data.getItems();
        notifyDataSetChanged();
    }

    public void addData(ClienPostData data) {
        if( items != null ) {
            items.addAll(data.getItems());
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_clien_list_item, parent, false);

        ClienPostViewHolder viewHolder = new ClienPostViewHolder(root);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClienPostViewHolder clienPostViewHolder = (ClienPostViewHolder) holder;

        if( items != null ) {
            ClienPostData.ClienPostItem item = items.get(position);

            clienPostViewHolder.tvTitle.setText(item.getTitle());
            clienPostViewHolder.tvReplyCount.setText("reply : " + item.getReplyCount());

            // hasUserImage
            boolean hasUserImg = item.isHasImgID();
            if( hasUserImg ) {
                clienPostViewHolder.tvUser.setVisibility(View.GONE);
                clienPostViewHolder.sdvUser.setVisibility(View.VISIBLE);

                Uri uriImg = Uri.parse(item.getImgUrl());
                if( uriImg != null ) {
                    clienPostViewHolder.sdvUser.setImageURI(uriImg);
                }
            } else {
                clienPostViewHolder.tvUser.setVisibility(View.VISIBLE);
                clienPostViewHolder.sdvUser.setVisibility(View.GONE);

                clienPostViewHolder.tvUser.setText(item.getUser());
            }

            holder.itemView.setTag(item.getLink());

            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(viewToAnimate.getContext(), R.anim.up_from_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
