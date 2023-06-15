package com.example.azaequiz;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class TextScanner {
    final private TextRecognizer recognizer;
    final private OnSuccessListener<Text> onSuccess;
    final private OnFailureListener onFailure;

    public TextScanner(OnSuccessListener<Text> onSuccess, OnFailureListener onFailure) {
        recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    public void recogBitmap(Bitmap bitmap) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        recognizer.process(image)
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }
}
