package cc.cassian.springs;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
	@Comment("Whether to use 26.2+ bounciness logic. Note that this replaces large parts of collision code, so mod conflicts may emerge.")
	@Comment("When disabled, only basic bounciness logic is present.")
	public boolean advancedHorizontalBounciness = true;
	@Comment("Whether to use 26.2+ bounciness logic. Note that this replaces large parts of collision code, so mod conflicts may emerge.")
	@Comment("When disabled, only basic bounciness logic is present.")
	public boolean advancedVerticalBounciness = true;
}
