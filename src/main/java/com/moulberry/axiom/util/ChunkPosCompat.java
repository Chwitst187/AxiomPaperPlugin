package com.moulberry.axiom.util;

import net.minecraft.world.level.ChunkPos;

public final class ChunkPosCompat {

    private ChunkPosCompat() {
    }

    public static long asLong(int chunkX, int chunkZ) {
        return (chunkX & 4294967295L) | ((chunkZ & 4294967295L) << 32);
    }

    public static ChunkPos fromLong(long chunkPosLong) {
        return new ChunkPos(getX(chunkPosLong), getZ(chunkPosLong));
    }

    public static int getX(long chunkPosLong) {
        return ChunkPos.getX(chunkPosLong);
    }

    public static int getZ(long chunkPosLong) {
        return ChunkPos.getZ(chunkPosLong);
    }

}

