package ca.corbett.crypttext.extensions.ui;

import ca.corbett.extras.LookAndFeelManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Dimension;

/**
 * A content panel for the Snotes extension. Contains the following:
 * <ul>
 *     <li>A tree view of the Snotes directory at the top of the panel.</li>
 *     <li>An ActionPanel with action links for each of the following:
 *         <ul>
 *             <li>create new Notes based on existing Templates.</li>
 *             <li>search through Notes based on existing Queries.</li>
 *             <li>edit Template library.</li>
 *             <li>edit Query library.</li>
 *         </ul>
 *     </li>
 * </ul>
 * <p>
 * TODO this panel is currently a placeholder. Actual code will follow.
 * </p>
 *
 * @author <a href="https://github.com/scorbo2">scorbo2</a>
 */
public class SnotesPanel extends JPanel implements ChangeListener {

    private static final int PANEL_WIDTH = 210; // maybe make this configurable

    public SnotesPanel() {
        setMinimumSize(new Dimension(PANEL_WIDTH, 1));
        setPreferredSize(new Dimension(PANEL_WIDTH, 1));
        add(new JLabel("TODO")); // just getting the panel into place for now...
    }

    /**
     * Invoke this when the panel is no longer needed, to perform cleanup.
     * This method is idempotent - it's safe to invoke even if it's already been invoked.
     */
    public void dispose() {
        LookAndFeelManager.removeChangeListener(this);
    }

    /**
     * Resets the layout of this panel and all of its contents.
     */
    public void resetLayout() {
        LookAndFeelManager.addChangeListener(this);
    }

    /**
     * Invoked from LookAndFeelManager when the look and feel of the application changes,
     * so that this panel can update its own look and feel to match.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
