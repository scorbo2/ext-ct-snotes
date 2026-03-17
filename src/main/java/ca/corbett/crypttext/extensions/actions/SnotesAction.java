package ca.corbett.crypttext.extensions.actions;

import ca.corbett.extras.EnhancedAction;

import java.awt.event.ActionEvent;

/**
 * An action to toggle the visibility of the Snotes panel when invoked.
 *
 * @author <a href="https://github.com/scorbo2">scorbo2</a>
 */
public class SnotesAction extends EnhancedAction {

    public SnotesAction() {
        super("Show/hide Snotes panel");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO
        System.out.println("Here is where I would show the Snotes panel... IF I HAD ONE!");
    }
}
