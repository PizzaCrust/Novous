package novous.api.block;

import novous.api.util.DomainPath;

/**
 * Abstraction of the Minecraft Block class. Can be used to create new blocks and can
 * process vanilla/other modded blocks.
 *
 * The default interface is final, but objects can be interacted with and it interacts with the
 * original object. Please check if the {@link Block} is instance of
 * the mutable interface, before automatically casting to the mutable interface.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public interface Block extends Physical {

    /**
     * Represents the textual name, or registry name to be used.
     * Example usage is in: /give (username) (domain):(resource_name)
     * @return
     */
    DomainPath getRegistryPath();

    /**
     * Represents the unlocalized, or default name to be used when a language file is not provided
     * /given inside of the mod.
     * @return
     */
    String getUnlocalizedName();

    /**
     * Represents the numerical ID of the block. It cannot conflict with other blocks from other
     * mods, including Vanilla Minecraft. However, in the future this will be automated.
     * @return
     */
    int getNumericalId();

    /**
     * Allows unrestricted access to readonly fields on {@link Block} abstraction.
     *
     * @since 1.0-SNAPSHOT
     * @author PizzaCrust
     */
    interface Mutable extends Block {

        void setRegistryPath(DomainPath domainPath);
        void setUnlocalizedName(String domainPath);
        void setNumericalId(int domainPath);

    }

}
