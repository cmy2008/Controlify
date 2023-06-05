package dev.isxander.controlify.gui.screen;

import dev.isxander.controlify.Controlify;
import dev.isxander.controlify.config.GlobalSettings;
import dev.isxander.controlify.reacharound.ReachAroundMode;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GlobalSettingsScreenFactory {
    public static Screen createGlobalSettingsScreen(Screen parent) {
        var globalSettings = Controlify.instance().config().globalSettings();
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("controlify.gui.global_settings.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("controlify.gui.global_settings.title"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("controlify.gui.load_vibration_natives"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("controlify.gui.load_vibration_natives.tooltip"))
                                        .text(Component.translatable("controlify.gui.load_vibration_natives.tooltip.warning").withStyle(ChatFormatting.RED))
                                        .build())
                                .binding(true, () -> globalSettings.loadVibrationNatives, v -> globalSettings.loadVibrationNatives = v)
                                .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter())
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .option(Option.<ReachAroundMode>createBuilder()
                                .name(Component.translatable("controlify.gui.reach_around"))
                                .description(state -> OptionDescription.createBuilder()
                                        .webpImage(screenshot("reach-around-placement.webp"))
                                        .text(Component.translatable("controlify.gui.reach_around.tooltip"))
                                        .text(Component.translatable("controlify.gui.reach_around.tooltip.parity").withStyle(ChatFormatting.GRAY))
                                        .text(state == ReachAroundMode.EVERYWHERE ? Component.translatable("controlify.gui.reach_around.tooltip.warning").withStyle(ChatFormatting.RED) : Component.empty())
                                        .build())
                                .binding(GlobalSettings.DEFAULT.reachAround, () -> globalSettings.reachAround, v -> globalSettings.reachAround = v)
                                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(ReachAroundMode.class))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("controlify.gui.ui_sounds"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("controlify.gui.ui_sounds.tooltip"))
                                        .build())
                                .binding(GlobalSettings.DEFAULT.uiSounds, () -> globalSettings.uiSounds, v -> globalSettings.uiSounds = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("controlify.gui.notify_low_battery"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("controlify.gui.notify_low_battery.tooltip"))
                                        .build())
                                .binding(GlobalSettings.DEFAULT.notifyLowBattery, () -> globalSettings.notifyLowBattery, v -> globalSettings.notifyLowBattery = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("controlify.gui.out_of_focus_input"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("controlify.gui.out_of_focus_input.tooltip"))
                                        .build())
                                .binding(GlobalSettings.DEFAULT.outOfFocusInput, () -> globalSettings.outOfFocusInput, v -> globalSettings.outOfFocusInput = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("controlify.gui.keyboard_movement"))
                                .description(OptionDescription.createBuilder()
                                        .text(Component.translatable("controlify.gui.keyboard_movement.tooltip"))
                                        .build())
                                .binding(GlobalSettings.DEFAULT.keyboardMovement, () -> globalSettings.keyboardMovement, v -> globalSettings.keyboardMovement = v)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(ButtonOption.createBuilder()
                                .name(Component.translatable("controlify.gui.open_issue_tracker"))
                                .action((screen, button) -> Util.getPlatform().openUri("https://github.com/isxander/controlify/issues"))
                                .build())
                        .build())
                .build().generateScreen(parent);
    }

    private static ResourceLocation screenshot(String filename) {
        return Controlify.id("textures/screenshots/" + filename);
    }
}