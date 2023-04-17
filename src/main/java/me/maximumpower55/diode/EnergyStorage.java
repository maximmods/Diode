package me.maximumpower55.diode;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public interface EnergyStorage {

	BlockApiLookup<EnergyStorage, Direction> SIDED = BlockApiLookup.get(new ResourceLocation("diode:sided_energy"), EnergyStorage.class, Direction.class);

	ItemApiLookup<EnergyStorage, ContainerItemContext> ITEM = ItemApiLookup.get(new ResourceLocation("diode:energy"), EnergyStorage.class, ContainerItemContext.class);


	EnergyValue amount();
	EnergyValue capacity();

	EnergyValue receive(EnergyValue amount, TransactionContext transaction);
	default boolean canReceive() {
		return true;
	}

	EnergyValue extract(EnergyValue amount, TransactionContext transaction);
	default boolean canExtract() {
		return true;
	}

}
