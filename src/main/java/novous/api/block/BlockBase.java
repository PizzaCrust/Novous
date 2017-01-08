package novous.api.block;

import com.google.common.base.Preconditions;

import novous.api.util.DomainPath;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This class implements some functionality to some degree like the regular Minecraft block.
 * However, the class cannot be used for references of blocks. References should directly go to
 * the superinterface, {@link Block} or {@link Block.Mutable}.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class BlockBase implements Block.Mutable {

    private DomainPath registryPath;
    private DomainPath texturePath;

    private String unlocalizedName;

    private int numericalId;

    public BlockBase(){}

    public BlockBase(DomainPath registryPath, DomainPath texturePath, String unlocalizedName,
                     int numericalId) {
        this.setRegistryPath(registryPath);
        this.setTexturePath(texturePath);
        this.setUnlocalizedName(unlocalizedName);
        this.setNumericalId(numericalId);
    }

    @Override
    public DomainPath getRegistryPath() {
        return this.registryPath;
    }

    @Override
    public DomainPath getTexturePath() {
        return this.texturePath;
    }

    @Override
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    @Override
    public int getNumericalId() {
        return this.numericalId;
    }

    @Override
    public void setRegistryPath(DomainPath domainPath) {
        checkNotNull(domainPath);
        this.registryPath = domainPath;
    }

    @Override
    public void setTexturePath(DomainPath domainPath) {
        checkNotNull(domainPath);
        this.texturePath = domainPath;
    }

    @Override
    public void setUnlocalizedName(String domainPath) {
        checkNotNull(domainPath);
        this.unlocalizedName = domainPath;
    }

    @Override
    public void setNumericalId(int domainPath) {
        this.numericalId = domainPath;
    }
}
