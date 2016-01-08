package ninis.com.pynis;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ninis.com.pynis.data.ClienPostData;
import ninis.com.pynis.viewholder.ClienPostViewHolder;

/**
 * Created by gypark on 2015. 12. 16..
 */
public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ClienPostData.ClienPostItem> items;

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
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_clien_list_item, null);

        ClienPostViewHolder viewHolder = new ClienPostViewHolder(root);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ClienPostViewHolder clienPostViewHolder = (ClienPostViewHolder) holder;

        if( items != null ) {
            ClienPostData.ClienPostItem item = items.get(position);

            clienPostViewHolder.tvTitle.setText(item.getTitle());

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
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
