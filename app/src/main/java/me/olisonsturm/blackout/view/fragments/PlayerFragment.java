package me.olisonsturm.blackout.view.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;
import me.olisonsturm.blackout.R;
import me.olisonsturm.blackout.model.Player;
import me.olisonsturm.blackout.model.PlayerViewModel;
import me.olisonsturm.blackout.view.activitys.TestActivity;
import me.olisonsturm.blackout.view.adapter.PlayerListAdapter;


public class PlayerFragment extends Fragment {

    Button continueBtn;
    FloatingActionButton addBtn;
    private PlayerViewModel playerViewModel;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance) {
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        addBtn = view.findViewById(R.id.addNewPlayer);
        continueBtn = view.findViewById(R.id.startGameButton);


        RecyclerView recyclerView = view.findViewById(R.id.player_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);


        final PlayerListAdapter adapter = new PlayerListAdapter();
        recyclerView.setAdapter(adapter);


        playerViewModel = new ViewModelProvider((ViewModelStoreOwner) view.getContext()).get(PlayerViewModel.class);
        playerViewModel.getAllPlayers().observe(getViewLifecycleOwner(), new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                adapter.setPlayers(players);
            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Player player = adapter.getPlayerAt(position);

                if (direction == ItemTouchHelper.LEFT) {
                    playerViewModel.delete(player);
                    Snackbar.make(recyclerView, player.getNickName() + " wirklich löschen?", Snackbar.LENGTH_LONG)
                            .setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    playerViewModel.insert(player);
                                }
                            }).show();
                }

                if (direction == ItemTouchHelper.RIGHT){
                    Bundle bundle = new Bundle();
                    bundle.putString("Nick_Name", player.getNickName());
                    bundle.putString("Real_Name", player.getRealName());
                    bundle.putInt("gender", player.getGender());
                    bundle.putInt("priority", player.getPriority());

                    BottomSheetPlayerEdit bottomSheetPlayerEdit = new BottomSheetPlayerEdit();
                    bottomSheetPlayerEdit.setArguments(bundle);
                    bottomSheetPlayerEdit.show(getParentFragmentManager(), "BottomSheetPlayerEdit");
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_delete)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.green))
                        .addSwipeRightActionIcon(R.drawable.ic_edit)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(recyclerView);


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new BluetoothChatFragment()).commit();
                startActivity(new Intent(getContext(), TestActivity.class));
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetPlayer bottomSheetPlayer = new BottomSheetPlayer();
                bottomSheetPlayer.show(getParentFragmentManager(), "BottomSheetPlayer");
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deletePlayers:
                playerViewModel.deleteAllPlayers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
