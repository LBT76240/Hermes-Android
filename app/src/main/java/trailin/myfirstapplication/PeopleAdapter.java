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
import android.widget.Toast;

import java.util.List;

import static trailin.myfirstapplication.R.drawable.plan;

/**
 * Created by trail on 2017-03-19.
 */

public class PeopleAdapter extends ArrayAdapter<People> {

    private Context context = null;
    private ImageView selectedAvatar = null;
    private TextView selectedNom = null;
    private TextView selectedJob = null;
    private TextView selectedSalle = null;


    public PeopleAdapter(Context context, List<People> peoples,ImageView selectedAvatar,TextView selectedNom, TextView selectedJob,TextView selectedSalle) {


        super(context, 0, peoples);
        this.context=context;
        this.selectedAvatar = selectedAvatar;
        this.selectedNom = selectedNom;
        this.selectedJob =selectedJob;
        this.selectedSalle=selectedSalle;
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



        viewHolder.avatar.setImageResource(people.getAvatar());

        final String name = (String) viewHolder.nom.getText();
        final String job = (String) viewHolder.job.getText();
        final String salle = (String) viewHolder.salle.getText();
        final Drawable avatar = viewHolder.avatar.getDrawable();


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAvatar.setImageDrawable(avatar);
                selectedNom.setText(name);
                selectedJob.setText(job);
                selectedSalle.setText(salle);

            }
        });

        return convertView;
    }

    private class PeopleViewHolder{
        public TextView nom;
        public TextView job;
        public TextView salle;
        public ImageView avatar;
    }


}