package net.nimbu.thaumaturgy.mixin;





import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.glints.ModRenderLayer;
import net.nimbu.thaumaturgy.item.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {



    @Inject(method = "getDirectItemGlintConsumer", at = @At(value = "HEAD"), cancellable = true)
    private static void getItemGlintConsumerMixin(VertexConsumerProvider provider,
                                                  RenderLayer layer,
                                                  boolean solid,
                                                  boolean glint,
                                                  CallbackInfoReturnable<VertexConsumer> cir){
        if (!glint || !solid) {
            return; // let vanilla handle non-glint rendering
        }
        //Identifier.of("minecraft", "textures/misc/white.png");
        //Identifier.of("thaumaturgy", "textures/item/pixie_dust.png")
        //this changes the return of the function getDirectItemGlintConsumer, ending the function immediately
        //cir.setReturnValue(VertexConsumers.union(provider.getBuffer( RenderLayer.getDebugFilledBox() ), provider.getBuffer(layer)));
        cir.setReturnValue(VertexConsumers.union(provider.getBuffer(ModRenderLayer.MOD_GLINT), provider.getBuffer(layer)));


        /*
        cir.setReturnValue(VertexConsumers.union(provider.getBuffer( RenderLayer.of(
                "water_mask",
                VertexFormats.POSITION,
                VertexFormat.DrawMode.QUADS,
                1536,
                RenderLayer.MultiPhaseParameters.builder().program(RenderPhase.WATER_MASK_PROGRAM).texture(RenderPhase.NO_TEXTURE).writeMaskState(RenderPhase.DEPTH_MASK).build(false)
        ) ), provider.getBuffer(layer)));*/
//.layering?



        //attempted to add the end portal render layer - did not cover the item sprite once again. reoccurring issue?
        //RenderLayer.getDebugFilledBox() managed to cover some of the sprite - in a very weird way albeit
        //getEntityCutoutNoCullZOffset() set entire sprite white with white texture, as did getOutline
    }


    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
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







    //Below is useful example code

    /*

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
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
}




/*
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
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