package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gameevent.GameEvent;

public class SlimeGameEvents {
	public static final Holder.Reference<GameEvent> BOUNCE = register("bounce");

	private static Holder.Reference<GameEvent> register(final String name) {
		return register(name, 16);
	}

	private static Holder.Reference<GameEvent> register(final String name, final int notificationRadius) {
		return Registry.registerForHolder(BuiltInRegistries.GAME_EVENT, SlimeTime.of(name), new GameEvent(notificationRadius));
	}

	public static void touch() {

	}
}
