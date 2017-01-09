package novous.internal;

import com.mumfrey.liteloader.LiteMod;

import java.io.File;

/**
 * Represents the mod container class which loads NovousAPI into LiteLoader, including
 * the Mixin patches.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class NovousMod implements LiteMod {

    public NovousMod(){}

    @Override
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public void init(File configPath) {

    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath) {

    }

    @Override
    public String getName() {
        return "Novous API";
    }

}
