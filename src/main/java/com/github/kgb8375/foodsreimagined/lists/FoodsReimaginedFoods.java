package com.github.kgb8375.foodsreimagined.lists;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class FoodsReimaginedFoods {
	// List of all food types
	public static final Food SNACK = (new Food.Builder()).hunger(4).saturation(0.625F).build();
	public static final Food BREAKFAST = (new Food.Builder()).hunger(7).saturation(0.64F).effect(new EffectInstance(Effects.SPEED, 3600, 0), 1.0F).build();
	public static final Food LUNCH = (new Food.Builder()).hunger(10).saturation(0.7F).build();
	public static final Food DINNER = (new Food.Builder()).hunger(14).saturation(0.429F).effect(new EffectInstance(Effects.SLOWNESS, 400, 0), 1.0F).build();
	// Desert's debuffs are in Food and Events
	public static final Food DESERT = (new Food.Builder()).hunger(4).saturation(0.375F).effect(new EffectInstance(Effects.SPEED, 3600, 0), 1.0F).effect(new EffectInstance(Effects.HASTE, 3600, 0), 1.0F).build();
}
