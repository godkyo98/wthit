package mcp.mobius.waila.util;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record ModInfo(
    String id,
    String name
) {

    public static Function<String, Optional<ModInfo>> supplier;

    private static final Map<String, ModInfo> CONTAINER_CACHE = Maps.newHashMap();

    static {
        register(new ModInfo("minecraft", "Minecraft"));
    }

    public static void register(ModInfo info) {
        CONTAINER_CACHE.put(info.id(), info);
    }

    public static ModInfo get(String namespace) {
        return CONTAINER_CACHE.computeIfAbsent(namespace, s -> supplier.apply(namespace).orElse(new ModInfo(s, s)));
    }

    public static ModInfo get(ResourceLocation id) {
        return get(id.getNamespace());
    }

    public static ModInfo get(Block block) {
        return get(Registry.BLOCK.getKey(block));
    }

    public static ModInfo get(Item item) {
        return get(Registry.ITEM.getKey(item));
    }

    public static ModInfo get(Entity entity) {
        ResourceLocation id = Registry.ENTITY_TYPE.getKey(entity.getType());
        return get(id);
    }

}
