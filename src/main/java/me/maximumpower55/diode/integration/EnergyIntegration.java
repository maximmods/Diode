package me.maximumpower55.diode.integration;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;

import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.loader.api.FabricLoader;

public final class EnergyIntegration implements IMixinConfigPlugin {

	public static final Logger LOGGER = LogUtils.getLogger();

	private ImmutableMap<String, Pair<Runnable, String[]>> INTEGRATIONS = ImmutableMap.<String, Pair<Runnable, String[]>>builder()
		.put("team_reborn_energy", Pair.of(() -> {}, new String[]{"techreborn.TREnergyStorageMixin", "techreborn.DIEnergyStorageMixin"}))
		.build();

	private List<String> mixins = Lists.newArrayList();

	@Override
	public void onLoad(String mixinPackage) {
		for (var modId : FabricLoader.getInstance().getAllMods().stream().map(container -> container.getMetadata().getId()).collect(Collectors.toList())) {
			if (INTEGRATIONS.containsKey(modId)) {
				INTEGRATIONS.get(modId).left().run();
				for (var mixin : INTEGRATIONS.get(modId).right()) { mixins.add(mixin); }
			}
		}
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return mixins;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

}
