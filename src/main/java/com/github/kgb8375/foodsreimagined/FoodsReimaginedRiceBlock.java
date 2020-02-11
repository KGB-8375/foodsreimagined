package com.github.kgb8375.foodsreimagined;

import java.util.Random;

import com.github.kgb8375.foodsreimagined.lists.FoodsReimaginedItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// Custom Rice Class
public class FoodsReimaginedRiceBlock extends CropsBlock {
	private static final IntegerProperty RICE_AGE = BlockStateProperties.AGE_0_5;

	protected FoodsReimaginedRiceBlock(Block.Properties properties) {
		super(properties);
	}
	
	@Override
	public IntegerProperty getAgeProperty() {
		return RICE_AGE;
	}
	
	@Override
	public int getMaxAge() {
		return 5;
	}
	
	@Override
	protected IItemProvider getSeedsItem() {
		return FoodsReimaginedItems.RICE_SEEDS;
	}
	
	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
	    builder.add(RICE_AGE);
	}
}
