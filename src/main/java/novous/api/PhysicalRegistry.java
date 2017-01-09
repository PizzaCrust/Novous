package novous.api;

import java.util.ArrayList;
import java.util.List;

import novous.api.block.Block;
import novous.api.block.BlockBase;
import novous.api.block.Physical;
import novous.api.util.DomainPath;
import novous.api.util.Registry;

/**
 * An {@link Registry} pertaining to physical items, such as blocks.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class PhysicalRegistry implements Registry<Physical> {

    private static PhysicalRegistry CURRENT_INSTANCE;

    public static PhysicalRegistry instance() {
        if (CURRENT_INSTANCE == null) {
            return new PhysicalRegistry();
        }
        return new PhysicalRegistry();
    }

    protected PhysicalRegistry() {
        //register(new BlockBase(new DomainPath("novous:test_block"),
        //    new DomainPath("minecraft:textures/blocks/iron_block.png"), "Test Block", 400));
    }

    private final List<Physical> physicalEntries = new ArrayList<>();

    @Override
    public List<Physical> getRegistrations() {
        return physicalEntries;
    }

    public List<Block> getRegisteredBlocks() {
        ArrayList<Block> blocks = new ArrayList<>();
        getRegistrations().forEach((physical) -> {
            if (physical instanceof Block) {
                blocks.add((Block) physical);
            }
        });
        return blocks;
    }

}
