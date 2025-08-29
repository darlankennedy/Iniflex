package br.com.empresa.desafio.util;

import java.math.BigDecimal;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class FormatUtils {
    private FormatUtils() {}

    public static final Locale PT_BR = new Locale("pt", "BR");
    public static final DateTimeFormatter DATE_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final NumberFormat CURRENCY_BR = NumberFormat.getCurrencyInstance(PT_BR);

    private static final DecimalFormat NUMBER_BR;
    static {
        DecimalFormatSymbols s = new DecimalFormatSymbols(PT_BR);
        s.setDecimalSeparator(',');
        s.setGroupingSeparator('.');
        NUMBER_BR = new DecimalFormat("#,##0.00", s);
    }

    public static String formatCurrency(BigDecimal value) {
        if (value == null) return "R$ 0,00";
        return CURRENCY_BR.format(value);
    }

    public static String formatNumber(BigDecimal value) {
        if (value == null) return "0,00";
        return NUMBER_BR.format(value);
    }

    public static Collator collator() {
        Collator c = Collator.getInstance(PT_BR);
        c.setStrength(Collator.PRIMARY);
        return c;
    }
}
