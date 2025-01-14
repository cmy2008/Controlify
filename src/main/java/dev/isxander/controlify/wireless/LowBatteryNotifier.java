package dev.isxander.controlify.wireless;

import dev.isxander.controlify.Controlify;
import dev.isxander.controlify.controller.battery.BatteryLevel;
import dev.isxander.controlify.controller.battery.BatteryLevelComponent;
import dev.isxander.controlify.controller.ControllerEntity;
import dev.isxander.controlify.controllermanager.ControllerManager;
import dev.isxander.controlify.utils.ToastUtils;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class LowBatteryNotifier {
    private static final Map<String, BatteryLevel> previousBatteryLevels = new HashMap<>();
    private static int interval;

    public static void tick() {
        if (interval > 0) {
            interval--;
            return;
        }
        interval = 20 * 60; // 1 minute

        if (!Controlify.instance().config().globalSettings().notifyLowBattery)
            return;

        ControllerManager controllerManager = Controlify.instance().getControllerManager().orElse(null);
        if (controllerManager == null)
            return;

        for (ControllerEntity controller : controllerManager.getConnectedControllers()) {
            BatteryLevel batteryLevel = controller.batteryLevel()
                    .map(BatteryLevelComponent::getBatteryLevel)
                    .orElse(BatteryLevel.UNKNOWN);

            if (batteryLevel == BatteryLevel.UNKNOWN) {
                continue;
            }

            String uid = controller.info().uid();
            if (previousBatteryLevels.containsKey(uid)) {
                BatteryLevel previousBatteryLevel = previousBatteryLevels.get(uid);
                if (batteryLevel.ordinal() < previousBatteryLevel.ordinal()) {
                    if (batteryLevel == BatteryLevel.LOW) {
                        ToastUtils.sendToast(
                                Component.translatable("controlify.toast.low_battery.title"),
                                Component.translatable("controlify.toast.low_battery.message", controller.name()),
                                true
                        );
                    }
                }
            }

            previousBatteryLevels.put(uid, batteryLevel);
        }
    }
}
