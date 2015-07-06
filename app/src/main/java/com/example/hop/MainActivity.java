package com.example.hop;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hop.model.GameManager;
import com.example.hop.model.Stage;


public class MainActivity extends ActionBarActivity {

    public static final int QUESTION = 0;
    public static final int CHECKPOINT = 1;

    private GameManager gameManager;
    private StagesViewAdapter mStagesAdapter;
    private LinearLayoutManager mStagesLayout;
    private RecyclerView mStagesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameManager = gameManager.getInstance();

        mStagesAdapter = new StagesViewAdapter();
        mStagesLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mStagesView = (RecyclerView) findViewById(R.id.game_stages_view);
        mStagesView.setLayoutManager(mStagesLayout);
        mStagesView.setAdapter(mStagesAdapter);
    }

    public void doNextButton(View view) {
        gameManager.doPassCurrentStage();
    }

    private class StagesViewAdapter extends RecyclerView.Adapter<StagesViewAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (QUESTION == viewType) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_stages_question, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_stages_checkpoint, parent, false);
            }

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Stage stage = gameManager.stages[position];
            TextView label;
            if (stage.checkpoint) {
                label = (TextView) holder.itemView.findViewById(R.id.game_stages_checkpoint_label);
                TextView titleView = (TextView) holder.itemView.findViewById(R.id.game_stages_checkpoint_title);
                if (gameManager.getCurrentStage().stageIndex > position) {
                    label.setBackgroundResource(R.mipmap.checkpoint_small_unlocked);
                    label.setTextColor(label.getContext().getResources().getColor(android.R.color.white));
                    label.setText(String.valueOf(stage.displayIndex + 1));
                    titleView.setTextColor(label.getContext().getResources().getColor(R.color.action_bar_bg));
                } else {
                    titleView.setTextColor(label.getContext().getResources().getColor(R.color.stages_dark_grey));
                    label.setBackgroundResource(R.mipmap.checkpoint_small_locked);
                }
            } else {
                label = (TextView) holder.itemView.findViewById(R.id.game_stages_question_label);
                if (gameManager.getCurrentStage().stageIndex > position) {
                    label.setBackgroundResource(R.drawable.game_stage_question_unlocked);
                    label.setTextColor(label.getContext().getResources().getColor(android.R.color.white));
                    label.setText(String.valueOf(stage.displayIndex + 1));
                } else {
                    label.setBackgroundResource(R.drawable.game_stage_question_locked);
                    label.setTextColor(label.getContext().getResources().getColor(R.color.stages_dark_grey));
                    label.setText(String.valueOf(stage.displayIndex + 1));
                }
            }
        }

        @Override
        public int getItemCount() {
            return gameManager.stages.length;
        }

        @Override
        public int getItemViewType(int position) {
            return gameManager.stages[position].checkpoint ? CHECKPOINT : QUESTION;
        }
    }
}