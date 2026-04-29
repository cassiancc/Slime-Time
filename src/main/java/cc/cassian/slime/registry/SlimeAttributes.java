package cc.cassian.slime.registry;

import cc.cassian.slime.SlimeTime;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public interface SlimeAttributes {
	Holder<Attribute> BOUNCINESS = register("bounciness", new RangedAttribute("attribute.name.bounciness", 0.0, 0.0, 1.0).setSyncable(true));

	private static Holder<Attribute> register(final String name, final Attribute attribute) {
		return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, SlimeTime.of(name), attribute);
	}

	static void touch() {

	}
}
