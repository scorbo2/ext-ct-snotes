package ca.corbett.crypttext.extensions;

import ca.corbett.crypttext.AppConfig;
import ca.corbett.crypttext.extensions.actions.SnotesAction;
import ca.corbett.extensions.AppExtensionInfo;
import ca.corbett.extras.io.KeyStrokeManager;
import ca.corbett.extras.properties.AbstractProperty;
import ca.corbett.extras.properties.KeyStrokeProperty;

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

    private final AppExtensionInfo extInfo;
    private final SnotesAction snotesAction;

    public SnotesExtension() {
        extInfo = AppExtensionInfo.fromExtensionJar(getClass(), extInfoLocation);
        if (extInfo == null) {
            throw new RuntimeException("SnotesExtensino: can't parse extInfo.json!");
        }
        this.snotesAction = new SnotesAction();
    }

    @Override
    public AppExtensionInfo getInfo() {
        return extInfo;
    }

    @Override
    protected List<AbstractProperty> createConfigProperties() {
        List<AbstractProperty> list = new ArrayList<>();

        list.add(new KeyStrokeProperty(KEY_PROP,
                                        "Snotes panel:",
                                        KeyStrokeManager.parseKeyStroke("F8"),
                                        snotesAction)
                           .setAllowBlank(true)
                           .setHelpText("Show or hide the Snotes panel"));

        return list;
    }

    @Override
    protected void loadJarResources() {
        // Nothing to load here yet. But we will likely have icons and templates at some point...
    }
}
