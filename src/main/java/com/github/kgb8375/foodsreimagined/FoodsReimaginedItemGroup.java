package com.github.kgb8375.foodsreimagined;

import com.github.kgb8375.foodsreimagined.lists.FoodsReimaginedItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FoodsReimaginedItemGroup extends ItemGroup {
	public FoodsReimaginedItemGroup()
	{
		super("foodsreimagined");
	}
	
	@Override
	public ItemStack createIcon() 
	{
		return new ItemStack(FoodsReimaginedItems.SLICED_BREAD);
	}
}
