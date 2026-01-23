package net.nimbu.thaumaturgy.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

public class ModTags {
    public static class Blocks {

        /*
        - "incorrect_for_iron_tool" is applied to all blocks with the "needs_diamond_tool" tag
        - all blocks with the "incorrect_for_iron_tool" tag have the "needs_spirit_tool" tag removed

        - "needs_[level]_tool" is the tag used to specify blocks which can ONLY be mined by that level tool
        and above.
        - "incorrect_for_[level]_tool" is the tag applied to everything below. can also be applied to blocks
        above in order to make a block only mine-able by iron but not by diamond, for example, as THIS is
        the tag that is checked when mining a block?
        */
        public static final TagKey<Block> NEEDS_SPIRIT_TOOL = createTag("needs_spirit_tool");
        public static final TagKey<Block> INCORRECT_FOR_SPIRIT_TOOL = createTag("incorrect_for_spirit_tool");



        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Thaumaturgy.MOD_ID, name));
        }

        public static class Items {

            private static TagKey<Item> createTag(String name) {
                return  TagKey.of(RegistryKeys.ITEM, Identifier.of(Thaumaturgy.MOD_ID, name));
            }
        }
    }
}
