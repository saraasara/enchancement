/*
 * All Rights Reserved (c) MoriyaShiine
 */

package moriyashiine.enchancement.mixin.vanillachanges.singlelevelmode;

import moriyashiine.enchancement.common.ModConfig;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
	@ModifyVariable(method = "addEnchantment", at = @At("HEAD"), argsOnly = true)
	private int enchancement$singleLevelMode(int value) {
		if (ModConfig.singleLevelMode) {
			return 1;
		}
		return value;
	}
}
