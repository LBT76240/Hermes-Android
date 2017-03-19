package trailin.myfirstapplication;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Color;

import java.util.List;

import static trailin.myfirstapplication.R.drawable.plan;

/**
 * Created by trail on 2017-03-19.
 */

public class PeopleAdapter extends ArrayAdapter<People> {

    public PeopleAdapter(Context context, List<People> peoples) {
        super(context, 0, peoples);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_people,parent, false);
        }

        PeopleViewHolder viewHolder = (PeopleViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new PeopleViewHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.nom);
            viewHolder.job = (TextView) convertView.findViewById(R.id.job);
            viewHolder.salle = (TextView) convertView.findViewById(R.id.salle);
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        People people = getItem(position);
        viewHolder.nom.setText(people.getNom());
        viewHolder.job.setText(people.getJob());
        viewHolder.salle.setText(people.getSalle());

        //TODO Mettre l'avatar

        viewHolder.avatar.setImageResource(people.getAvatar());

        return convertView;
    }

    private class PeopleViewHolder{
        public TextView nom;
        public TextView job;
        public TextView salle;
        public ImageView avatar;
    }
}