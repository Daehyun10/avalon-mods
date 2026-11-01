package com.avalon.branding;

import java.io.File;
import java.io.IOException;
import net.minecraft.server.packs.FilePackResources;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;

public final class BrandingPackCheck {
    public static void main(String[] args) throws Exception {
        // Use Minecraft's JAR reader and JSON metadata path without booting Forge registries.
        MetadataSectionSerializer<JsonObject> serializer = new MetadataSectionSerializer<>() {
            public String getMetadataSectionName() { return "pack"; }
            public JsonObject fromJson(JsonObject object) { return object; }
        };
        try (FilePackResources resources = new FilePackResources(new File(args[0]))) {
            JsonObject metadata = resources.getMetadataSection(serializer);
            if (metadata == null || metadata.get("pack_format").getAsInt() != 8 || !metadata.get("description").isJsonPrimitive()) {
                throw new AssertionError("Minecraft could not read the 1.18.2 branding pack metadata");
            }
            System.out.println("PASS: Minecraft FilePackResources reads the branding JAR with pack format 8.");
        }
        if (args.length > 1) {
            boolean reproduced = false;
            try (FilePackResources resources = new FilePackResources(new File(args[1]))) {
                resources.getMetadataSection(serializer);
            } catch (IOException expected) {
                reproduced = expected.getMessage().contains("pack.mcmeta");
            }
            if (!reproduced) throw new AssertionError("The original missing pack.mcmeta failure was not reproduced");
            System.out.println("PASS: The original JAR reproduces the missing pack.mcmeta failure.");
        }
    }
}
