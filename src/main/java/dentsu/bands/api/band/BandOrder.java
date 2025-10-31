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

}
