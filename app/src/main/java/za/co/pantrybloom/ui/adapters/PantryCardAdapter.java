package za.co.pantrybloom.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import za.co.pantrybloom.R;
import za.co.pantrybloom.data.PantryEntry;

public class PantryCardAdapter extends RecyclerView.Adapter<PantryCardAdapter.Card> {
    private final List<PantryEntry> entries=new ArrayList<>();
    private final Consumer<PantryEntry> onClick;
    private boolean warningsEnabled=true;

    public PantryCardAdapter(Consumer<PantryEntry> onClick) { this.onClick=onClick; }

    public void submit(List<PantryEntry> updated,boolean showWarnings) {
        entries.clear(); entries.addAll(updated);
        warningsEnabled=showWarnings;
        notifyDataSetChanged();
    }

    @NonNull @Override public Card onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        return new Card(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pantry,parent,false));
    }

    @Override public void onBindViewHolder(@NonNull Card holder,int pos) {
        PantryEntry item=entries.get(pos);
        holder.title.setText(item.name);
        holder.amount.setText(quantity(item.amount) + " " + item.unit
                + (item.expiry==null || item.expiry.isEmpty()?"":"  •  Expires " + item.expiry));
        String note=warningsEnabled ? expiryNote(item.expiry) : "";
        holder.expiry.setText(note);
        holder.expiry.setVisibility(note.isEmpty()?View.GONE:View.VISIBLE);
        holder.itemView.setOnClickListener(v -> onClick.accept(item));
    }

    @Override public int getItemCount() { return entries.size(); }

    private String expiryNote(String iso) {
        if (iso == null || iso.isEmpty()) return "";
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd",Locale.ROOT);
        formatter.setLenient(false);
        try {
            Calendar now=Calendar.getInstance();
            now.set(Calendar.HOUR_OF_DAY,12);now.set(Calendar.MINUTE,0);
            now.set(Calendar.SECOND,0);now.set(Calendar.MILLISECOND,0);
            Calendar end=Calendar.getInstance();
            end.setTime(formatter.parse(iso));
            end.set(Calendar.HOUR_OF_DAY,12);end.set(Calendar.MINUTE,0);
            end.set(Calendar.SECOND,0);end.set(Calendar.MILLISECOND,0);
            long delta=TimeUnit.MILLISECONDS.toDays(end.getTimeInMillis()-now.getTimeInMillis());
            if (delta < 0) return "Past expiry date";
            if (delta <= 3) return "Use soon: " + iso;
        } catch (ParseException ignored) { return ""; }
        return "";
    }

    private static String quantity(double x) {
        return x==Math.floor(x)?String.format(Locale.ROOT,"%.0f",x)
                :String.format(Locale.ROOT,"%.3f",x).replaceAll("0+$","").replaceAll("\\.$","");
    }

    static class Card extends RecyclerView.ViewHolder {
        final TextView title,amount,expiry;
        Card(View view) {
            super(view);
            title=view.findViewById(R.id.item_name);
            amount=view.findViewById(R.id.item_amount);
            expiry=view.findViewById(R.id.item_expiry);
        }
    }
}
