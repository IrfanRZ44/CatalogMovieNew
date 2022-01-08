package id.exomatik.catalogmovie.services.timer;

public enum TimeFormatEnum {
    MILLIS,
    SECONDS,
    MINUTES,
    HOUR,
    DAY;

    public String canonicalForm() {
        return this.name();
    }

    public static id.exomatik.catalogmovie.services.timer.TimeFormatEnum fromCanonicalForm(String canonical) {
        return valueOf(id.exomatik.catalogmovie.services.timer.TimeFormatEnum.class, canonical);
    }
}
