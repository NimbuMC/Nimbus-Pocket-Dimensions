package net.nimbu.thaumaturgy.mixin;


import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.ModItems;
import net.nimbu.thaumaturgy.item.RevisualisedItemRenderer;
import net.nimbu.thaumaturgy.item.SpellFlashRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {




    //Changing item sprites
    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderItem/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/renderItem/VertexConsumerProvider;IILnet/minecraft/client/renderItem/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItemMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {

        if (stack.get(ModDataComponentTypes.REPLACE_MODEL_NAMESPACE)!=null && stack.get(ModDataComponentTypes.REPLACE_MODEL_PATH)!=null){
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(
                    Identifier.of(stack.get(ModDataComponentTypes.REPLACE_MODEL_NAMESPACE), stack.get(ModDataComponentTypes.REPLACE_MODEL_PATH))));
        }

        return bakedModel;
    }

//    @Shadow
//    @Final
//    @Mutable
//    private static Identifier ITEM_ENCHANTMENT_GLINT;
//
//    @Inject(method = "<clinit>", at = @At("TAIL"))
//    private static void replaceGlintTexture(CallbackInfo ci) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        if (client.player == null) return;
//
//        // Switch less frequently: use player block coordinates
//        int x = client.player.getBlockX();
//        int z = client.player.getBlockZ();
//
//        // Example: alternate glint every 5 blocks
//        if ((x / 5 + z / 5) % 2 == 0) {
//            ITEM_ENCHANTMENT_GLINT = Identifier.of(Thaumaturgy.MOD_ID, "textures/item/hammer.png");
//        } else {
//            ITEM_ENCHANTMENT_GLINT = Identifier.of(Thaumaturgy.MOD_ID, "textures/item/tome_of_expansion.png");
//        }
//    }

    //Adding addition effects

    @Inject(
            method = "renderItem(Lnet/minecraft/item/ItemStack;" +
                    "Lnet/minecraft/client/renderItem/model/json/ModelTransformationMode;" +
                    "ZLnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/renderItem/VertexConsumerProvider;" +
                    "IILnet/minecraft/client/renderItem/model/BakedModel;)V",
            at = @At("TAIL")
    )
    private void renderItemMixin(
            ItemStack stack,
            ModelTransformationMode renderMode,
            boolean leftHanded,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            int overlay,
            BakedModel model,
            CallbackInfo ci
    ) {
        //---------------------------Enchantment glints?-------------------------
        if (stack.getOrDefault(ModDataComponentTypes.REVISUALISED, false)){
            RevisualisedItemRenderer.renderItem(
                    stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay, model
            );}

        //---------------------------Spell flash rendering--------------------------
        if (stack.isOf(ModItems.STAFF) && stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER) != null) {

            int flashTimer = stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER);

            BakedModel flashModel;

            if (flashTimer>6) {
                flashModel = getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(
                        Identifier.of(Thaumaturgy.MOD_ID, "staff_spell_flash_0")));
            } else if (flashTimer>3) {
                flashModel = getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(
                        Identifier.of(Thaumaturgy.MOD_ID, "staff_spell_flash_1")));
            } else{
                flashModel=getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(
                        Identifier.of(Thaumaturgy.MOD_ID, "staff_spell_flash_2")));
            }

            SpellFlashRenderer.renderFlash(
                    flashModel, stack, renderMode, leftHanded, matrices, vertexConsumers, light, overlay
            );
        }
    }
}









//below is code hell dw about it
















/*
    @Inject(method = "getDirectItemGlintConsumer", at = @At(value = "HEAD"), cancellable = true)
    private static void getItemGlintConsumerMixin(VertexConsumerProvider provider,
                                                  RenderLayer layer,
                                                  boolean solid,
                                                  boolean glint,
                                                  CallbackInfoReturnable<VertexConsumer> cir){
        if (!glint || !solid) {
            return; // let vanilla handle non-glint rendering
        }

        //this changes the return of the function getDirectItemGlintConsumer, ending the function immediately


        cir.setReturnValue(VertexConsumers.union(provider.getBuffer(RenderLayer.getGlint()), provider.getBuffer(layer)));


    }*/












     /*
        cir.setReturnValue(VertexConsumers.union(provider.getBuffer( RenderLayer.of(
                "water_mask",
                VertexFormats.POSITION,
                VertexFormat.DrawMode.QUADS,
                1536,
                RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.WATER_MASK_PROGRAM).texture(RenderPhase.NO_TEXTURE).writeMaskState(RenderPhase.DEPTH_MASK).build(false)
        ) ), provider.getBuffer(layer)));*/
//.layering?



    //attempted to add the end portal renderItem layer - did not cover the item sprite once again. reoccurring issue?
    //RenderLayer.getDebugFilledBox() managed to cover some of the sprite - in a very weird way albeit
    //getEntityCutoutNoCullZOffset() set entire sprite white with white texture, as did getOutline


    //Below is useful example code

    /*

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderItem/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/renderItem/VertexConsumerProvider;IILnet/minecraft/client/renderItem/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItemMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        // (stack.getItem() == ModItems.SPECTRE_STAFF && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
        //    return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(TutorialMod.MOD_ID, "spectre_staff")));
        //}
        if (stack.getItem() == ModItems.SPIRIT_AXE){
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(Thaumaturgy.MOD_ID, "spirit_sword")));
        }
        return bakedModel;
    }
    */

    /*
    //controls enchantment glints on items
    @ModifyVariable(method = "getDirectItemGlintConsumer",
            at = @At(value = "HEAD"),
            index = 3)
    private static boolean getDirectItemGlintConsumer(boolean glint){

        return false;
    }*/

    //VertexConsumer custom = provider.getBuffer(ModRenderLayers.getCustomGlint());
    //cir.setReturnValue(custom); //this changes the return of the function getDirectItemGlintConsumer, ending the function immediately
    //if (ModGlintState.shouldUseCustomGlint()) {


    /*
    @ModifyVariable(method = "getDynamicDisplayGlintConsumer",
            at = @At(value = "HEAD"),
            index = 3)
    private static boolean getDynamicDisplayGlintConsumerMixin(boolean glint){
        System.out.println("getDynamicDisplayGlintConsumerMixin called");
        return true;
    }*/

    /*
    //ABSOLUTELY NO IDEA WHAT THIS FUNCTION IF FOR
    @ModifyVariable(method = "getItemGlintConsumer",
            at = @At(value = "HEAD"),
            index = 3)
    private static boolean getItemGlintConsumerMixin(boolean glint){
        System.out.println("getItemGlintConsumerMixin called");
        return true;
    }*/







    /*
    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() == ModItems.SPECTRE_STAFF) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(TutorialMod.MOD_ID, "spectre_staff_3d")));
        }

        return bakedModel;
    }*/





/*
import net.minecraft.client.renderItem.RenderLayer;
import net.minecraft.client.renderItem.VertexConsumer;
import net.minecraft.client.renderItem.VertexConsumerProvider;
import net.minecraft.client.renderItem.item.ItemRenderer;
import net.minecraft.resource.SynchronousResourceReloader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
abstract class ItemRendererMixin implements SynchronousResourceReloader { //only running for staffs (could have been the floating one) maybe? idk this shit doesnt work

    @Inject(method = "getItemGlintConsumer", at = @At(value = "HEAD"))
    private static void getItemGlintConsumerMixin(VertexConsumerProvider provider,
                                                  RenderLayer layer,
                                                  boolean solid,
                                                  boolean glint,
                                                  CallbackInfoReturnable<VertexConsumer> cir){
        System.out.println("Glint consumer fired.");
    }
}
*/