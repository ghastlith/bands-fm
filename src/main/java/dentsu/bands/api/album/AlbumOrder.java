package dentsu.bands.api.album;

public enum AlbumOrder {

    UNORDERED("unordered"),
    NAME_ASCENDING("+name"),
    NAME_DESCENDING("-name"),
    RELEASE_DATE_ASCENDING("+releasedDate"),
    RELEASE_DATE_DESCENDING("-releasedDate");

    public final String value;

    private AlbumOrder(final String value) {
        this.value = value;
    }

    public static AlbumOrder fromValue(final String value) {
        for (AlbumOrder albumOrder : values()) {
            if (albumOrder.value.equals(value)) {
                return albumOrder;
            }
        }

        return null;
    }

}
