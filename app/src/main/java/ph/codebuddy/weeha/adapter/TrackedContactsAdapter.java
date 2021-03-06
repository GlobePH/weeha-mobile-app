package ph.codebuddy.weeha.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ph.codebuddy.weeha.R;
import ph.codebuddy.weeha.model.TrackRequest;

/**
 * Created by rommeldavid on 24/07/16.
 */
public class TrackedContactsAdapter extends RecyclerView.Adapter<TrackedContactsAdapter.ViewAllJoinersHolder> {
    ArrayList<TrackRequest> trackRequests;
    Context context;

    public TrackedContactsAdapter(ArrayList<TrackRequest> list, Context context){
        this.trackRequests = list;
        this.context = context;
    }
    @Override
    public ViewAllJoinersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tracked_contacts, parent, false);

        return new ViewAllJoinersHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewAllJoinersHolder holder, int position) {
        TrackRequest current = trackRequests.get(position);

        holder.tvJoinerName.setText(current.getFullName());
        Picasso.with(context).load(current.getAvatar()).placeholder(R.drawable.placeholder_user).into(holder.rivAvatar);

        holder.ivTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return trackRequests.size();
    }

    public class ViewAllJoinersHolder extends RecyclerView.ViewHolder {
        RoundedImageView rivAvatar;
        AppCompatTextView tvJoinerName;
        AppCompatImageView ivTrack;

        public ViewAllJoinersHolder(View itemView) {
            super(itemView);
            rivAvatar = (RoundedImageView) itemView.findViewById(R.id.rivAvatar);
            tvJoinerName = (AppCompatTextView) itemView.findViewById(R.id.tvJoinerName);
            ivTrack = (AppCompatImageView) itemView.findViewById(R.id.track);

        }
    }
}
