package com.github.kgb8375.foodsreimagined;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kgb8375.foodsreimagined.lists.FoodsReimaginedFoods;
import com.github.kgb8375.foodsreimagined.lists.FoodsReimgainedItems;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent;
import net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("foodsreimagined")
public class FoodsReimagined {
	public static FoodsReimagined instance;
	public static final String MODID = "foodsreimagined";
	private static final Logger LOGGER = LogManager.getLogger(MODID);
	
	public static final ItemGroup FOODSREIMAGINED = new FoodsReimaginedItemGroup();
	
	public FoodsReimagined()
	{
		instance = this;
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		MinecraftForge.EVENT_BUS.register(this);
		// Hooks for food events (e.g. desert debuffs)
	}
	
	private void setup(final FMLCommonSetupEvent event)
	{
		LOGGER.info("Setup method registered.");
	}
	
	private void clientRegistries(final FMLClientSetupEvent event)
	{
		LOGGER.info("clientRegistries method registered.");
	}

	// Register all of our items
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			event.getRegistry().registerAll(
				// Crafting Items
				FoodsReimgainedItems.SLICED_BREAD = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("sliced_bread")),
				FoodsReimgainedItems.TOASTED_BREAD = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("toasted_bread")),
				FoodsReimgainedItems.STOCK = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("stock")),
				FoodsReimgainedItems.CHEESE = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("cheese")),
				FoodsReimgainedItems.CHEESE_CURDS = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("cheese_curds")),
				FoodsReimgainedItems.BUTTER = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("butter")),
				FoodsReimgainedItems.SALT = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("salt")),
				FoodsReimgainedItems.SALT_BUCKET = new Item(new Item.Properties().group(FOODSREIMAGINED).containerItem(Items.BUCKET)).setRegistryName(location("salt_bucket")),
				
				// Tools
				FoodsReimgainedItems.KNIFE = new FoodsReimaginedTool(250).setRegistryName(location("knife")),
				FoodsReimgainedItems.CHURN = new FoodsReimaginedTool(0).setRegistryName(location("churn")),
				
				// Foods
				FoodsReimgainedItems.SANDWICH_TOAST = new FoodsReimaginedFood(FoodsReimaginedFoods.SNACK, false, 64).setRegistryName(location("sandwich_toast")),
				FoodsReimgainedItems.BORSCHT = new FoodsReimaginedFood(FoodsReimaginedFoods.LUNCH, true, 4).setRegistryName(location("borscht"))
			);
			
			LOGGER.info("Items registered.");
		}
	}
	
	// If the player uses the full buff they get a sugar crash
	@SubscribeEvent
	public void potionExpiryEvent(PotionExpiryEvent event) {
		
		Effect effect = event.getPotionEffect().getPotion();
		LivingEntity livingEntity = event.getEntityLiving();
		
		if(livingEntity instanceof PlayerEntity & effect == Effects.SPEED & livingEntity.getTags().contains("hyper"))
		{
			livingEntity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1200, 0));
			livingEntity.addPotionEffect(new EffectInstance(Effects.MINING_FATIGUE, 1200, 0));
			livingEntity.removeTag("hyper");
		}
	}
	
	// If the player uses a milk bucket to remove the buff they don't get a sugar crash
	@SubscribeEvent
	public void potionRemoveEvent(PotionRemoveEvent event) {
		
		Effect effect = event.getPotionEffect().getPotion();
		LivingEntity livingEntity = event.getEntityLiving();
		
		if(livingEntity instanceof PlayerEntity & effect == Effects.SPEED & livingEntity.getTags().contains("hyper"))
		{
			livingEntity.removeTag("hyper");
		}
	}
	
	private static ResourceLocation location(String name)
	{
		return new ResourceLocation(MODID, name);
	}
}
