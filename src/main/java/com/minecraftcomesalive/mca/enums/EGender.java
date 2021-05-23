package com.minecraftcomesalive.mca.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public enum EGender
{
	UNASSIGNED(-1, "unassigned"),
	MALE(0, "male"),
	FEMALE(1, "female");
	
	int id;
	String name;
	
	EGender(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public static EGender byId(int idIn) {
		Optional<EGender> state = Arrays.stream(values()).filter((e) -> e.id == idIn).findFirst();
		return state.orElse(UNASSIGNED);
	}

	public static EGender getRandom(Random rand) {
		return rand.nextBoolean() ? MALE : FEMALE;
	}

	public static EGender byName(String name) {
		Optional<EGender> state = Arrays.stream(values()).filter((e) -> e.name.equals(name)).findFirst();
		return state.orElse(UNASSIGNED);
	}
}
