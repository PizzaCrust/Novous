package novous.api;

import java.util.ArrayList;
import java.util.List;

import novous.api.block.Block;
import novous.api.block.Physical;
import novous.api.util.Registry;

/**
 * An {@link Registry} pertaining to physical items, such as blocks.
 *
 * @since 1.0-SNAPSHOT
 * @author PizzaCrust
 */
public class PhysicalRegistry implements Registry<Physical> {

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
