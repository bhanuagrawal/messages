package com.example.messages.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.messages.R;
import com.example.messages.data.entities.User;
import com.example.messages.data.entities.UserMessage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemAdater<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int MESSAGE = 0;
    public static final int USERS = 1;

    private final int itemVIewType;
    private ArrayList<T> mData;
    private ItemAdaterListner itemAdaterListner;
    private Context context;

    public ItemAdater(Context context, ItemAdaterListner itemAdaterListner, int viewType) {
        this.mData = new ArrayList<>();
        this.itemAdaterListner = itemAdaterListner;
        this.context = context;
        this.itemVIewType = viewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (itemVIewType){
            case MESSAGE:
                MessageViewHolder messageViewHolder = new MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.message, parent, false));
                return messageViewHolder;
            case USERS:
                UserViewHolder userViewHolder = new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user, parent, false));
                return userViewHolder;
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()){
            case MESSAGE:
                ((MessageViewHolder)holder).bindTo((UserMessage) mData.get(position));
                break;
            case USERS:
                ((UserViewHolder)holder).bindTo((User) mData.get(position));
                break;
        }



    }


    @Override
    public int getItemViewType(int position) {
        return itemVIewType;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void onDataChange(ArrayList<T> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }



    class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv1)
        TextView tv1;

        @BindView(R.id.tv2)
        TextView tv2;


        @BindView(R.id.imageView2)
        ImageView imageView;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemAdaterListner.onSelect((User) mData.get(getAdapterPosition()));
                }
            });
        }


        public void bindTo(User user) {
            tv1.setText(user.getName());
            tv2.setText(user.getMobile());
        }


    }

    class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv1)
        TextView tv1;

        @BindView(R.id.tv2)
        TextView tv2;

        @BindView(R.id.tv3)
        TextView tv3;


        public MessageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(UserMessage userMessage) {
            tv1.setText(userMessage.getUser().getName());
            tv2.setText(userMessage.getMessage().getText());
            tv3.setText(userMessage.getMessage().getDate().toString());
        }
    }


    public interface ItemAdaterListner{
        void onSelect(User user);
    }
}
