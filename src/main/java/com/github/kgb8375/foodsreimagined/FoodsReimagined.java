package com.github.kgb8375.foodsreimagined;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kgb8375.foodsreimagined.lists.FoodsReimaginedBlocks;
import com.github.kgb8375.foodsreimagined.lists.FoodsReimaginedFoods;
import com.github.kgb8375.foodsreimagined.lists.FoodsReimaginedItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.LootTableLoadEvent;
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

	// Register all of our items (and extra loot table
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents
	{
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			event.getRegistry().registerAll
			(
				// Crafting Items
				FoodsReimaginedItems.SLICED_BREAD = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("sliced_bread")),
				FoodsReimaginedItems.TOASTED_BREAD = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("toasted_bread")),
				FoodsReimaginedItems.STOCK = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("stock")),
				FoodsReimaginedItems.CHEESE = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("cheese")),
				FoodsReimaginedItems.CHEESE_CURDS = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("cheese_curds")),
				FoodsReimaginedItems.BUTTER = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("butter")),
				FoodsReimaginedItems.SALT = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("salt")),
				FoodsReimaginedItems.SALT_BUCKET = new Item(new Item.Properties().group(FOODSREIMAGINED).containerItem(Items.BUCKET)).setRegistryName(location("salt_bucket")),
				
				// Tools
				FoodsReimaginedItems.KNIFE = new FoodsReimaginedTool(250).setRegistryName(location("knife")),
				FoodsReimaginedItems.CHURN = new FoodsReimaginedTool(0).setRegistryName(location("churn")),
				
				// Foods
				FoodsReimaginedItems.SANDWICH_TOAST = new FoodsReimaginedFood(FoodsReimaginedFoods.SNACK, false, 64).setRegistryName(location("sandwich_toast")),
				FoodsReimaginedItems.BORSCHT = new FoodsReimaginedFood(FoodsReimaginedFoods.LUNCH, true, 4).setRegistryName(location("borscht")),
				
				// Crops
				FoodsReimaginedItems.RICE = new Item(new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(FoodsReimaginedBlocks.RICE.getRegistryName()),
				FoodsReimaginedItems.RICE_SEEDS = new BlockNamedItem(FoodsReimaginedBlocks.RICE, new Item.Properties().group(FOODSREIMAGINED)).setRegistryName(location("rice_seeds"))
			);
			
			LOGGER.info("Items registered.");
		}
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event)
		{
			event.getRegistry().registerAll
			(
				FoodsReimaginedBlocks.RICE = new FoodsReimaginedRiceBlock(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0F).sound(SoundType.CROP)).setRegistryName(location("rice"))
			);
			
			LOGGER.info("Blocks registered.");
		}
	}
	
	// If the player uses the full buff they get a sugar crash
	@SubscribeEvent
	public void potionExpiryEvent(PotionExpiryEvent event) 
	{
		
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
	public void potionRemoveEvent(PotionRemoveEvent event) 
	{
		
		Effect effect = event.getPotionEffect().getPotion();
		LivingEntity livingEntity = event.getEntityLiving();
		
		if(livingEntity instanceof PlayerEntity & effect == Effects.SPEED & livingEntity.getTags().contains("hyper"))
		{
			livingEntity.removeTag("hyper");
		}
	}
	
	// Adds rice seeds to vanilla grass loot table
	@SubscribeEvent
	public void onLootLoad(LootTableLoadEvent event) {
		if (event.getName().equals(new ResourceLocation("minecraft", "blocks/grass"))) {
			event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(location("blocks/grass"))).build());
		}
	}
	
	private static ResourceLocation location(String name)
	{
		return new ResourceLocation(MODID, name);
	}
}
