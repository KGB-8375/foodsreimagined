package com.github.kgb8375.foodsreimagined;

import com.github.kgb8375.foodsreimagined.lists.FoodsReimaginedFoods;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class FoodsReimaginedFood extends Item {

	private Boolean isBowl;
	private Food foodType;
	
	public FoodsReimaginedFood(Food foodType, Boolean isSoup, int maxStackSize) {
		super(new Item.Properties().group(FoodsReimagined.FOODSREIMAGINED).food(foodType).maxStackSize(maxStackSize));
		this.isBowl = isSoup;
		this.foodType = foodType;
	}
	

   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
	  
      // If the item is a soup drop an extra bowl
      if(isBowl)
    	  entityLiving.entityDropItem(new ItemStack(Items.BOWL));
      
      if(foodType == FoodsReimaginedFoods.DESERT)
      {
    	  entityLiving.addTag("hyper");
      }
      
      // Eats the food and removes one from the stack
      return super.onItemUseFinish(stack, worldIn, entityLiving);
   }
}
