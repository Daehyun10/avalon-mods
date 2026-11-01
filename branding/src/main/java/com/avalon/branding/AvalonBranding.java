package com.avalon.branding;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mod(AvalonBranding.MOD_ID)
public final class AvalonBranding {
    public static final String MOD_ID = "avalonbranding";

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Setup {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Window window = Minecraft.getInstance().getWindow();
                window.setTitle("AVALON");
                Client.setIcon(window.getWindow());
            });
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
    public static final class Client {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                // Minecraft rewrites its title on world and connection changes.
                Minecraft.getInstance().getWindow().setTitle("AVALON");
            }
        }

        private static void setIcon(long window) {
            try (InputStream input = AvalonBranding.class.getResourceAsStream("/assets/avalonbranding/window-icon.jpg")) {
                if (input == null) throw new IOException("AVALON icon resource is missing");
                BufferedImage source = ImageIO.read(input);
                if (source == null) throw new IOException("AVALON icon could not be decoded");
                int[] sizes = {32, 128};
                ByteBuffer[] pixels = new ByteBuffer[sizes.length];
                try (GLFWImage.Buffer icons = GLFWImage.malloc(sizes.length)) {
                    try {
                        for (int index = 0; index < sizes.length; index++) {
                            int size = sizes[index];
                            BufferedImage scaled = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
                            Graphics2D graphics = scaled.createGraphics();
                            try {
                                graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                                graphics.drawImage(source, 0, 0, size, size, null);
                            } finally {
                                graphics.dispose();
                            }
                            ByteBuffer rgba = MemoryUtil.memAlloc(size * size * 4);
                            pixels[index] = rgba;
                            for (int y = 0; y < size; y++) {
                                for (int x = 0; x < size; x++) {
                                    int argb = scaled.getRGB(x, y);
                                    rgba.put((byte) (argb >> 16)).put((byte) (argb >> 8)).put((byte) argb).put((byte) (argb >> 24));
                                }
                            }
                            rgba.flip();
                            icons.position(index).width(size).height(size).pixels(rgba);
                        }
                        icons.position(0);
                        GLFW.glfwSetWindowIcon(window, icons);
                    } finally {
                        for (ByteBuffer buffer : pixels) {
                            if (buffer != null) MemoryUtil.memFree(buffer);
                        }
                    }
                }
            } catch (Exception error) {
                LogManager.getLogger(MOD_ID).warn("Unable to apply AVALON window icon", error);
            }
        }
    }
}
