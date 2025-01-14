package dev.isxander.controlify.mixins.compat.sodium;

import org.spongepowered.asm.mixin.*;

/*? if sodium {*//*
import dev.isxander.controlify.compatibility.sodium.SodiumGuiScreenProcessor;
import dev.isxander.controlify.screenop.ScreenProcessor;
import dev.isxander.controlify.screenop.ScreenProcessorProvider;
import me.jellysquid.mods.sodium.client.gui.SodiumOptionsGUI;
import me.jellysquid.mods.sodium.client.gui.options.control.ControlElement;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Pseudo
@Mixin(value = SodiumOptionsGUI.class, remap = false)
public abstract class SodiumOptionsGUIMixin extends Screen implements ScreenProcessorProvider {
    @Shadow @Final private List<ControlElement<?>> controls;

    @Unique private final SodiumGuiScreenProcessor controlify$screenProcessor
            = new SodiumGuiScreenProcessor((SodiumOptionsGUI) (Object) this);

    protected SodiumOptionsGUIMixin(Component title) {
        super(title);
    }

    @Inject(method = "rebuildGUIOptions", at = @At("RETURN"))
    private void focusFirstButton(CallbackInfo ci) {
        this.setInitialFocus(controls.get(0));
    }

    @Override
    public ScreenProcessor<?> screenProcessor() {
        return controlify$screenProcessor;
    }
}
*//*?} else {*/
@Mixin(targets = {})
public class SodiumOptionsGUIMixin {
}
/*?}*/
