# PantryBloom — report & video evidence guide

This is an outline, **not a completed report**. Use only real app results and the student's own reflection.

## Required report structure

1. **Prescribed cover page**: Student name, ITS number, module, date, app name and signed declaration.
2. **Table of contents**.
3. **Introduction**: food-waste issue, intended audience, purpose of strict suggestions.
4. **System design**: sketch navigation `MainActivity` → 3 Fragments; Pantry → IngredientEditorActivity (Intent); Cook → RecipeDetailActivity (Intent). Draw an ER diagram with `pantry`, `recipes`, `recipe_requirements` and `recipe_requirements.recipeId` related to `recipes.id` logically.
5. **Real screenshots**: empty pantry; populated pantry; add/edit/delete; form errors; empty and matching suggestions; recipe detail; preferences ON/OFF; near-expiry indicator; restart/persistence results. Captions identify the actual result.
6. **3–5 short code examples**: `Room` entity+DAO; `PantryRepository`; `StrictRecipeMatcher.canMake`; `RecyclerView.Adapter`; `Intent` screen navigation. Explain in the student's own words.
7. **Real challenges and solutions**: Describe 2–3 actual issues encountered and what fixed them, referencing any matching Git commits. Do not fabricate.
8. **Conclusion/reflection**: What the student learned; what she would improve and why.
9. **Harvard-style references**: Credit Android and Room documentation or tutorials actually used. Do not invent author names or publication dates.
10. **Working GitHub URL**: Add its public HTTPS repository URL in the report.

Use Times New Roman 12 pt, 1.5 spacing where specified by the assignment handout. Submit the report as DOCX or PDF **inside the ZIP**.

## 5–7 minute narrated video flow (student's own voice)

- **~1 min:** Open student's GitHub repository; show and explain authentic incremental commits.
- **~2–3 min:** Run app, demonstrate pantry CRUD and input validation, delete/edit an ingredient and show strict recipe appear/disappear, close/reopen to verify persistent data, show Settings and recipe details.
- **~2 min:** With code visible, explain three concepts (Fragments/lifecycle, Room+DAO data flow, RecyclerView adapters, explicit Intents, or strict-matching algorithm).
- **~30–60 sec:** Explain why Room/SQLite fits this offline app.

Keep MP4 in the final ZIP (not a cloud link). Recording must contain a clear, continuous spoken explanation.

## Final ZIP naming

`Studentnumber_Surname_MobileAppDev700_Assignment.zip`

- `PantryBloom/` (whole project source, excluding `.gradle/` and `build/`)
- `Studentnumber_Surname_MobileAppDev700_Assignment.docx` (or PDF)
- `PantryBloom_Demo.mp4`

Check that final ZIP is **under 50 MB**, opens correctly, and the GitHub URL in the report works.
