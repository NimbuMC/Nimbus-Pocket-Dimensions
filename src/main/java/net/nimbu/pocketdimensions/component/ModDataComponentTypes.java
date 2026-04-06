//package net.nimbu.pocketdimensions.component;
//
//import com.mojang.serialization.Codec;
//import net.minecraft.component.ComponentType;
//import net.minecraft.registry.Registries;
//import net.minecraft.registry.Registry;
//import net.minecraft.util.Identifier;
//import net.nimbu.pocketdimensions.PocketDimensions;
//import org.ladysnake.cca.api.v3.component.ComponentKey;
//import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
//
//import java.util.function.UnaryOperator;
//
//public class ModDataComponentTypes {
//
//    //Allows for what are effectively variables to be added to instances of items
//
//
//
//
//
//    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
//        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(PocketDimensions.MOD_ID, name),
//                builderOperator.apply(ComponentType.builder()).build());
//    }
//
//    public static void registerDataComponentTypes(){
//        PocketDimensions.LOGGER.info("Registering data component types for "+ PocketDimensions.MOD_ID);
//    }
//}
