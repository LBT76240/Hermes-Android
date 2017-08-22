package trailin.myfirstapplication;


import android.app.Activity;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import java.util.Locale;
import java.util.UUID;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.widget.ImageButton;

/**
 * Created by trail on 2017-03-14.
 */

public class TabletActivity extends Activity {
    private TextToSpeech tts;



    MainThread mainThread = MainThread.getInstance();
    String menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.FRANCE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    String utteranceId=this.hashCode() + "";
                    tts.speak("Hello", TextToSpeech.QUEUE_FLUSH, null, utteranceId);

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });


        Log.d("", "onCreate");




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


    private void onAddContact() {
        if(false) {
            final List<People> peoples = recupererPeople();

            for (int i = 0; i < peoples.size(); i++) {
                // Creates a new Intent to insert a contact
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                // Sets the MIME type to match the Contacts Provider
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        /* Assumes EditText fields in your UI contain an email address
 * and a phone number.
 *
 */



/*
 * Inserts new data into the Intent. This data is passed to the
 * contacts app's Insert screen
 */
// Inserts an email address
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "test@yolo.tem")
/*
 * In this example, sets the email type to be a work email.
 * You can set other email types as necessary.
 */
                        .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
// Inserts a phone number
                        .putExtra(ContactsContract.Intents.Insert.PHONE, "0606060606")
/*
 * In this example, sets the phone type to be a work phone.
 * You can set other phone types as necessary.
 */
                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                        .putExtra(ContactsContract.Intents.Insert.NAME, peoples.get(i).getNom());

                startActivity(intent);
            }
        }
    }


    private void onHome() {
        setContentView(R.layout.tabletlayouthome);
        menu = "home";
        mainThread.setMenu(menu);



        Button buttonCarte = (Button) findViewById(R.id.buttonCarte);
        Button buttonPersonne = (Button) findViewById(R.id.buttonPersonne);
        Button buttonSalle = (Button) findViewById(R.id.buttonSalle);
        Button buttonIA = (Button) findViewById(R.id.buttonIA);
        Button buttonContact = (Button) findViewById(R.id.buttonContact);

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
        buttonIA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onIA();
            }
        });
        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddContact();
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


    private TextView txtSpeechInput;
    private TextView txtSpeechOutput;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;


    protected void onIA() {
        setContentView(R.layout.activity_main);

        Button buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHome();
            }
        });

        txtSpeechInput = (TextView) findViewById(R.id.txtSpeechInput);
        txtSpeechOutput = (TextView) findViewById(R.id.txtSpeechOutput);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        // hide the action bar
        //getActionBar().hide();

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0));
                    txtSpeechOutput.setText("");

                    EditText ipEdit = (EditText) findViewById(R.id.ipEdit);
                    String ip = ipEdit.getText().toString();
                    RequeteThread requeteThread = new RequeteThread(tts,txtSpeechInput,txtSpeechOutput,ip);

                    requeteThread.start();

                    /*
                    String toSpeak = result.get(0);
                    String utteranceId=this.hashCode() + "";
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
                    */

                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
