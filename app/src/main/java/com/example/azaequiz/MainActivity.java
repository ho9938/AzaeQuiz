package com.example.azaequiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    private BoardView board;
    private ImageButton submitBtn;
    private ImageView testView;
    private TextScanner textScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        board = findViewById(R.id.q_answer);

        submitBtn = findViewById(R.id.q_submit);
        submitBtn.setOnClickListener(submitAnswer);

        testView = findViewById(R.id.q_test);
        
        textScanner = new TextScanner(recogSuccess, recogFailure);
    }

    public void notifyText(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
    }
  
    View.OnClickListener submitAnswer = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("MainActivity", "submitAnswer()");
            Bitmap result = board.getBitmap();
            testView.setImageBitmap(result);
    }

    OnSuccessListener<Text> recogSuccess = new OnSuccessListener<Text>() {
        @Override
        public void onSuccess(Text text) {
            Log.d("recogBitmap", "success");
            String result = text.getText();
            notifyText(result);
        }
    };

    OnFailureListener recogFailure = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("recogBitmap", "fail");
        }
    };
}