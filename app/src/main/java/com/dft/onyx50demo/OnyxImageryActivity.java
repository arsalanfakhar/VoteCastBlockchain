package com.dft.onyx50demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dft.onyx.NfiqMetrics;
import com.dft.onyxcamera.config.OnyxResult;
import com.dft.onyxcamera.util.UploadMatchResult;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class OnyxImageryActivity extends Activity {
    private static final String TAG = OnyxImageryActivity.class.getName();

    private Activity activity;

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_imagery);
        activity = this;
        ImageView rawImage1 = findViewById(R.id.rawImage1);
        ImageView processedImage1 = findViewById(R.id.processedImage1);

        ImageView rawImage2 = findViewById(R.id.rawImage2);
        rawImage2.setVisibility(View.INVISIBLE);

        ImageView processedImage2 = findViewById(R.id.processedImage2);
        processedImage2.setVisibility(View.INVISIBLE);

        ImageView rawImage3 = findViewById(R.id.rawImage3);
        rawImage3.setVisibility(View.INVISIBLE);

        ImageView processedImage3 = findViewById(R.id.processedImage3);
        processedImage3.setVisibility(View.INVISIBLE);

        ImageView rawImage4 = findViewById(R.id.rawImage4);
        rawImage4.setVisibility(View.INVISIBLE);

        ImageView processedImage4 = findViewById(R.id.processedImage4);
        processedImage4.setVisibility(View.INVISIBLE);

        TextView livenessTextView = findViewById(R.id.livenessText);

        TextView nfiqTextView1 = findViewById(R.id.nfiqText1);
        nfiqTextView1.setVisibility(View.INVISIBLE);
        TextView nfiqTextView2 = findViewById(R.id.nfiqText2);
        nfiqTextView2.setVisibility(View.INVISIBLE);
        TextView nfiqTextView3 = findViewById(R.id.nfiqText3);
        nfiqTextView1.setVisibility(View.INVISIBLE);
        TextView nfiqTextView4 = findViewById(R.id.nfiqText4);
        nfiqTextView4.setVisibility(View.INVISIBLE);
        rawImage1.setImageDrawable(null);
        processedImage1.setImageDrawable(null);
        rawImage2.setImageDrawable(null);
        processedImage2.setImageDrawable(null);
        rawImage3.setImageDrawable(null);
        processedImage3.setImageDrawable(null);
        rawImage4.setImageDrawable(null);
        processedImage4.setImageDrawable(null);

        OnyxResult onyxResult = MainApplication.getOnyxResult();
        if (onyxResult == null) {
            return;
        }

        ArrayList<Bitmap> rawImages = onyxResult.getRawFingerprintImages();
        ArrayList<Bitmap> processedImages = onyxResult.getProcessedFingerprintImages();
        livenessTextView.setText(String.format("Liveness: %.2f",onyxResult.getMetrics().getLivenessConfidence()));
        if (onyxResult.getMetrics() != null && onyxResult.getMetrics().getNfiqMetrics() != null) {
//            List<NfiqMetrics> nfiqMetricsList = onyxResult.getMetrics().getNfiqMetrics();
//            for (int i = 0; i < nfiqMetricsList.size(); i++) {
//                switch (i) {
//                    case 0:
//                        if (nfiqMetricsList.get(i) != null) {
//                            nfiqTextView1.setText("NFIQ: " + String.valueOf(nfiqMetricsList.get(i).getNfiqScore()));
//                        }
//                    case 1:
//                        if (nfiqMetricsList.get(i) != null) {
//                            nfiqTextView2.setText("NFIQ: " + String.valueOf(nfiqMetricsList.get(i).getNfiqScore()));
//                        }
//                    case 2:
//                        if (nfiqMetricsList.get(i) != null) {
//                            nfiqTextView3.setText("NFIQ: " + String.valueOf(nfiqMetricsList.get(i).getNfiqScore()));
//                        }
//                    case 3:
//                        if (nfiqMetricsList.get(i) != null) {
//                            nfiqTextView4.setText("NFIQ: " + String.valueOf(nfiqMetricsList.get(i).getNfiqScore()));
//                        }
//                }
//            }
        }
        if (rawImages != null) {
            for (int i = 0; i < rawImages.size(); i++) {
                switch (i) {
                    case 0:
                        if (rawImages.get(i) != null) {
                            rawImage1.setImageBitmap(rawImages.get(i));
                        }
                        if (processedImages.get(i) != null) {
                            processedImage1.setImageBitmap(processedImages.get(i));
                        }
                        break;
//                    case 1:
//                        if (rawImages.get(i) != null) {
//                            rawImage2.setImageBitmap(rawImages.get(i));
//                        }
//                        if (processedImages.get(i) != null) {
//                            processedImage2.setImageBitmap(processedImages.get(i));
//                        }
//                        break;
//                    case 2:
//                        if (rawImages.get(i) != null) {
//                            rawImage3.setImageBitmap(rawImages.get(i));
//                        }
//                        if (processedImages.get(i) != null) {
//                            processedImage3.setImageBitmap(processedImages.get(i));
//                        }
//                        break;
//                    case 3:
//                        if (rawImages.get(i) != null) {
//                            rawImage4.setImageBitmap(rawImages.get(i));
//                        }
//                        if (processedImages.get(i) != null) {
//                            processedImage4.setImageBitmap(processedImages.get(i));
//                        }
//                        break;
                }
            }
        }

        if (onyxResult.getMetrics() != null && onyxResult.getMetrics().getTransactionId() != null) {
            String transactionId = onyxResult.getMetrics().getTransactionId();
            UploadMatchResult uploadMatchResult = new UploadMatchResult(this,
                    transactionId,
                    false,
                    "INES");
            uploadMatchResult.uploadMatchResult();
        }

        FileUtil fileUtil = new FileUtil();
        fileUtil.checkExternalMedia(this);
        if (onyxResult.getWsqData() != null && !onyxResult.getWsqData().isEmpty() && fileUtil.getWriteExternalStoragePermission(this)) {
            for (int i = 0; i < onyxResult.getWsqData().size(); i++) {
                fileUtil.writeToSDFile(this, onyxResult.getWsqData().get(i), "wsq" + i);
                break;
            }
        }

        fileUtil.checkExternalMedia(this);
        if (onyxResult.getProcessedFingerprintImages() != null && !onyxResult.getProcessedFingerprintImages().isEmpty() && fileUtil.getWriteExternalStoragePermission(this)) {
            for (int i = 0; i < onyxResult.getProcessedFingerprintImages().size(); i++) {
                fileUtil.writePNGToSDFile(this, onyxResult.getProcessedFingerprintImages().get(i), "png" + i);
                break;
            }
        }

        Button finishButton = findViewById(R.id.finishButton);
        finishButton.setText("Verify");

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainApplication.setOnyxResult(null);
                startActivity(new Intent(activity, OnyxSetupActivity.class));
            }
        });
    }

}
