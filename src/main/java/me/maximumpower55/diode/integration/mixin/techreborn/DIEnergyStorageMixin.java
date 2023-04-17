package me.maximumpower55.diode.integration.mixin.techreborn;

import org.spongepowered.asm.mixin.Mixin;

import me.maximumpower55.diode.EnergyValue;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import team.reborn.energy.api.EnergyStorage;

@Mixin(me.maximumpower55.diode.EnergyStorage.class)
public interface DIEnergyStorageMixin extends EnergyStorage {

	@Override
	default long getAmount() {
		return ((me.maximumpower55.diode.EnergyStorage) this).amount().get(EnergyValue.DEFAULT_TYPE);
	}

	@Override
	default long getCapacity() {
		return ((me.maximumpower55.diode.EnergyStorage) this).capacity().get(EnergyValue.DEFAULT_TYPE);
	}

	@Override
	default long insert(long amount, TransactionContext transaction) {
		return ((me.maximumpower55.diode.EnergyStorage) this).receive(new EnergyValue(EnergyValue.DEFAULT_TYPE, amount), transaction).get(EnergyValue.DEFAULT_TYPE);
	}
	@Override
	default boolean supportsInsertion() {
		return ((me.maximumpower55.diode.EnergyStorage) this).canReceive();
	}

	@Override
	default long extract(long amount, TransactionContext transaction) {
		return ((me.maximumpower55.diode.EnergyStorage) this).extract(new EnergyValue(EnergyValue.DEFAULT_TYPE, amount), transaction).get(EnergyValue.DEFAULT_TYPE);
	}
	@Override
	default boolean supportsExtraction() {
		return ((me.maximumpower55.diode.EnergyStorage) this).canExtract();
	}

}
