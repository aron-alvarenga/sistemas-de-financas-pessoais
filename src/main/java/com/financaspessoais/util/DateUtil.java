package com.financaspessoais.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    // Formato padrão de data para o aplicativo
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Converte LocalDate para String no formato dd/MM/yyyy
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * Converte String no formato dd/MM/yyyy para LocalDate
     */
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Valida se a String está no formato de data correto
     */
    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }

    /**
     * Obtém a data atual formatada
     */
    public static String getTodayFormatted() {
        return format(LocalDate.now());
    }

    /**
     * Converte java.sql.Date para LocalDate
     */
    public static LocalDate toLocalDate(java.sql.Date date) {
        return date != null ? date.toLocalDate() : null;
    }

    /**
     * Converte LocalDate para java.sql.Date
     */
    public static java.sql.Date toSqlDate(LocalDate date) {
        return date != null ? java.sql.Date.valueOf(date) : null;
    }
}