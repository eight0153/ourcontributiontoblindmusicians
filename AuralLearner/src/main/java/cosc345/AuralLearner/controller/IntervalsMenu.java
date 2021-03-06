package cosc345.AuralLearner.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Button;

import java.util.Objects;

import cosc345.AuralLearner.R;
import cosc345.AuralLearner.model.Difficulty;

public class IntervalsMenu extends VoiceControlActivity {

    //needs voice control stuff
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervals_menu);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button easyBtn = findViewById(R.id.intervalsMenu_easyBtn);
        easyBtn.setOnClickListener(view -> IntervalsMenu.this.openExercise(Difficulty.EASY));
        Button mediumBtn = findViewById(R.id.intervalsMenu_mediumBtn);
        mediumBtn.setOnClickListener(view -> IntervalsMenu.this.openExercise(Difficulty.MEDIUM));
        Button hardBtn = findViewById(R.id.intervalsMenu_hardBtn);
        hardBtn.setOnClickListener(view -> IntervalsMenu.this.openExercise(Difficulty.HARD));
        findViewById(R.id.intervalsMenu_helpBtn).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.intervalsMenu_difficultyHelpTitle)
                    .setMessage(R.string.intervalsMenu_difficultyHelpText)
                    .setPositiveButton(R.string.dialogOk, null);
            builder.create()
                    .show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openExercise(Difficulty difficulty) {
        Intent intent = new Intent(IntervalsMenu.this, IntervalsExercise.class);
        intent.putExtra("EXTRA_DIFFICULTY", difficulty.toString());
        startActivity(intent);
    }
}
