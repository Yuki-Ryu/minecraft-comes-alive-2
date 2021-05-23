package com.minecraftcomesalive.mca.util.math;

import net.minecraft.util.math.BlockPos;

public class BlockPosMCA extends BlockPos {
    private BlockPos mcPos;

    public static final BlockPosMCA ORIGIN = new BlockPosMCA(0,0,0);

    public BlockPosMCA(int x, int y, int z) {
        super(x,y,z);
        mcPos = new BlockPos(x, y, z);
    }

    public BlockPosMCA(double x, double y, double z) {
        super(x,y,z);
        mcPos = new BlockPos(x, y, z);
    }

    private BlockPosMCA(BlockPos pos) {
        super(pos);
        this.mcPos = pos;
    }

    public static BlockPosMCA fromMC(BlockPos pos) {
        return new BlockPosMCA(pos);
    }

    public int getX() {
        return mcPos.getX();
    }

    public int getY() {
        return mcPos.getY();
    }

    public int getZ() {
        return mcPos.getZ();
    }

    public BlockPosMCA add(int x, int y, int z) {
        mcPos.offset(x, y, z);
        return this;
    }

    public BlockPosMCA down() {
        mcPos = mcPos.below();
        return this;
    }

    public double getDistance(int x, int y, int z) {
        return Math.sqrt(mcPos.distSqr(x, y, z, false));
    }

    public BlockPos getMcPos() {
        return this.mcPos;
    }
}
