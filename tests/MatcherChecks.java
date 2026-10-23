import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import za.co.pantrybloom.logic.StrictRecipeMatcher;
import za.co.pantrybloom.logic.StrictRecipeMatcher.Amount;

public class MatcherChecks {
    private static void verify(boolean condition, String name) {
        if (!condition) throw new AssertionError(name);
        System.out.println("PASS " + name);
    }
    public static void main(String[] args) {
        List<Amount> pantry=Arrays.asList(new Amount("eggs",2,"pcs"),new Amount("milk",0.2,"L"),
                new Amount("butter",5,"g"),new Amount("butter",5,"g"));
        verify(StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("egg",2,"pcs"))),"plural egg");
        verify(StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("milk",200,"ml"))),"litre to ml");
        verify(StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("butter",10,"g"))),"combined stock");
        verify(!StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("egg",2,"pcs"),new Amount("flour",3,"g"))),"no partial matches");
        verify(!StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("milk",201,"ml"))),"too little");
        verify(!StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("milk",1,"g"))),"do not mix dimensions");
        verify(!StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("milk",0,"ml"))),"reject zero requirement");
        verify(!StrictRecipeMatcher.canMake(pantry, Collections.emptyList()),"reject empty recipes");
        verify(!StrictRecipeMatcher.canMake(pantry, Arrays.asList(new Amount("milk",Double.NaN,"ml"))),"reject NaN requirement");
        verify(StrictRecipeMatcher.key("strawberries").equals("strawberry"),"berry plurals");
    }
}
