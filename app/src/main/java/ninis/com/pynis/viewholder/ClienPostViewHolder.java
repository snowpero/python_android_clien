package ninis.com.pynis.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import ninis.com.pynis.R;

/**
 * Created by gypark on 2015. 12. 16..
 */
public class ClienPostViewHolder extends RecyclerView.ViewHolder {

    public TextView tvTitle;
    public TextView tvUser;
    public SimpleDraweeView sdvUser;
    public TextView tvReplyCount;

    public ClienPostViewHolder(View itemView) {
        super(itemView);

        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvUser = (TextView) itemView.findViewById(R.id.tv_user);
        sdvUser = (SimpleDraweeView) itemView.findViewById(R.id.sdv_user_img);
        tvReplyCount = (TextView) itemView.findViewById(R.id.tv_reply_count);
    }
}
