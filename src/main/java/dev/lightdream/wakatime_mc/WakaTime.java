package dev.lightdream.wakatime_mc;

import dev.lightdream.lambda.ScheduleUtils;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WakaTime implements ClientModInitializer {

    public static WakaTime instance;
    private boolean keyPressed = false;

    public void setKeyPressed(boolean keyPressed) {
        this.keyPressed = keyPressed;
    }

    private void sendHeartbeat() throws IOException {
        MinecraftClient client = MinecraftClient.getInstance();
        ServerInfo currentServer = client.getCurrentServerEntry();
        String address = "localhost";

        if (currentServer != null) {
            address = currentServer.address;
        }

        if (!keyPressed) {
            return;
        }

        String home = System.getProperty("user.home");

        String base = "/.wakatime/wakatime-cli";

        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            base += ".exe";
        }


        List<String> command = new ArrayList<>(List.of(
                home + base,
                "--entity-type", "domain",
                "--entity", address,
                "--project", address,
                "--language", "Minecraft",
                "--plugin", "\"Minecraft Client\"",
                "--write"
        ));

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();
        processBuilder.start();

        setKeyPressed(false);
    }

    @Override
    public void onInitializeClient() {
        instance = this;

        ScheduleUtils.runTaskTimerAsync(() -> {
            try {
                sendHeartbeat();
            } catch (IOException e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }
        }, 30 * 1000);
    }
}
