package cc.cassian.springs.registry;

import cc.cassian.springs.SpringyThings;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class SpringyAttributes {
	public static final Holder<Attribute> BOUNCINESS = register("bounciness", new RangedAttribute("springthings.attribute.name.bounciness", 0.0, 0.0, 1.0).setSyncable(true));

	private static Holder<Attribute> register(final String name, final Attribute attribute) {
		return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, SpringyThings.of(name), attribute);
	}

	public static void touch() {

	}
}
