package ca.corbett.crypttext.extensions;

import ca.corbett.crypttext.AppConfig;
import ca.corbett.crypttext.extensions.actions.SnotesAction;
import ca.corbett.crypttext.extensions.ui.SnotesPanel;
import ca.corbett.crypttext.ui.actions.UIReloadAction;
import ca.corbett.extensions.AppExtensionInfo;
import ca.corbett.extras.io.KeyStrokeManager;
import ca.corbett.extras.properties.AbstractProperty;
import ca.corbett.extras.properties.BooleanProperty;
import ca.corbett.extras.properties.KeyStrokeProperty;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This CryptText application extension brings the Snotes application into CryptText,
 * in the form of a toggleable panel. View, search, or create notes within CryptText,
 * exactly as you could with the Snotes standalone application. The key difference here,
 * is that we get the encryption feature offered by CryptText for free! Now, your notes
 * can be easily encrypted upon save, if you wish.
 *
 * @author <a href="https://github.com/scorbo2">scorbo2</a>
 */
public class SnotesExtension extends CryptTextExtension {

    private static final Logger log = Logger.getLogger(SnotesExtension.class.getName());
    private static final String extInfoLocation = "/ca/corbett/crypttext/extensions/snotes/extInfo.json";
    private static final String KEY_PROP = AppConfig.KEYSTROKE_PREFIX + "Snotes.snotesKey";
    private static final String PANEL_PROP = "Snotes.Snotes options.panelVisible";

    private final AppExtensionInfo extInfo;
    private final SnotesAction snotesAction;
    private final SnotesPanel snotesPanel;
    private final JCheckBoxMenuItem panelMenuItem;

    public SnotesExtension() {
        extInfo = AppExtensionInfo.fromExtensionJar(getClass(), extInfoLocation);
        if (extInfo == null) {
            throw new RuntimeException("SnotesExtensino: can't parse extInfo.json!");
        }
        this.snotesAction = new SnotesAction(this);
        this.snotesPanel = new SnotesPanel();
        this.panelMenuItem = new JCheckBoxMenuItem(snotesAction);
    }

    @Override
    public AppExtensionInfo getInfo() {
        return extInfo;
    }

    @Override
    public void onActivate() {
        panelMenuItem.setState(isSnotesVisible());
        snotesPanel.resetLayout();
    }

    @Override
    public void onDeactivate() {
        snotesPanel.dispose();
    }

    @Override
    protected List<AbstractProperty> createConfigProperties() {
        List<AbstractProperty> list = new ArrayList<>();

        // The hotkey to toggle panel visibility:
        list.add(new KeyStrokeProperty(KEY_PROP,
                                        "Snotes panel:",
                                        KeyStrokeManager.parseKeyStroke("F8"),
                                        snotesAction)
                           .setAllowBlank(true)
                           .setHelpText("Show or hide the Snotes panel"));

        // The actual "is visible" property:
        list.add(new BooleanProperty(PANEL_PROP,
                                     "Show Snotes panel",
                                     true));

        return list;
    }

    @Override
    protected void loadJarResources() {
        // Nothing to load here yet. But we will likely have icons and templates at some point...
    }

    @Override
    public List<JMenu> getTopLevelMenus() {
        JMenu snotesMenu = new JMenu("Snotes");
        snotesMenu.add(panelMenuItem);
        return List.of(snotesMenu);
    }

    /**
     * We will return our SnotesPanel for the RIGHT position, if it is configured to be visible.
     * Otherwise, null. Note: the position is currently not configurable. This is consistent with
     * the other CryptText extensions. But it would be pretty straightforward to make it configurable,
     * by following the example of other applications, like ImageViewer.
     */
    @Override
    public JComponent getExtraComponent(ExtraComponentPosition position) {
        if (position == ExtraComponentPosition.RIGHT && isSnotesVisible()) {
            return snotesPanel;
        }
        return null;
    }

    /**
     * Toggles the visibility of the Snotes panel. This is invoked by the SnotesAction when its hotkey is pressed,
     * and also by the JCheckBoxMenuItem in the Snotes menu when it is clicked.
     * This triggers an immediate UI reload, so the results of toggling the panel visibility are visible right away.
     */
    public void togglePanelVisibility() {
        // Toggle the "is visible" property for the Snotes panel:
        AbstractProperty prop = AppConfig.getInstance().getPropertiesManager().getProperty(PANEL_PROP);
        if (!(prop instanceof BooleanProperty booleanProp)) {
            log.severe("SnotesExtension: unable to find configured property!");
            return;
        }
        boolean isVisibleNow = !booleanProp.getValue();
        booleanProp.setValue(isVisibleNow);
        AppConfig.getInstance().save(); // Trigger an immediate save so the value is persisted
        panelMenuItem.setState(isVisibleNow);
        UIReloadAction.getInstance().actionPerformed(null); // Trigger immediate UI reload
    }

    /**
     * Looks up the current value of our config prop and returns it if found.
     */
    private boolean isSnotesVisible() {
        AbstractProperty prop = AppConfig.getInstance().getPropertiesManager().getProperty(PANEL_PROP);
        if (prop instanceof BooleanProperty boolProp) {
            return boolProp.getValue();
        }
        return false;
    }
}
