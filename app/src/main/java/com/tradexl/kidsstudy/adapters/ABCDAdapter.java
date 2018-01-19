package com.tradexl.kidsstudy.adapters;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.tradexl.kidsstudy.R;

import java.util.List;

/**
 * Created by Raghav on 15-Jan-18.
 */

public class ABCDAdapter extends RecyclerView.Adapter<ABCDAdapter.MyViewHolder>  {
    private Activity activity;
    private List<String> list;
    private ClickListenerInterface listener;
    private RecyclerView recyclerView;
    private int lastPosition = -1;

    public ABCDAdapter(Activity activity, List<String> list, ClickListenerInterface listener, RecyclerView recyclerView) {
        this.activity = activity;
        this.list = list;
        this.listener = listener;
        this.recyclerView = recyclerView;

    }




    @Override
    public ABCDAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.abcd_row, parent, false);
        ABCDAdapter.MyViewHolder myViewHolder = new ABCDAdapter.MyViewHolder(view);

        return myViewHolder;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ABCDAdapter.MyViewHolder holder, final int position) {
       /* final Context context = recyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();*/

        holder.counting.setText(list.get(position).toString());
        setAnimation(holder.itemView, position);
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.clickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView counting;
        CardView card_view;


        public MyViewHolder(View itemView) {
            super(itemView);
            counting = (TextView) itemView.findViewById(R.id.counting);
            card_view = (CardView) itemView.findViewById(R.id.card_view);

        }
    }

    public interface ClickListenerInterface {
        void clickListener(int position);
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.item_animation_right);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}