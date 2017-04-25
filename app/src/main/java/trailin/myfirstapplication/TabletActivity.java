package trailin.myfirstapplication;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * Created by trail on 2017-03-14.
 */

public class TabletActivity extends AppCompatActivity {



    ActionThread actionThread= null;
    MainThread mainThread = MainThread.getInstance();
    String menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("", "onCreate");


        actionThread = mainThread.getActionThread();

        menu = mainThread.getMenu();

        if(menu == null) {
            onHome();
        } else {

            if(menu.equals("carte")) {
                onCarte();
            }
            if(menu.equals("personne")) {
                onPersonne();
            }
            if(menu.equals("salle")) {
                onSalle();
            }
            onHome();
        }



    }


    private void onHome() {
        setContentView(R.layout.tabletlayouthome);
        menu = "home";
        mainThread.setMenu(menu);

        Button buttonCarte = (Button) findViewById(R.id.buttonCarte);
        Button buttonPersonne = (Button) findViewById(R.id.buttonPersonne);
        Button buttonSalle = (Button) findViewById(R.id.buttonSalle);

        buttonCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCarte();
            }
        });
        buttonPersonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPersonne();
            }
        });
        buttonSalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSalle();
            }
        });

    }

    private void onCarte() {
        setContentView(R.layout.tabletlayoutcarte);

        menu="carte";
        mainThread.setMenu(menu);

        Button buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHome();
            }
        });




    }

    private void onPersonne() {
        setContentView(R.layout.tabletlayoutpersonne);

        menu="personne";
        mainThread.setMenu(menu);

        Button buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHome();
            }
        });


        final ImageView selectedAvatar = (ImageView) findViewById(R.id.avatar);
        final TextView selectedNom = (TextView) findViewById(R.id.nom);
        final TextView selectedJob = (TextView) findViewById(R.id.job);
        final TextView selectedSalle = (TextView) findViewById(R.id.salle);
        final Button selectedButton = (Button) findViewById(R.id.buttonJoin);

        final ListView listView = (ListView) findViewById(R.id.listView);


        final List<People> peoples = recupererPeople();;

        final PeopleAdapter adapter = new PeopleAdapter(TabletActivity.this, peoples,selectedAvatar,selectedNom,selectedJob,selectedSalle,selectedButton);
        listView.setAdapter(adapter);


        final Button buttonRecherche= (Button) findViewById(R.id.buttonRecherche);
        final EditText editText = (EditText) findViewById(R.id.editText);

        buttonRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                name = name.toLowerCase();

                List<People> peoplesSearch = recupererPeople(name,peoples);

                PeopleAdapter adapter = new PeopleAdapter(TabletActivity.this, peoplesSearch,selectedAvatar,selectedNom,selectedJob,selectedSalle,selectedButton);
                listView.setAdapter(adapter);

            }
        });

        Button buttonReset = (Button) findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText("");
                listView.setAdapter(adapter);

            }
        });

        selectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedNom.getText().equals("")) {

                } else {
                    Toast.makeText(TabletActivity.this, "Go to " + selectedNom.getText(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private List<People> recupererPeople(String name, List<People> peoples) {
        List<People> peoplesSearch = new ArrayList<People>();

        int maxPeople = peoples.size();

        for(int i = 0; i<maxPeople;i++) {
            People people = peoples.get(i);
            String peopleName = people.getNom();
            peopleName = peopleName.toLowerCase();

            if(peopleName.contains(name)) {
                peoplesSearch.add(people);
            }
        }


        return peoplesSearch;
    }



    private List<People> recupererPeople() {
        List<People> peoples = new ArrayList<People>();

        Resources r = this.getResources();

        String [] strings = r.getStringArray(R.array.people_array);

        int maxPeople = (strings.length)/4;

        for(int i = 0;i<maxPeople;i++) {

            String nom=strings[i*4+0];
            String job=strings[i*4+1];
            String salle=strings[i*4+2];

            String resDrawable = strings[i*4+3];




            int drawableResourceId = this.getResources().getIdentifier(resDrawable, "drawable", this.getPackageName());
            peoples.add(new People(drawableResourceId,nom,job,salle));
        }







        return peoples;
    }

    private void onSalle() {
        setContentView(R.layout.tabletlayoutpersonne);

        menu="salle";
        mainThread.setMenu(menu);

        Button buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHome();
            }
        });



        final String[] salles = new String[]{
                "Amphi 10", "Amphi 11","A 101" , "A 102", "A 103", "A 104", "A 105", "A 106", "A 107", "A 108", "B 101" , "B 102", "B 103", "B 104", "B 105", "B 106", "B 107", "B 108"
        };

        final ListView listView = (ListView) findViewById(R.id.listView);

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TabletActivity.this,
                android.R.layout.simple_list_item_1, salles);
        listView.setAdapter(adapter);

        final Button buttonRecherche= (Button) findViewById(R.id.buttonRecherche);
        final EditText editText = (EditText) findViewById(R.id.editText);
        editText.setHint("Taper le nom de la Salle ici");



        buttonRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String salle = editText.getText().toString();
                salle = salle.toLowerCase();



                ArrayList<String> sallesSearch = new ArrayList<String>();

                int maxSalle = salles.length;

                for(int i = 0;i<maxSalle;i++) {
                    String room = salles[i];
                    room = room.toLowerCase();

                    if(room.contains(salle)) {
                        sallesSearch.add(salles[i]);
                    }

                }




                ArrayAdapter<String> adapter = new ArrayAdapter<String>(TabletActivity.this,
                        android.R.layout.simple_list_item_1, sallesSearch);
                listView.setAdapter(adapter);


            }
        });

        Button buttonReset = (Button) findViewById(R.id.buttonReset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText.setText("");
                listView.setAdapter(adapter);

            }
        });

    }




    protected void error(String string) {
        makeText(TabletActivity.this, "Mauvaise valeur de : " + string, LENGTH_SHORT).show();
    }

}
