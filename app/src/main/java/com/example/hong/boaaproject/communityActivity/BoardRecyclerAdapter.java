package com.example.hong.boaaproject.communityActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hong.boaaproject.R;

import java.util.List;

public class BoardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    List<BoardModel> boardModels;

    public BoardRecyclerAdapter(Context context, List<BoardModel> boardModels) {
        this.context = context;
        this.boardModels = boardModels;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);


        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((CustomViewHolder) holder).tvName.setText(boardModels.get(position).userID);
        ((CustomViewHolder) holder).tvContent.setText(boardModels.get(position).boardContent);
        ((CustomViewHolder) holder).tvDate.setText(boardModels.get(position).boardDate);
       // ((CustomViewHolder) holder).ivImage.setText(boardModels.get(position).boardImgURL);
        ((CustomViewHolder) holder).etComment.setText(boardModels.get(position).boardComment);

    }

    @Override
    public int getItemCount() {
        return boardModels.size();
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        //ImageView iv, ivFavorite, ivImage;
        TextView tvName, tvContent, tvFavorite, tvNicName, tvDate, ivImage;
        EditText etComment;


        public CustomViewHolder(View view) {
            super(view);

           // iv = view.findViewById(R.id.iv);
           // ivFavorite = view.findViewById(R.id.ivFavorite);
//            ivImage = view.findViewById(R.id.ivImage);
            tvName = view.findViewById(R.id.tvName);
            tvContent = view.findViewById(R.id.tvContent);
            tvFavorite = view.findViewById(R.id.tvFavorite);
            tvNicName = view.findViewById(R.id.tvNicName);
            etComment = view.findViewById(R.id.etComment);
            tvDate = view.findViewById(R.id.tvDate);

        }
    }
}
