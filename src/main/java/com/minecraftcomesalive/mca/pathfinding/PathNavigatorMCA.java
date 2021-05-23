package com.minecraftcomesalive.mca.pathfinding;

import com.minecraftcomesalive.mca.util.math.BlockPosMCA;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.stream.Stream;

public class PathNavigatorMCA {
    private final PathNavigator vanillaNavigator;

    public PathNavigatorMCA(PathNavigator navigator) {
        this.vanillaNavigator = navigator;
    }

    public static PathNavigatorMCA fromMC(PathNavigator navigator) {
        return new PathNavigatorMCA(navigator);
    }

    public boolean tryGoTo(BlockPosMCA pos) {
        return vanillaNavigator.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.5F); //TODO verify
    }

    public PathNavigator getVanillaNavigator(){
        return this.vanillaNavigator;
    }
}
