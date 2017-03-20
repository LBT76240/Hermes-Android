package trailin.myfirstapplication;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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


        final ListView listView = (ListView) findViewById(R.id.listView);


        final List<People> peoples = recupererPeople();;

        final PeopleAdapter adapter = new PeopleAdapter(TabletActivity.this, peoples);
        listView.setAdapter(adapter);


        final Button buttonRecherche= (Button) findViewById(R.id.buttonRecherche);
        final EditText editText = (EditText) findViewById(R.id.editText);

        buttonRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                name = name.toLowerCase();

                List<People> peoplesSearch = recupererPeople(name,peoples);

                PeopleAdapter adapter = new PeopleAdapter(TabletActivity.this, peoplesSearch);
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

        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText("Tapez le numéro de salle ici");

        String[] prenoms = new String[]{
                "Amphi 10", "Amphi 11", "B101" , "B102", "B103", "B104", "B105", "B106", "B107", "B108"
        };

        ListView listView = (ListView) findViewById(R.id.listView);

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TabletActivity.this,
                android.R.layout.simple_list_item_1, prenoms);
        listView.setAdapter(adapter);

    }




    protected void error(String string) {
        makeText(TabletActivity.this, "Mauvaise valeur de : " + string, LENGTH_SHORT).show();
    }

}
