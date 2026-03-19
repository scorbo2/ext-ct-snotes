package ca.corbett.crypttext.extensions.snotes.model.filter;

import ca.corbett.crypttext.extensions.snotes.model.Note;

import java.util.Locale;

/**
 * This Filter can be used to filter Notes by their text content.
 * If a Note does not contain the given text (with optional case-sensitivity), then it will be filtered out.
 *
 * @author <a href="https://github.com/scorbo2">scorbo2</a>
 */
public class TextFilter extends Filter {

    private final String contains;
    private final boolean isCaseSensitive;

    public TextFilter(String contains) {
        this(contains, false);
    }

    /**
     * Create a text filter with the given text to search for, with optional case-sensitivity.
     * The given text can be null or empty, but the resulting filter will effectively be a no-op.
     * <p>
     * The given text will NOT be trimmed - it's valid to look for something like "hello   " with
     * three trailing spaces. A value of null will be treated the same as an empty string (no-op).
     * </p>
     */
    public TextFilter(String contains, boolean isCaseSensitive) {
        this.contains = contains == null ? "" : contains;
        this.isCaseSensitive = isCaseSensitive;
    }

    @Override
    public String getDescription() {
        return "Filter by text content";
    }

    @Override
    public boolean isFiltered(Note note) {
        if (note == null || note.getText() == null || note.getText().isBlank()) {
            // Don't bother filtering if there's nothing to filter.
            return false;
        }
        String candidateText = note.getText();
        String toFind = contains;
        if (!isCaseSensitive) {
            candidateText = candidateText.toLowerCase(Locale.ROOT);
            toFind = toFind.toLowerCase();
        }
        return !candidateText.contains(toFind);
    }
}
