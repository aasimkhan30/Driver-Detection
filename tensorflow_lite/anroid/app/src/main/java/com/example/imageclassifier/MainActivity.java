package com.example.imageclassifier;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.common.modeldownload.FirebaseRemoteModel;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vCamera)
    CameraKitView vCamera;
    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.ivFinalPreview)
    ImageView ivFinalPreview;
    @BindView(R.id.tvClassification)
    TextView tvClassification;

    private MnistClassifier mnistClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadMnistClassifier();
    }

    private void loadMnistClassifier() {
        try {
            mnistClassifier = Classifier.classifier(getAssets(), ModelConfig.MODEL_FILENAME);
        } catch (IOException e) {
            Toast.makeText(this, "MNIST model couldn't be loaded. Check logs for details.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        vCamera.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vCamera.onResume();
    }

    @Override
    protected void onPause() {
        vCamera.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        vCamera.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        vCamera.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @OnClick(R.id.btnTakePhoto)
    public void onTakePhoto() {
        vCamera.captureImage((cameraKitView, picture) -> {
            onImageCaptured(picture);
        });
    }

    private void onImageCaptured(byte[] picture) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
        Bitmap squareBitmap = ThumbnailUtils.extractThumbnail(bitmap, getScreenWidth(), getScreenWidth());
        ivPreview.setImageBitmap(squareBitmap);

        Bitmap preprocessedImage = ImageUtils.prepareImageForClassification(squareBitmap);
        ivFinalPreview.setImageBitmap(preprocessedImage);

        List<Classification> recognitions = mnistClassifier.recognizeImage(preprocessedImage);
        tvClassification.setText(recognitions.toString());
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
}
