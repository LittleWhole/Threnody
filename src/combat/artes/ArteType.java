package combat.artes;

public enum ArteType {
    SUPPORT("Support Arte"), STRIKE("Strike Arte"), ELEMENTAL("Elemental Arte"), MYSTIC("Mystic Arte");

    public final String name;

    ArteType(String name) {
        this.name = name;
    }

}
