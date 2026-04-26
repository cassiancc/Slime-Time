package cc.cassian.springs.api;

public interface SpringyEntity {
	default double springthings$getEntityBounciness() {
		throw new AssertionError("Implemented via Mixin.");
	}
}
