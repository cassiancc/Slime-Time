package cc.cassian.slime;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
	@Comment("Whether to allow bouncing horizontally, based on 26.2+ bounciness logic.")
	@Comment("Note that this replaces large parts of collision code, so mod conflicts may emerge.")
	public boolean horizontalBounciness = true;

	@Comment("Whether to allow bouncing vertically, based on 26.2+ bounciness logic.")
	@Comment("Note that this replaces large parts of collision code, so mod conflicts may emerge.")
	public boolean verticalBounciness = true;

	@Comment("Whether to allow throwing slimeballs.")
	public boolean throwableSlimeballs = true;

	@Comment("Whether thrown slimeballs should emit particles")
	public boolean slimeballParticles = true;

	public boolean addSlimeBallToCombatTab = true;
}
