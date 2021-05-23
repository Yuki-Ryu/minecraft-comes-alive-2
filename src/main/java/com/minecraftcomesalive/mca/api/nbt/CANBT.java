package com.minecraftcomesalive.mca.api.nbt;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.util.math.BlockPosMCA;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

import java.util.Set;
import java.util.UUID;

public class CANBT {
    private final CompoundNBT mcCompound;

    private CANBT() {
        mcCompound = new CompoundNBT();
    }

    private CANBT(CompoundNBT nbt) {
        this.mcCompound = nbt;
    }

    public static CANBT createNew() {
        return new CANBT();
    }

    public static CANBT fromMC(CompoundNBT nbt) {
        return new CANBT(nbt);
    }

    public static class ConstantsMCA {
        public static final UUID ZERO_UUID = new UUID(0, 0);
        private ConstantsMCA(){}
    }

    public CANBT copy() {
        return new CANBT(mcCompound.copy());
    }



    public CANBT setBoolean(String key, boolean value) {
        mcCompound.putBoolean(key, value);
        return this;
    }

    public void setString(String key, String value) {
        mcCompound.putString(key, value);
    }

    public void setInteger(String key, int value) {
        mcCompound.putInt(key, value);
    }

    public CANBT setTag(String key, CANBT value) { mcCompound.put(key, value.mcCompound); return this; }

    public void setList(String key, ListNBT list) {
        mcCompound.put(key, list);
    }

    public void setUUID(String key, UUID value) {
        mcCompound.putUUID(key, value);
    }

    public void setBPosM(String key, BlockPosMCA pos) {
        this.setInteger(key + "X", pos.getX());
        this.setInteger(key + "Y", pos.getY());
        this.setInteger(key + "Z", pos.getZ());
    }

    public void setFloat(String key, float value) {
        mcCompound.putFloat(key, value);
    }

    public void setByte(String key, byte value) {
        mcCompound.putByte(key, value);
    }

    public void setDouble(String key, double value) {
        mcCompound.putDouble(key, value);
    }

    public void set(String key, Object value) {
        Class<?> clazz = value.getClass();
        if (value instanceof Float) {
            setFloat(key, (Float)value);
        } else if (value instanceof Byte) {
            setByte(key, (Byte)value);
        } else if (value instanceof Double) {
            setDouble(key, (Double)value);
        } else if (value instanceof String) {
            setString(key, (String)value);
        } else if (value instanceof Integer) {
            setInteger(key, (Integer)value);
        } else if (value instanceof UUID) {
            setUUID(key, (UUID)value);
        } else if (value instanceof Boolean) {
            setBoolean(key, (Boolean)value);
        } else if (value instanceof CANBT) {
            setTag(key, (CANBT)value);
        } else {
            MCAMod.getLog().throwing(new Exception("Attempt to set CANBT data of unknown class!: " + clazz.getName()));
        }
    }




    public Set<String> getKeySet() {
        return mcCompound.getAllKeys();
    }

    public CompoundNBT getMcCompound() {
        return this.mcCompound;
    }

    public boolean getBoolean(String key) {
        return mcCompound.getBoolean(key);
    }

    public String getString(String key) {
        return mcCompound.getString(key);
    }

    public BlockPosMCA getBPosM(String key) {
        int x,y,z;

        x = mcCompound.getInt(key + "X");
        y = mcCompound.getInt(key + "Y");
        z = mcCompound.getInt(key + "Z");

        return new BlockPosMCA(x, y, z);
    }

    public CANBT getCompoundTag(String key) {
        return CANBT.fromMC(mcCompound.getCompound(key));
    }

    public int getInteger(String key) { return mcCompound.getInt(key); }

    public ListNBT getList(String key) {
        return mcCompound.getList(key, 9);
    }

    public UUID getUUID(String key) {
        return mcCompound.getUUID(key);
    }

    public float getFloat(String key) {
        return mcCompound.getFloat(key);
    }


}
