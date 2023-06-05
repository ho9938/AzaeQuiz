package com.example.azaequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class QuizListActivity extends AppCompatActivity {
    private QuizBank quizBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizlist);

        this.quizBank = new QuizBank();

        RecyclerView selection = findViewById(R.id.qlist_selection);
        selection.setLayoutManager(new GridLayoutManager(this, 8));
        selection.setAdapter(new QuizListAdapter(quizBank));
    }
}

class QuizListAdapter extends RecyclerView.Adapter<QuizHolder> {
    final private QuizBank quizBank;

    public QuizListAdapter(QuizBank quizBank) {
        this.quizBank = quizBank;
    }

    @NonNull
    @Override
    public QuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_quizlist, parent, false);

        return new QuizHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizHolder holder, int position) {
        holder.setText(quizBank.getName(position));
        holder.itemView.setOnClickListener(v -> {
            int index = holder.getAdapterPosition();

            Intent intent = new Intent(v.getContext(), ManagerActivity.class);
            intent.putExtra("isChallenge", false);
            intent.putExtra("index", index);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return quizBank.getSize();
    }
}

class QuizHolder extends RecyclerView.ViewHolder {
    private final TextView nameView;

    public QuizHolder(View itemView) {
        super(itemView);

        nameView = itemView.findViewById(R.id.qlist_item);
    }

    public void setText(String text) {
        nameView.setText(text);
    }
}



