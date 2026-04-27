package cc.cassian.slime.api;

public interface SlimeBlock {
	default float slime$getBounceRestitution() {
		throw new AssertionError("Implemented via Mixin.");
	}
}
