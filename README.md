# PantryBloom

**Module:** Mobile App Development 700  
**Built with:** Java, XML, Android Studio and Room (SQLite)

## The idea
PantryBloom keeps a list of ingredients at home and suggests meals using only what is already available. A recipe is displayed on the **Cook** page only if the pantry has every required ingredient in a sufficient quantity.

## Main screens
- **Pantry:** View ingredients and add, update or delete an item.
- **Cook:** View matching recipes and open their instructions.
- **Settings:** Turn the near-expiry highlight on or off.
- **Ingredient editor:** Enter a name, quantity, unit and optional date, with input checks.
- **Recipe detail:** See the ingredients and steps for one meal.

## How it is organised
- `MainActivity` switches between Pantry, Cook and Settings Fragments.
- `IngredientEditorActivity` and `RecipeDetailActivity` are opened using Intents.
- Room saves pantry records, recipe records and recipe requirements in a local SQLite database.
- `PantryDao` contains database operations; `PantryRepository` connects the data to the screens.
- The ingredient and recipe lists use RecyclerView adapters.
- `StrictRecipeMatcher` checks names, quantities and compatible units before suggesting a recipe.
- The database starts with 20 recipes.

## Open and run
1. Extract the project ZIP.
2. Open the **PantryBloom** project folder in Android Studio (not only the `app` folder).
3. Let Gradle sync, using JDK 17 and Android SDK 35. The project configuration uses Gradle 8.7 and Android Gradle Plugin 8.6.1.
4. Select an Android emulator, for example Medium Phone API 35, and run `app`.

**Setup note:** The starter currently contains the Gradle wrapper properties but not the launcher scripts or wrapper JAR. If Gradle cannot start, configure Gradle 8.7 or generate the missing wrapper files before running the project.

## Example matching test
1. Add `apple`, `1`, `pcs`.
2. Add `honey`, `10`, `g`. **Honey Apple Slices** should not appear in Cook.
3. Edit honey to `15 g`. The recipe should now appear.
4. Try `0.015 kg` instead of 15 g; this should still match.
5. Delete the apple and check that the suggestion disappears.
6. Close and reopen the app to check that the remaining ingredients are still stored.

Also test empty names, zero amounts, invalid dates, the recipe-detail screen and the Settings preference.

## GitHub and submission
Use a separate GitHub repository for this project and commit real development changes as they happen. The assignment requires at least ten meaningful commits, a narrated **5–7 minute** video and a report containing real screenshots and the public GitHub link. Put the project, report and MP4 together in one ZIP smaller than 50 MB.

## Current project status
This is a **starting point for individual development**, not a completed or emulator-tested submission. The included Java matching checks were run separately, but the complete Android build, Room code generation and emulator behaviour must still be checked in Android Studio.
