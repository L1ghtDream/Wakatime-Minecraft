package dev.lightdream.wakatime_mc.mixin;

import dev.lightdream.wakatime_mc.WakaTime;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseMixin {
    @Inject(method = "onCursorPos", at = @At("HEAD"))
    public void onKey(long window, double x, double y, CallbackInfo ci) {
        WakaTime.instance.setKeyPressed(true);
    }

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    public void onKey(long window, int button, int action, int mods, CallbackInfo ci) {
        WakaTime.instance.setKeyPressed(true);
    }
}
