package cc.cassian.slime.client.screen;

import cc.cassian.slime.Platform;
import cc.cassian.slime.SlimeTime;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.Nullable;

// Credits to Twilight Forest and Supplementaries, used as inspiration for this class
public class WelcomeMessageScreen extends Screen {
	private static final Component TEXT = Component.translatable("gui.slime_time.modefite.message");
	private static final String MODEFITE_URL_FABRIC = "https://modrinth.com/mod/modefite-item-definition-backport/version/XG0Uwv8m";
	private static final String MODEFITE_URL_NEOFORGE = "https://modrinth.com/mod/modefite-item-definition-backport/version/cy8BkRZc";
	private static final Component URL = Component.translatable("gui.slime_time.modefite.suggestions")
			.withStyle(Style.EMPTY.withColor(ChatFormatting.GOLD).applyFormat(ChatFormatting.UNDERLINE)
					.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, Platform.loader().equals("fabric") ? MODEFITE_URL_FABRIC : MODEFITE_URL_NEOFORGE)));
	private static final Component TITLE = Component.translatable("gui.slime_time.modefite.title")
			.withStyle(ChatFormatting.RED).withStyle(ChatFormatting.BOLD);

	private final Screen parent;
	private final Component text;
	private final Component url;
	private final Runnable onTurnOff;
	private int ticksUntilEnable;
	private MultiLineLabel message;
	private MultiLineLabel downloadLink;
	private Button exitButton;
	private Button disableButton;
	private static boolean warned = SlimeTime.CONFIG.client.warnedAboutModefite;

	public WelcomeMessageScreen(Screen screen, int ticksUntilEnable,
	                            Component title, Component text, Component url,
	                            Runnable onTurnOff) {
		super(title);
		this.message = MultiLineLabel.EMPTY;
		this.downloadLink = MultiLineLabel.EMPTY;
		this.parent = screen;
		this.ticksUntilEnable = ticksUntilEnable;
		this.text = text;
		this.url = url;
		this.onTurnOff = onTurnOff;
	}

	public static void openWarningScreen(Screen parent) {
		if (parent instanceof TitleScreen && !warned
				&& !Platform.isModLoaded("modefite")
		) {
			Minecraft.getInstance().setScreen(new WelcomeMessageScreen(parent, 60, TITLE, TEXT,
					URL, ()-> SlimeTime.CONFIG.client.warnedAboutModefite = true));
			warned = true;
		}
	}

	@Override
	public Component getNarrationMessage() {
		return CommonComponents.joinForNarration(super.getNarrationMessage(), text);
	}

	@Override
	protected void init() {
		super.init();
		this.exitButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_PROCEED, (pressed) -> {
			Minecraft.getInstance().setScreen(this.parent);
		}).bounds(this.width / 2 + 5, this.height * 5 / 6, 150, 20).build());
		this.exitButton.active = false;

		this.disableButton = this.addRenderableWidget(Button.builder(
				Component.translatable("gui.slime_time.modefite.turn_off"), (pressed) -> {
					Minecraft.getInstance().setScreen(this.parent);
					onTurnOff.run();
				}).bounds(this.width / 2 - 155, this.height * 5 / 6, 150, 20).build());
		this.disableButton.active = false;

		this.message = MultiLineLabel.create(this.font, text, this.width - 50);
		this.downloadLink = MultiLineLabel.create(this.font, url, this.width - 50);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		super.render(graphics, mouseX, mouseY, partialTicks);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 30, 16777215);
		this.message.renderCentered(graphics, this.width / 2, 55);
		this.downloadLink.renderCentered(graphics, this.width / 2, 120);
	}

	@Override
	public void tick() {
		super.tick();
		if (--this.ticksUntilEnable <= 0) {
			this.exitButton.active = true;
			this.disableButton.active = true;
		}
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return this.ticksUntilEnable <= 0;
	}

	@Override
	public void onClose() {
		Minecraft.getInstance().setScreen(this.parent);
	}

	@Override
	public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
		if (pMouseY > 120.0 && pMouseY < 130.0 && this.url != null) {
			Style style = this.getClickedComponentStyleAt((int) pMouseX);
			if (style != null && style.getClickEvent() != null && style.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
				this.handleComponentClicked(style);
				return false;
			}
		}
		return super.mouseClicked(pMouseX, pMouseY, pButton);
	}

	private @Nullable Style getClickedComponentStyleAt(int xPos) {
		int wid = Minecraft.getInstance().font.width(url);
		int left = this.width / 2 - wid / 2;
		int right = this.width / 2 + wid / 2;
		return xPos >= left && xPos <= right ? Minecraft.getInstance().font.getSplitter().componentStyleAtWidth(url, xPos - left) : null;
	}

}