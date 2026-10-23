package za.co.pantrybloom.data;

/** Twenty locally stored recipes. Each instruction only uses its listed food ingredients. */
final class RecipeSeed {
    private RecipeSeed() {}

    static void populate(RecipeDao dao) {
        if (dao.count() > 0) return;
        put(dao, "Honey Apple Slices", "1. Wash and slice the apple.\n2. Drizzle honey over the slices.",
                "apple|1|pcs", "honey|15|g");
        put(dao, "Banana Cocoa Mash", "1. Peel and mash the banana.\n2. Mix in the cocoa until smooth.",
                "banana|1|pcs", "cocoa|5|g");
        put(dao, "Cinnamon Pear Cups", "1. Cut the pears into small pieces.\n2. Toss with cinnamon and serve.",
                "pear|2|pcs", "cinnamon|1|g");
        put(dao, "Strawberry Yogurt Parfait", "1. Slice strawberries.\n2. Layer them with yogurt in a cup.",
                "strawberry|120|g", "yogurt|100|g");
        put(dao, "Berry Oat Bowl", "1. Combine oats and milk in a bowl.\n2. Warm until oats soften.\n3. Top with blueberries.",
                "oats|60|g", "blueberry|60|g", "milk|150|ml");
        put(dao, "Apple Peanut Snack", "1. Slice the apple.\n2. Spread peanut butter on each slice.",
                "apple|1|pcs", "peanut butter|20|g");
        put(dao, "Carrot and Hummus", "1. Wash and peel the carrots.\n2. Cut into sticks and dip in hummus.",
                "carrot|2|pcs", "hummus|50|g");
        put(dao, "Cucumber Cream Cheese Bites", "1. Slice the cucumber into rounds.\n2. Add cream cheese to each round.",
                "cucumber|1|pcs", "cream cheese|40|g");
        put(dao, "Sweetcorn Butter Cup", "1. Warm the sweetcorn.\n2. Stir in butter and serve.",
                "sweetcorn|150|g", "butter|10|g");
        put(dao, "Rice and Beans Bowl", "1. Warm the cooked rice and beans separately.\n2. Combine in a serving bowl.",
                "cooked rice|150|g", "beans|120|g");
        put(dao, "Lemon Chickpea Salad", "1. Drain the chickpeas.\n2. Squeeze lemon juice over them.\n3. Mix with olive oil.",
                "chickpeas|150|g", "lemon|1|pcs", "olive oil|10|ml");
        put(dao, "Tuna Rice Bowl", "1. Warm the cooked rice.\n2. Flake tuna over the rice and serve.",
                "tuna|100|g", "cooked rice|150|g");
        put(dao, "Avocado Rice Bowl", "1. Warm the cooked rice.\n2. Slice the avocado and place over rice.",
                "avocado|1|pcs", "cooked rice|150|g");
        put(dao, "Cheesy Potato Mash", "1. Boil potatoes in water until soft, then drain.\n2. Mash with milk and cheese.",
                "potato|250|g", "cheese|40|g", "milk|30|ml", "water|750|ml");
        put(dao, "Egg and Spinach Scramble", "1. Melt butter in a pan.\n2. Cook spinach until wilted.\n3. Add beaten eggs and stir until cooked.",
                "egg|2|pcs", "spinach|40|g", "butter|10|g");
        put(dao, "Bean and Tomato Bowl", "1. Cut the tomato into pieces.\n2. Combine with warmed beans.",
                "beans|140|g", "tomato|1|pcs");
        put(dao, "Creamy Cheese Pasta", "1. Boil pasta in water until tender, then drain.\n2. Toss with melted butter and grated cheese.",
                "pasta|100|g", "cheese|40|g", "butter|10|g", "water|1000|ml");
        put(dao, "Banana Milk Smoothie", "1. Peel and cut the banana.\n2. Blend it with milk until smooth.",
                "banana|1|pcs", "milk|200|ml");
        put(dao, "Orange Apple Fruit Cup", "1. Peel the orange and cut into sections.\n2. Chop the apple and combine.",
                "orange|1|pcs", "apple|1|pcs");
        put(dao, "Chilled Grape Snack", "1. Rinse the grapes and pat dry.\n2. Chill before serving.",
                "grape|150|g");
    }

    private static void put(RecipeDao dao, String title, String directions, String... ingredients) {
        long id=dao.insertRecipe(new RecipeEntry(title, directions));
        for (String s: ingredients) {
            String[] parts=s.split("\\|");
            dao.insertRequirement(new RecipeRequirement(id, parts[0], Double.parseDouble(parts[1]), parts[2]));
        }
    }
}
