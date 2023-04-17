package me.maximumpower55.diode.integration.mixin.techreborn;

import org.spongepowered.asm.mixin.Mixin;

import me.maximumpower55.diode.EnergyStorage;
import me.maximumpower55.diode.EnergyValue;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

@Mixin(team.reborn.energy.api.EnergyStorage.class)
public interface TREnergyStorageMixin extends EnergyStorage {

	@Override
	default EnergyValue amount() {
		return new EnergyValue(EnergyValue.DEFAULT_TYPE, ((team.reborn.energy.api.EnergyStorage) this).getAmount());
	}

	@Override
	default EnergyValue capacity() {
		return new EnergyValue(EnergyValue.DEFAULT_TYPE, ((team.reborn.energy.api.EnergyStorage) this).getCapacity());
	}

	@Override
	default EnergyValue receive(EnergyValue amount, TransactionContext transaction) {
		return new EnergyValue(EnergyValue.DEFAULT_TYPE, ((team.reborn.energy.api.EnergyStorage) this).insert(amount.get(EnergyValue.DEFAULT_TYPE), transaction));
	}
	@Override
	default boolean canReceive() {
		return ((team.reborn.energy.api.EnergyStorage) this).supportsInsertion();
	}

	@Override
	default EnergyValue extract(EnergyValue amount, TransactionContext transaction) {
		return new EnergyValue(EnergyValue.DEFAULT_TYPE, ((team.reborn.energy.api.EnergyStorage) this).extract(amount.get(EnergyValue.DEFAULT_TYPE), transaction));
	}
	@Override
	default boolean canExtract() {
		return ((team.reborn.energy.api.EnergyStorage) this).supportsExtraction();
	}

}
