import org.jetbrains.annotations.Nullable;

import io.github.coolcrabs.brachyura.decompiler.BrachyuraDecompiler;
import io.github.coolcrabs.brachyura.decompiler.fernflower.FernflowerDecompiler;
import io.github.coolcrabs.brachyura.fabric.FabricLoader;
import io.github.coolcrabs.brachyura.fabric.FabricMaven;
import io.github.coolcrabs.brachyura.fabric.SimpleFabricProject;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyCollector;
import io.github.coolcrabs.brachyura.fabric.FabricContext.ModDependencyFlag;
import io.github.coolcrabs.brachyura.maven.Maven;
import io.github.coolcrabs.brachyura.maven.MavenId;
import io.github.coolcrabs.brachyura.minecraft.Minecraft;
import io.github.coolcrabs.brachyura.minecraft.VersionMeta;
import io.github.coolcrabs.brachyura.quilt.QuiltMaven;
import net.fabricmc.mappingio.tree.MappingTree;

public class Buildscript extends SimpleFabricProject {
	private Versions versions = new Versions(getProjectDir().resolve("buildscript").resolve("versions.properties"));

	@Override
	public int getJavaVersion() {
		return Integer.parseInt(versions.JAVA.get());
	}

	@Override
	public @Nullable BrachyuraDecompiler decompiler() {
		return new FernflowerDecompiler(Maven.getMavenJarDep(QuiltMaven.URL, new MavenId("org.quiltmc", "quiltflower", versions.QUILTFLOWER.get())));
	}

	@Override
	public FabricLoader getLoader() {
		return new FabricLoader(FabricMaven.URL, FabricMaven.loader(versions.FABRIC_LOADER.get()));
	}

	@Override
	public VersionMeta createMcVersion() {
		return Minecraft.getVersion(versions.MINECRAFT.get());
	}

	@Override
	public MappingTree createMappings() {
		return createMojmap();
	}

	@Override
	public void getModDependencies(ModDependencyCollector d) {
		for (String[] module : new String[][] {
			{"fabric-api-base", "0.4.23+9ff28bce8b"},
			{"fabric-lifecycle-events-v1", "2.2.14+5da15ca1cc"},
			{"fabric-api-lookup-api-v1", "1.6.19+49abcf7e85"},
			{"fabric-transfer-api-v1", "2.1.15+ccd377ba23"},
			{"fabric-rendering-fluids-v1", "3.0.18+f1e4495b23"}
		}) {
			d.addMaven(FabricMaven.URL, new MavenId(FabricMaven.GROUP_ID + ".fabric-api", module[0], module[1]), ModDependencyFlag.COMPILE, ModDependencyFlag.RUNTIME);
		}

		d.addMaven(FabricMaven.URL, new MavenId("teamreborn:energy:2.3.0"), ModDependencyFlag.COMPILE, ModDependencyFlag.RUNTIME);
	}

	@Override
	public String getMavenGroup() {
		return "me.maximumpower55";
	}
}
