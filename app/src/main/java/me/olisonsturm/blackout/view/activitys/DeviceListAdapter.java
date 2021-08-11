package me.olisonsturm.blackout.view.activitys;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import me.olisonsturm.blackout.R;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.listViewHolder> {

    private static final String TAG = "DeviceListAdapter";
    private final ArrayList<DeviceInfo> myList;
    private final OnNoteListener onNoteListener;


    public DeviceListAdapter(ArrayList<DeviceInfo> list, OnNoteListener onNoteListener) {
        this.myList = list;
        this.onNoteListener = onNoteListener;
    }


    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetoothlist_item, parent, false);
        return new listViewHolder(view, onNoteListener);
    }


    @Override
    public void onBindViewHolder(@NonNull listViewHolder holder, int position) {

        try {
            holder.name.setText(myList.get(position).getName());
            holder.macAddress.setText(myList.get(position).getAddress());
        } catch (NullPointerException e) {
            Log.e(TAG, "onBindViewholder: Null Pointer" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    public static class listViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name;
        public TextView macAddress;
        OnNoteListener onNoteListener;

        public listViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            this.name = itemView.findViewById(R.id.layout_name);
            this.macAddress = itemView.findViewById(R.id.layout_address);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onCLick " + getBindingAdapterPosition());
            onNoteListener.onNoteClick(getBindingAdapterPosition());
        }
    }


}
