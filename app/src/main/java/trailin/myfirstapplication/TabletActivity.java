package trailin.myfirstapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

        String[] prenoms = new String[]{
                "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
                "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
                "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
                "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
                "Yann", "Zoé"
        };

        ListView listView = (ListView) findViewById(R.id.listView);

        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TabletActivity.this,
                android.R.layout.simple_list_item_1, prenoms);
        listView.setAdapter(adapter);

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
