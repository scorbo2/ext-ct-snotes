package ca.corbett.crypttext.extensions.snotes.actions;

import ca.corbett.crypttext.extensions.snotes.SnotesExtension;
import ca.corbett.extras.EnhancedAction;

import java.awt.event.ActionEvent;

/**
 * An action to toggle the visibility of the Snotes panel when invoked.
 *
 * @author <a href="https://github.com/scorbo2">scorbo2</a>
 */
public class SnotesAction extends EnhancedAction {

    private final SnotesExtension snotesExtension;

    public SnotesAction(SnotesExtension owner) {
        super("Show Snotes panel");
        this.snotesExtension = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snotesExtension.togglePanelVisibility();
    }
}
