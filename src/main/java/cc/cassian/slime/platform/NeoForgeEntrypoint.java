//? neoforge {
/*package cc.cassian.slime.platform;

import cc.cassian.slime.SlimeTime;
import cc.cassian.slime.api.SlimeColor;
import cc.cassian.slime.registry.SlimeDataComponents;
import cc.cassian.slime.registry.SlimeItems;
import cc.cassian.slime.util.SlimeHelpers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;import net.minecraft.server.packs.repository.Pack;import net.minecraft.server.packs.repository.PackSource;import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.AddPackFindersEvent;import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static cc.cassian.slime.SlimeTime.MOD_ID;
import static cc.cassian.slime.registry.SlimeBlocks.SLIME_BLOCKS;
import static cc.cassian.slime.registry.SlimeBlocks.asListOfStacks;
import static cc.cassian.slime.util.SlimeHelpers.addDyedItems;

@Mod(MOD_ID)
@EventBusSubscriber(modid = MOD_ID)
public class NeoForgeEntrypoint {
	private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MOD_ID);
	public static final Supplier<AttachmentType<SlimeColor>> SLIME_STATE = ATTACHMENT_TYPES.register(
    "with_stream_codec", () -> AttachmentType.builder(() -> (SlimeColor) null)
        .sync(SlimeColor.STREAM_CODEC)
        .build()
	);

	public static BuildCreativeModeTabContentsEvent event;

	public NeoForgeEntrypoint(IEventBus bus) {
		ATTACHMENT_TYPES.register(bus);
	}

	@SubscribeEvent
	public static void modifyTabs(RegisterEvent event) {
		if (event.getRegistryKey().equals(Registries.BLOCK)) {
			SlimeTime.onInitialize();
		}
	}

	@SubscribeEvent
	public static void modifyTabs(AddPackFindersEvent event) {
		event.addPackFinders(SlimeTime.of("resourcepacks/colourful_slimes"), PackType.SERVER_DATA, Component.literal("Colourful Slimes"), PackSource.BUILT_IN, true, Pack.Position.TOP);
	}

	@SubscribeEvent
	public static void modifyTabs(BuildCreativeModeTabContentsEvent event) {
		NeoForgeEntrypoint.event = event;
		ResourceKey<CreativeModeTab> tab = event.getTabKey();
		if (tab.equals(CreativeModeTabs.COMBAT)) {
			insertAfter(Items.TURTLE_HELMET, SlimeItems.SLIME_BOOTS);
			if (SlimeTime.CONFIG.slimeTime.addSlimeBallToCombatTab)
				insertAfter(Items.SNOWBALL, addDyedItems(Items.SLIME_BALL.getDefaultInstance()));
		}
		else if (tab.equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
			insertAfter(Items.TADPOLE_BUCKET, SlimeItems.SLIME_BUCKET);
			insertAfter(SlimeItems.SLIME_BUCKET, SlimeItems.MAGMA_CUBE_BUCKET);
			insertBefore(Items.SADDLE, SlimeHelpers.addDyedItems(SlimeItems.SLIME_SLING.getDefaultInstance()));
		} else if (tab.equals(CreativeModeTabs.COLORED_BLOCKS)) {
			acceptAll(asListOfStacks(SLIME_BLOCKS));
		}
	}

	private static void acceptAll(List<ItemStack> newItems) {
		event.acceptAll(newItems, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	private static void insertAfter(ItemStack anchor, ItemStack newItem) {
		event.insertAfter(anchor, newItem, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	private static void insertAfter(Item anchor, Item newItem) {
		event.insertAfter(anchor.getDefaultInstance(), newItem.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	private static void insertBefore(Item anchor, Item newItem) {
		event.insertAfter(anchor.getDefaultInstance(), newItem.getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

    private static void insertAfter(Item anchor, List<ItemStack> newItems) {
        newItems.reversed().forEach(stack -> {
			event.insertAfter(anchor.getDefaultInstance(), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		});
    }

	private static void insertBefore(Item anchor, List<ItemStack> newItems) {
		newItems.reversed().forEach(stack -> {
			event.insertBefore(anchor.getDefaultInstance(), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		});
	}

	@SubscribeEvent
	public static void sendRecipes(OnDatapackSyncEvent event) {
		event.sendRecipes(RecipeType.CRAFTING);
	}

}
*///?}