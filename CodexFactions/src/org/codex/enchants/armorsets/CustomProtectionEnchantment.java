package org.codex.enchants.armorsets;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class CustomProtectionEnchantment extends Enchantment{

	public static final CustomProtectionEnchantment enchant = new CustomProtectionEnchantment();

	public CustomProtectionEnchantment() {
		super(42);
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.ARMOR;
	}

	@Override
	public int getMaxLevel() {
		return 4;
	}

	@Override
	public String getName() {
		return "Protection";
	}

	@Override
	public int getStartLevel() {
		return 1;
	}

}
