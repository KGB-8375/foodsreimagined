package com.github.kgb8375.foodsreimagined;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FoodsReimaginedTool extends Item {
	
	public FoodsReimaginedTool(int durability) {
		super(new Item.Properties().group(FoodsReimagined.FOODSREIMAGINED).maxDamage(durability).setNoRepair());
	}
	
	/*
	 * A container item is simply the item given back from crafting.
	 */
	@Override
	public boolean hasContainerItem()
	{
		return true;
	}
	
	/*
	 * First test to see if the item should break. If it should, don't give anything back (air). If it shouldn't, remove one 
	 * durability from the stack and give it back.
	 */
	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		if(itemStack.isDamageable())
		{
			int damage = itemStack.getDamage();
			if(damage < itemStack.getMaxDamage()) //
			{
				++damage;
				ItemStack damaged_tool = new ItemStack(this.asItem());
				damaged_tool.setDamage(damage);
				return damaged_tool;
			}
			else
				return new ItemStack(Items.AIR);
		}
		else
			return new ItemStack(this.asItem());
	}
}
