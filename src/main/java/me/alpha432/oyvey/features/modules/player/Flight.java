package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.network.ClientPlayerEntity;

public class Flight extends Module {

    private final double speed = 0.5;

    public Flight() {
        super("Flight", "Allows the player to fly.", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) return;

        ClientPlayerEntity player = mc.player;

        // Disable gravity
        player.setNoGravity(true);

        // Stop falling
        player.setVelocity(player.getVelocity().x, 0, player.getVelocity().z);

        // Jump = Up
        if (mc.options.jumpKey.isPressed()) {
            player.setVelocity(player.getVelocity().x, speed, player.getVelocity().z);
        }

        // Sneak = Down
        if (mc.options.sneakKey.isPressed()) {
            player.setVelocity(player.getVelocity().x, -speed, player.getVelocity().z);
        }

        // Forward movement
        if (player.forwardSpeed != 0 || player.sidewaysSpeed != 0) {
            float yaw = (float) Math.toRadians(player.getYaw());

            double forward = player.forwardSpeed;
            double strafe = player.sidewaysSpeed;

            double motionX = (-Math.sin(yaw) * forward + Math.cos(yaw) * strafe) * speed;
            double motionZ = ( Math.cos(yaw) * forward + Math.sin(yaw) * strafe) * speed;

            player.setVelocity(motionX, player.getVelocity().y, motionZ);
        }
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.setNoGravity(false);
        }
    }
}
