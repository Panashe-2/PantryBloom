package za.co.pantrybloom.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Pure Java logic so it can be exercised separately from Android UI or Room. */
public final class StrictRecipeMatcher {
    private StrictRecipeMatcher() {}

    public static final class Amount {
        public final String name;
        public final double value;
        public final String unit;
        public Amount(String name, double value, String unit) {
            this.name=name; this.value=value; this.unit=unit;
        }
    }

    public static String key(String raw) {
        if (raw == null) return "";
        String x=raw.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+", " ");
        switch (x) {
            case "eggs": return "egg";
            case "apples": return "apple";
            case "bananas": return "banana";
            case "pears": return "pear";
            case "oranges": return "orange";
            case "grapes": return "grape";
            case "strawberries": return "strawberry";
            case "blueberries": return "blueberry";
            case "potatoes": return "potato";
            case "tomatoes": return "tomato";
            case "carrots": return "carrot";
            case "crackers": return "cracker";
            case "chick peas": return "chickpeas";
            case "yoghurt": return "yogurt";
            default: return x;
        }
    }

    private static String dimension(String unit) {
        if (unit == null) return "";
        String u=unit.trim().toLowerCase(Locale.ROOT);
        if (u.equals("g") || u.equals("kg")) return "mass";
        if (u.equals("ml") || u.equals("l")) return "volume";
        if (u.equals("pcs")) return "count";
        return "";
    }

    private static double baseValue(double value, String unit) {
        String u=unit.trim().toLowerCase(Locale.ROOT);
        return (u.equals("kg") || u.equals("l")) ? value*1000.0 : value;
    }

    private static String stockKey(String name, String unit) {
        return key(name) + "|" + dimension(unit);
    }

    public static boolean canMake(List<Amount> pantry, List<Amount> needs) {
        if (pantry == null || needs == null || needs.isEmpty()) return false;
        Map<String, Double> stock=new HashMap<>();
        for (Amount item: pantry) {
            if (item == null || key(item.name).isEmpty() || dimension(item.unit).isEmpty()
                    || !Double.isFinite(item.value) || item.value <= 0) continue;
            stock.merge(stockKey(item.name, item.unit), baseValue(item.value, item.unit), Double::sum);
        }
        Map<String, Double> demand=new HashMap<>();
        for (Amount need: needs) {
            if (need == null || key(need.name).isEmpty() || dimension(need.unit).isEmpty()
                    || !Double.isFinite(need.value) || need.value <= 0) return false;
            demand.merge(stockKey(need.name, need.unit), baseValue(need.value, need.unit), Double::sum);
        }
        for (Map.Entry<String, Double> requirement: demand.entrySet()) {
            double available=stock.getOrDefault(requirement.getKey(), 0.0);
            if (available + 0.000001 < requirement.getValue()) return false;
        }
        return true;
    }
}
