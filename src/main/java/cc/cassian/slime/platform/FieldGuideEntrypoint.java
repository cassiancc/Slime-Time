package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.api.VariatedSlimeAccess;
import com.evandev.fieldguide.api.variant.VariantDef;
import com.evandev.fieldguide.api.variant.VariantProvider;
import com.evandev.fieldguide.variant.FieldGuideVariantManager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.monster.Slime;

import java.util.ArrayList;
import java.util.List;

public class FieldGuideEntrypoint {

    public static void registerProvider() {
        FieldGuideVariantManager.registerProvider(Slime.class, new SlimeTimeVariantProvider());
    }

    public static class SlimeTimeVariantProvider implements VariantProvider<Slime> {

        @Override
        public List<VariantDef> getVariants(Slime entity) {
            if (entity.is(net.minecraft.world.entity.EntityType.SLIME) && SlimeTime.CONFIG.slimeTime.colourfulSlimes) {
                ArrayList<VariantDef> list = new ArrayList<>();
                for (SlimeColor color : SlimeColor.values()) {
                    list.add(new VariantDef(color.getName(), color));
                }
                return list;
            }
            return List.of();
        }

        @Override
        public void apply(Slime entity, VariantDef def) {
            if (entity instanceof VariatedSlimeAccess variatedSlimeAccess) {
                variatedSlimeAccess.slimeTime$setVariant((SlimeColor) def.value());
            }
        }

        @Override
        public VariantDef getCurrent(Slime entity) {
            if (!(entity instanceof VariatedSlimeAccess holder)) return new VariantDef("default", null);
            SlimeColor current = holder.slimeTime$getVariant();
            if (current == null) return new VariantDef("default", null);
            return new VariantDef(current.toString(), current);
        }

        @Override
        public String getCacheKey(Slime entity) {
            return BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()) + "_slime_time";
        }
    }
}