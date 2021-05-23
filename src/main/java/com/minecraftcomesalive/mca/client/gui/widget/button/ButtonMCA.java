package com.minecraftcomesalive.mca.client.gui.widget.button;

import com.minecraftcomesalive.mca.MCAMod;
import com.minecraftcomesalive.mca.api.IInteraction;

/**
 * APIButton is a button defined in assets/mca/api/gui/*
 * <p>
 * These buttons are dynamically attached to a GuiScreen and include additional instruction/constraints for building
 * and processing interactions.
 */
public class ButtonMCA {
    private int id;             // numeric id
    private String identifier;  // string identifier for the button in the .lang file
    private int x;              // x position
    private int y;              // y position
    private int width;          // button width
    private int height;         // button height
    private String interactionClass;    // fully qualified path of the class used to handle this interaction, should implelment IInteraction.

    public ButtonMCA(int id, String identifier, int x, int y, int width, int height, String interactionClass) {
        this.id = id;
        this.identifier = identifier;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.interactionClass = interactionClass;
    }

    public IInteraction getInteraction() {
        try {
            return (IInteraction) Class.forName(interactionClass).newInstance();
        } catch (Exception e) {
            MCAMod.logAndThrow("Unable to find class for interaction.", e);
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
