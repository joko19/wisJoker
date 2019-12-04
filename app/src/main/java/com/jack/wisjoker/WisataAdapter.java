package com.jack.wisjoker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.security.AccessControlContext;
import java.util.List;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.MyViewHolder> {

    private Context mContext;
    private List<DataWisata> mData;

    public WisataAdapter(Context mContext, List<DataWisata> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.list_wisata,viewGroup,false);
        final MyViewHolder iniholder = new MyViewHolder(v);

        return iniholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_nama.setText(mData.get(i).getNamaTempat());
//        myViewHolder.tv_lokasi.setText(mData.get(i).getLokasi());
        myViewHolder.tv_deskripsi.setText(mData.get(i).getDeskripsi());
        final DataWisata iniwisata= mData.get(i);
        Glide.with(myViewHolder.baris_item.getContext())
                .load(iniwisata.getImgUrl())
                .into(myViewHolder.img_cover);

        myViewHolder.baris_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent inidetail = new Intent(context, DetailWisata.class);
                inidetail.putExtra(DetailWisata.EXTRA_WISATA, mData.get(i));
                inidetail.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(inidetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout baris_item;
        private TextView tv_nama, tv_lokasi, tv_deskripsi;
        private ImageView img_cover;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            baris_item = itemView.findViewById(R.id.baris);
            tv_nama= itemView.findViewById(R.id.tv_judul);
//            tv_lokasi =itemView.findViewById(R.id.tv);
            tv_deskripsi = itemView.findViewById(R.id.tv_deskripsi);
            img_cover =  itemView.findViewById(R.id.img_poster);
        }
    }

}

