package net.nimbu.thaumaturgy.renderer;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

import java.util.List;
import java.util.Random;

public class LobotomyVisualState {

    private static final Random RANDOM = new Random();

    private static final List<Identifier> IMAGES = List.of(
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy1.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy2.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy3.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy4.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy5.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy6.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy7.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy8.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy9.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy10.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy11.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy12.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy13.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy14.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy15.png"),
            Identifier.of(Thaumaturgy.MOD_ID, "textures/gui/lobotomy/lobotomy16.png")
    );

    public static Identifier currentImage = null;
    public static int ticksRemaining = 0;
    public static int maxTicks = 200; // ~0.5 seconds (20 tps)

    public static void trigger() {
        currentImage = IMAGES.get(RANDOM.nextInt(IMAGES.size()));
        ticksRemaining = maxTicks;
    }
}
