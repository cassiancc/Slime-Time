package cc.cassian.springs.api;

public interface SpringyBlock {
	default float springthings$getBounceRestitution() {
		throw new AssertionError("Implemented via Mixin.");
	}
}
