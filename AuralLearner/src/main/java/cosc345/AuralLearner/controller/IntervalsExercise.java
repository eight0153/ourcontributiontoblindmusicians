package cosc345.AuralLearner.controller;

import android.os.Bundle;

import cosc345.AuralLearner.R;
import cosc345.AuralLearner.model.Difficulty;
import cosc345.AuralLearner.model.IntervalExerciseGrader;

public class IntervalsExercise extends ExerciseActivity {
    private Difficulty difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervals_exercise);

        startBtn = findViewById(R.id.intervalsExercise_startBtn);
        stopBtn = findViewById(R.id.intervalsExercise_stopBtn);

        startBtn.setOnClickListener(v -> startExercise());
        stopBtn.setOnClickListener(v -> stopExercise());

        String difficulty = getIntent().getStringExtra("EXTRA_DIFFICULTY");

        if (difficulty.equals(Difficulty.EASY.toString())) {
            this.difficulty = Difficulty.EASY;
        } else if (difficulty.equals(Difficulty.MEDIUM.toString())) {
            this.difficulty = Difficulty.MEDIUM;
        } else {
            this.difficulty = Difficulty.HARD;
        }
    }

    @Override
    void setupExercise() {
        grader = new IntervalExerciseGrader(difficulty);
    }
}
