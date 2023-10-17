package com.soclosetoheaven.client.locale;


import org.apache.commons.lang3.LocaleUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.*;

public class Localizer {


    private static final String PATH = "locales.language";

    private static Localizer instance;

    private ResourceBundle bundle;

    private Locale previousLocale;

    private Locale currentLocale;

    private Localizer(Locale locale) {
        setCurrentLocale(locale);
        this.previousLocale = null;
    }


    public void setCurrentLocale(Locale newLocale) {
        if (Arrays.stream(Locales.values()).map(e -> e.locale).toList().contains(newLocale)) {
            ResourceBundle newBundle = ResourceBundle.getBundle(PATH, newLocale);
            Locale.setDefault(newLocale);
            this.previousLocale = currentLocale;
            this.currentLocale = newLocale;
            this.bundle = newBundle;
        }
    }

    public void setCurrentLocale(String locale) {
        setCurrentLocale(LocaleUtils.toLocale(locale));
    }


    public String formatDate(Date date) {
        return getByLocale(currentLocale).format.format(date);
    }

    public String formatDate(Object date) {
        if (previousLocale == null)
            return date.toString();
        try {
            return formatDate(getByLocale(previousLocale).format.parse(date.toString()));
        } catch (ParseException e) {
            return null;
        }
    }


    public String getStringByKey(String key) {
        if (key == null)
            return null;
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public static Localizer getInstance() {
        if (instance == null)
            instance = new Localizer(Locales.RU.locale);
        return instance;
    }

    public enum Locales {

        RU(new Locale("ru")),
        MK(new Locale("mk")),
        DA(new Locale("da")),
        ES_EC(new Locale("es", "EC"));
        Locales(Locale locale) {
            this.locale = locale;
            this.format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, locale);
        }

        public final Locale locale;

        public final DateFormat format;
    }

    public static Locales getByLocale(Locale locale) {
        return Arrays.stream(Locales.values()).filter(i -> i.locale.equals(locale)).findFirst().orElse(null);
    }
}
