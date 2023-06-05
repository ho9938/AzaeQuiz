package com.example.azaequiz;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CheckAnswerDialog extends Dialog {
    final private TextView tv1, tv2, tv3;

    public CheckAnswerDialog(@NonNull Context context,
                             View.OnClickListener l1,
                             View.OnClickListener l2,
                             View.OnClickListener l3) {
        super(context);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.dialog_checkanswer);

        tv1 = findViewById(R.id.check_tv1);
        tv2 = findViewById(R.id.check_tv2);
        tv3 = findViewById(R.id.check_tv3);

        Button btn1 = findViewById(R.id.check_btn1);
        Button btn2 = findViewById(R.id.check_btn2);
        Button btn3 = findViewById(R.id.check_btn3);

        btn1.setOnClickListener(l1);
        btn2.setOnClickListener(l2);
        btn3.setOnClickListener(l3);
    }

    public void setContent(String correct_answer, String given_answer) {
        if (isCorrect(correct_answer, given_answer)) {
            tv1.setText("CORRECT!");
        } else {
            tv1.setText("INCORRECT!");
        }

        tv2.setText("Correct Answer: " + correct_answer);
        tv3.setText("Your Answer: " + given_answer);
    }

    public Boolean isCorrect(String correct_answer, String given_answer) {
        return correct_answer.equalsIgnoreCase(given_answer);
    }
}
