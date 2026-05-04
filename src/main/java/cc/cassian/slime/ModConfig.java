package cc.cassian.slime;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.DisplayName;
import folk.sisby.kaleido.lib.quiltconfig.api.values.ValueList;

public class ModConfig extends WrappedConfig {
	public SlimeTime slimeTime = new SlimeTime();
	public static class SlimeTime implements WrappedConfig.Section {
		@Comment("Whether to allow throwing slimeballs.")
		public boolean throwableSlimeballs = true;
		public boolean addSlimeBallToCombatTab = true;
	}

	public Client client = new Client();
	public static class Client implements WrappedConfig.Section {
		@Comment("Whether thrown slimeballs should emit particles")
		public boolean slimeballParticles = true;
	}

	public Bounciness bounciness = new Bounciness();
	public static class Bounciness implements WrappedConfig.Section {
		@Comment("Whether to allow bouncing horizontally, based on 26.2+ bounciness logic.")
		@Comment("Note that this replaces large parts of collision code, so mod conflicts may emerge.")
		public boolean horizontalBounciness = true;

		@Comment("Whether to allow bouncing vertically, based on 26.2+ bounciness logic.")
		@Comment("Note that this replaces large parts of collision code, so mod conflicts may emerge.")
		public boolean verticalBounciness = true;
	}

	public SlimeSling slimeSling = new SlimeSling();
	public static class SlimeSling implements WrappedConfig.Section {
		public float horizontalForceMultiplier = 1.5f;

		public float verticalForceMultiplier = 1f;
	}
}
