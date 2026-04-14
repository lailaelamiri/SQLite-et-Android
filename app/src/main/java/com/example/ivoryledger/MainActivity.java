package com.example.ivoryledger;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ivoryledger.classes.EnrolledScholar;
import com.example.ivoryledger.service.ScholarVaultService;

public class MainActivity extends AppCompatActivity {

    private EditText inputFamilyName;
    private EditText inputGivenName;
    private Button btnEnroll;

    private EditText inputScholarId;
    private Button btnLocate;
    private Button btnErase;
    private TextView displayScholarResult;

    private ScholarVaultService vaultService;

    void clearEnrollmentFields() {
        inputFamilyName.setText("");
        inputGivenName.setText("");
    }

    void animateButtonPress(View targetView) {
        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(targetView, "scaleX", 1f, 0.93f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(targetView, "scaleY", 1f, 0.93f);
        ObjectAnimator scaleUpX = ObjectAnimator.ofFloat(targetView, "scaleX", 0.93f, 1f);
        ObjectAnimator scaleUpY = ObjectAnimator.ofFloat(targetView, "scaleY", 0.93f, 1f);

        scaleDownX.setDuration(80);
        scaleDownY.setDuration(80);
        scaleUpX.setDuration(180);
        scaleUpY.setDuration(180);
        scaleUpX.setInterpolator(new OvershootInterpolator());
        scaleUpY.setInterpolator(new OvershootInterpolator());

        AnimatorSet pressSet = new AnimatorSet();
        pressSet.play(scaleDownX).with(scaleDownY);
        pressSet.play(scaleUpX).with(scaleUpY).after(scaleDownX);
        pressSet.start();
    }

    void animateResultReveal(View targetView) {
        targetView.setAlpha(0f);
        targetView.setTranslationY(20f);
        targetView.animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(400)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    void runEntryAnimations() {
        View[] panels = {inputFamilyName, inputGivenName, btnEnroll, inputScholarId, btnLocate, btnErase};
        for (int i = 0; i < panels.length; i++) {
            panels[i].setAlpha(0f);
            panels[i].setTranslationY(40f);
            panels[i].animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setStartDelay(100L * i)
                    .setDuration(450)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vaultService = new ScholarVaultService(this);

        inputFamilyName = findViewById(R.id.inputFamilyName);
        inputGivenName = findViewById(R.id.inputGivenName);
        btnEnroll = findViewById(R.id.btnEnroll);

        inputScholarId = findViewById(R.id.inputScholarId);
        btnLocate = findViewById(R.id.btnLocate);
        btnErase = findViewById(R.id.btnErase);
        displayScholarResult = findViewById(R.id.displayScholarResult);

        runEntryAnimations();

        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonPress(v);

                String familyInput = inputFamilyName.getText().toString().trim();
                String givenInput = inputGivenName.getText().toString().trim();

                if (familyInput.isEmpty() || givenInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in both fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                vaultService.enrollScholar(new EnrolledScholar(familyInput, givenInput));
                clearEnrollmentFields();

                for (EnrolledScholar scholar : vaultService.fetchAllScholars()) {
                    Log.d(scholar.getScholarId() + "", scholar.getFamilyName() + " " + scholar.getGivenName());
                }

                Toast.makeText(MainActivity.this, "Scholar enrolled successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonPress(v);

                String idInput = inputScholarId.getText().toString().trim();
                if (idInput.isEmpty()) {
                    displayScholarResult.setText("");
                    Toast.makeText(MainActivity.this, "Please enter a scholar ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                EnrolledScholar foundScholar = vaultService.fetchScholarById(Integer.parseInt(idInput));
                if (foundScholar == null) {
                    displayScholarResult.setText("");
                    Toast.makeText(MainActivity.this, "No scholar found with this ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                displayScholarResult.setText(foundScholar.getFamilyName() + "  " + foundScholar.getGivenName());
                animateResultReveal(displayScholarResult);
            }
        });

        btnErase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateButtonPress(v);

                String idInput = inputScholarId.getText().toString().trim();
                if (idInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a scholar ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                EnrolledScholar targetScholar = vaultService.fetchScholarById(Integer.parseInt(idInput));
                if (targetScholar == null) {
                    Toast.makeText(MainActivity.this, "No scholar found to remove", Toast.LENGTH_SHORT).show();
                    return;
                }

                vaultService.removeScholar(targetScholar);
                displayScholarResult.setText("");
                inputScholarId.setText("");
                Toast.makeText(MainActivity.this, "Scholar removed from ledger", Toast.LENGTH_SHORT).show();
            }
        });
    }
}