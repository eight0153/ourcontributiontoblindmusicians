package cosc345.AuralLearner.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;

/** Represents a musical scale. */
public class Scale extends Playable {
    private static final String LOG_TAG = "Scale";
    /** the notes in the scale. */
    public final ArrayList<Note> notes;
    public final ScaleType scaleType;

    /**
     * Create a scale starting of the given type (e.g. major/minor), starting at the given note.
     *
     * @param root the note to start the scale at.
     * @param scaleType the type of scale to create.
     */
    public Scale(Note root, ScaleType scaleType) {
        this(root, scaleType, Note.NoteLength.CROTCHET);
    }

    public Scale(Note root, ScaleType scaleType, Note.NoteLength length) {
        this.scaleType = scaleType;
        root.setNoteLength(Note.NoteLength.MINIM);
        int[] scalePattern = scaleType.getSemitonePattern();

        int scaleSize = scalePattern.length + 1;
        notes = new ArrayList<>(scaleSize);
        notes.add(root);

        for (int i = 0; i < scalePattern.length; i++) {
            Note currNote = notes.get(i);
            String nextNoteName = Note.NOTE_NAMES[currNote.getNameIndex() + scalePattern[i]];
            Note nextNote = new Note(nextNoteName, length);
            notes.add(nextNote);
        }
    }

    @Override
    public void play() {
        super.play();

        Log.i(LOG_TAG, this.toString());

        playNextNote(0);
    }

    /**
     * Play each note in the scale recursively.
     *
     * @param index the index of the next note to play.
     */
    private void playNextNote(int index) {
        if (index == notes.size()) {
            if (delegate != null) {
                delegate.onPlaybackFinished();
            }

            onDone();
        } else {
            Note next = notes.get(index);
            next.setDelegate(new Delegate() {
                @Override
                public void onPlaybackStarted() {
                }

                @Override
                public void onPlaybackFinished() {
                    playNextNote(index + 1);
                }

                @Override
                public void onDone() {

                }
            });

            next.play();
        }
    }

    @Override
    public void stop() {
        for (Note note : notes) {
            note.stop();
        }

        super.stop();
    }

    @Override
    public String prettyPrint() {
        return String.format(Locale.ENGLISH ,"%s scale starting at %s.", scaleType, notes.get(0));
    }

    @Override
    public String toString() {
        return scaleType.toString() + " " + notes.toString();
    }

    /** Captures the different types of scales. */
    public enum ScaleType {
        MAJOR, NATURAL_MINOR;

        /**
         * Get a list of numbers where each number represents how many semitones higher the
         * next note in the given scale should be relative to the previous note.
         *
         * @return a list of numbers representing the semitone pattern of the given scale.
         */
        public int[] getSemitonePattern() {
            switch (this) {
                case MAJOR:
                    return new int[] {2, 2, 1, 2, 2, 2, 1};
                case NATURAL_MINOR:
                    return new int[] {2, 1, 2, 2, 1, 2, 2};
                default:
                    return new int[0];
            }
        }

        @Override
        public String toString() {
            switch (this) {
                case MAJOR:
                    return "Major";
                case NATURAL_MINOR:
                    return "Natural Minor";
                default:
                    return "Unknown";
            }
        }
    }
}
