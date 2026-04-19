package com.moulberry.axiom.util;

public final class ChunkPosCompat {

    private ChunkPosCompat() {
    }

    public static long asLong(int chunkX, int chunkZ) {
        return (chunkX & 4294967295L) | ((chunkZ & 4294967295L) << 32);
    }

}
