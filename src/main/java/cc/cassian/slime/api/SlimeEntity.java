package cc.cassian.slime.api;

public interface SlimeEntity {
	default double slime$getEntityBounciness() {
		throw new AssertionError("Implemented via Mixin.");
	}
}
