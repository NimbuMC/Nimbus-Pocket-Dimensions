package net.nimbu.thaumaturgy.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    //Allows for what are effectively variables to be added to instances of items

    public static final ComponentType<Integer> SPELL_FLASH_TIMER =
            register("spell_flash_timer", builder ->  builder.codec(Codec.INT));
    public static final ComponentType<Integer> EQUIPPED_SPELL_COLOUR =
            register("equipped_spell_colour", builder -> builder.codec(Codec.INT));

    public static final ComponentType<Boolean> REVISUALISED =
            register("revisualised", booleanBuilder -> booleanBuilder.codec(Codec.BOOL));

    public static final ComponentType<String> REPLACE_MODEL_NAMESPACE =
            register("replace_model_namespace", stringBuilder -> stringBuilder.codec(Codec.STRING));
    public static final ComponentType<String> REPLACE_MODEL_PATH =
            register("replace_model_path", stringBuilder -> stringBuilder.codec(Codec.STRING));



    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Thaumaturgy.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes(){
        Thaumaturgy.LOGGER.info("Registering data component types for "+Thaumaturgy.MOD_ID);
    }
}
