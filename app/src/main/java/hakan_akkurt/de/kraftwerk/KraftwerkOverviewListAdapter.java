package hakan_akkurt.de.kraftwerk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hakan Akkurt on 06.04.2019.
 */

public class KraftwerkOverviewListAdapter extends ArrayAdapter<Kraftwerk> {
    public KraftwerkOverviewListAdapter(final Context context, final List<Kraftwerk> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Kraftwerk currentKraftwerk = getItem(position);

        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.kraftwerk_overwiev_list_item, parent, false);

            ((TextView) view.findViewById(R.id.view_typDerErzeugungsanlage)).setText(currentKraftwerk.getTypDerErzeugungsanlage());

            TextView leistungInKw = view.findViewById(R.id.view_leistungInKw);
            TextView anschaffungsdatum = view.findViewById(R.id.view_anschaffungsdatum);


            if (currentKraftwerk.getLeistungInKw() == null) {
                leistungInKw.setVisibility(View.GONE);
            } else {
                leistungInKw.setVisibility(View.VISIBLE);
                leistungInKw.setText(currentKraftwerk.getLeistungInKw() + " KW");
            }


            if (currentKraftwerk.getAnschaffungsdatum() == null) {
                anschaffungsdatum.setVisibility(View.GONE);
            } else {
                anschaffungsdatum.setVisibility(View.VISIBLE);
                anschaffungsdatum.setText(getDateInString(currentKraftwerk.getAnschaffungsdatum()));
            }
        }
            return view;

    }
        private String getDateInString(Calendar calendar){
            return String.format(Locale.GERMANY, "%02d.%d", calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.YEAR));
        }

    }
