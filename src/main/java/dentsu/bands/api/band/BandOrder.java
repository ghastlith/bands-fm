package dentsu.bands.api.band;

public enum BandOrder {

    UNORDERED("unordered"),
    NAME_ASCENDING("+name"),
    NAME_DESCENDING("-name"),
    POPULARITY_ASCENDING("+numPlays"),
    POPULARITY_DESCENDING("-numPlays");

    public final String value;

    private BandOrder(final String value) {
        this.value = value;
    }

    public static BandOrder fromValue(final String value) {
        for (BandOrder bandOrder : values()) {
            if (bandOrder.value.equals(value)) {
                return bandOrder;
            }
        }

        return null;
    }

}
