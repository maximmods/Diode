package me.maximumpower55.diode;

import java.util.HashMap;

import it.unimi.dsi.fastutil.Pair;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public class EnergyValue implements Comparable<EnergyValue> {

	public static final ResourceLocation DEFAULT_TYPE = new ResourceLocation("diode", "default");

	public static final EnergyValue ZERO = new EnergyValue(DEFAULT_TYPE, 0);
	public static final EnergyValue MAX = new EnergyValue(DEFAULT_TYPE, Long.MAX_VALUE);

	private static final HashMap<Pair<ResourceLocation, ResourceLocation>, Converter> CONVERTERS = new HashMap<>();

	public static void registerConverter(Pair<ResourceLocation, ResourceLocation> typePair, Converter converter) {
		CONVERTERS.putIfAbsent(typePair, converter);
	}

	public final ResourceLocation typeId;
	private long value;

	public EnergyValue(ResourceLocation typeId, long value) {
		this.typeId = typeId;
		this.value = value;
	}

	public long get() {
		return this.value;
	}

	public long get(ResourceLocation typeId) {
		if (typeId != this.typeId) {
			final var typePair = Pair.of(this.typeId, typeId);
			if (CONVERTERS.containsKey(typePair)) return CONVERTERS.get(typePair).convert(this.value);
		}
		return this.value;
	}

	public static EnergyValue fromNBT(CompoundTag nbt) {
		return new EnergyValue(new ResourceLocation(nbt.getString("type")), nbt.getLong("value"));
	}

	public CompoundTag writeNBT() {
		final var nbt = new CompoundTag();
		nbt.putLong("value", value);
		nbt.putString("type", typeId.toString());
		return nbt;
	}

	@Override
	public int compareTo(EnergyValue value) {
		if (this.value == value.get(this.typeId)) return 0;
		return this.value > value.get(this.typeId) ? 1 : -1;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EnergyValue value) return this.value == value.get(typeId);
		return false;
	}

	@FunctionalInterface
	public static interface Converter {
		long convert(long value);
	}

}
