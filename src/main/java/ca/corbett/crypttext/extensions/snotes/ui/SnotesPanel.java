package ca.corbett.crypttext.extensions.snotes.ui;

import ca.corbett.crypttext.extensions.snotes.SnotesExtension;
import ca.corbett.crypttext.extensions.snotes.threads.SnotesLoaderThread;
import ca.corbett.crypttext.ui.MainWindow;
import ca.corbett.extras.LookAndFeelManager;
import ca.corbett.extras.progress.MultiProgressDialog;
import ca.corbett.extras.progress.SimpleProgressAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    private final SnotesExtension extension;

    public SnotesPanel(SnotesExtension owner) {
        setMinimumSize(new Dimension(PANEL_WIDTH, 1));
        setPreferredSize(new Dimension(PANEL_WIDTH, 1));
        this.extension = owner;
        add(new JLabel("TODO")); // just getting the panel into place for now...
        JButton tempButton = new JButton("Load all");
        tempButton.addActionListener(e -> testLoad());
        add(tempButton);
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
        LookAndFeelManager.removeChangeListener(this);
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

    /**
     * This is temporary code until we get a proper UI built for this extension.
     * Right now I just want to test our loader thread and see if we can load content.
     */
    private void testLoad() {
        SnotesLoaderThread loaderThread = new SnotesLoaderThread(extension.getConfiguredDataDirectory());
        loaderThread.addProgressListener(new TempListener(loaderThread));
        MultiProgressDialog dialog = new MultiProgressDialog(MainWindow.getInstance(), "Loading...");
        dialog.runWorker(loaderThread, true);
    }

    /**
     * This will be removed once our UI is in place. Just for testing the model and IO code.
     */
    private class TempListener extends SimpleProgressAdapter {
        private final SnotesLoaderThread thread;

        public TempListener(SnotesLoaderThread thread) {
            this.thread = thread;
        }

        @Override
        public void progressComplete() {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Load complete! Loaded "
                    + thread.getSearchResults().size() + " notes.");
        }

        @Override
        public void progressCanceled() {
            JOptionPane.showMessageDialog(MainWindow.getInstance(), "Oh no! Load was canceled. I got "
                    + thread.getSearchResults().size() + " notes before I was interrupted.");

        }
    }
}
