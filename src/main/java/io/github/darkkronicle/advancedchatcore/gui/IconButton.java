package io.github.darkkronicle.advancedchatcore.gui;

import fi.dy.masa.malilib.render.RenderUtils;
import io.github.darkkronicle.advancedchatcore.util.Color;
import io.github.darkkronicle.advancedchatcore.util.Colors;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class IconButton extends CleanButton {

    @Setter
    @Getter
    private int padding;

    @Setter
    @Getter
    private Identifier icon;

    @Setter
    @Getter
    private int iconWidth;

    @Setter
    @Getter
    private int iconHeight;

    @Setter
    @Getter
    private Consumer<IconButton> onClick;

    @Getter
    @Setter
    private String onHover;

    public IconButton(int x, int y, int sideLength, int iconLength, Identifier icon, Consumer<IconButton> mouseClick) {
        this(x, y, sideLength, sideLength, iconLength, iconLength, icon, mouseClick);
    }

    public IconButton(int x, int y, int width, int height, int iconWidth, int iconHeight, Identifier icon, Consumer<IconButton> mouseClick) {
        this(x, y, width, height, 2, iconWidth, iconHeight, icon, mouseClick, null);
    }

    public IconButton(int x, int y, int width, int height, int padding, int iconWidth, int iconHeight, Identifier icon, Consumer<IconButton> mouseClick, String onHover) {
        super(x, y, width, height, null, null);
        this.padding = padding;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.icon = icon;
        this.onClick = mouseClick;
        this.onHover = onHover;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, boolean unused) {
        int relMX = mouseX - x;
        int relMY = mouseY - y;
        hovered = relMX >= 0 && relMX <= width && relMY >= 0 && relMY <= height;

        Color plusBack = Colors.getInstance().getColorOrWhite("background").withAlpha(100);
        if (hovered) {
            plusBack = Colors.getInstance().getColorOrWhite("hover").withAlpha(plusBack.alpha());
        }

        context.fill(x, y, x + width, y + height, plusBack.color());
        context.drawTexture(RenderPipelines.GUI_TEXTURED, icon, x, y, 0, 0, width, height, width, height);

        if (hovered && onHover != null) {
            RenderUtils.drawCenteredString(context, mouseX + 4,
                    mouseY - 16,
                    Colors.getInstance().getColorOrWhite("white").color(),
                    onHover);
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        this.mc.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        onClick.accept(this);
        return true;
    }

}
