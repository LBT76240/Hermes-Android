package trailin.myfirstapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.net.Uri;


import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    ActionThread actionThread= null;
    MainThread mainThread = MainThread.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        actionThread = mainThread.getActionThread();

        if(actionThread == null) {
            onDisconnected();
        } else {
            onConnected();
        }





















        /* IMC
        setContentView(R.layout.imc);

        Button buttonCalcul = (Button) findViewById(R.id.calcul);
        Button buttonRaz = (Button) findViewById(R.id.raz);



        buttonRaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editPoids = (EditText) findViewById(R.id.poids);
                editPoids.getText().clear();

                EditText editTaille = (EditText) findViewById(R.id.taille);
                editTaille.getText().clear();

                CheckBox checkBox = (CheckBox) findViewById(R.id.mega);
                checkBox.setChecked(false);

                RadioButton radioButton = (RadioButton) findViewById(R.id.radio2);
                radioButton.setChecked(true);

            }
        });

        buttonCalcul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CheckBox checkbox = (CheckBox) findViewById(R.id.mega);
                if(checkbox.isChecked()) {
                    Toast.makeText(MainActivity.this, megaString, Toast.LENGTH_SHORT).show();
                } else {

                    EditText editPoids = (EditText) findViewById(R.id.poids);


                    float poids;
                    String stringP = editPoids.getText().toString();
                    if (!stringP.equals("")) {

                        poids = Float.valueOf(stringP);

                        if (poids > 0) {


                            EditText editTaille = (EditText) findViewById(R.id.taille);


                            float taille;
                            String stringT = editTaille.getText().toString();
                            if (!stringT.equals("")) {
                                taille = Float.valueOf(stringT);

                                if (taille > 0) {


                                    RadioGroup radioGroup = (RadioGroup) findViewById(R.id.group);
                                    int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                                    if (checkedRadioButtonId == R.id.radio2) {
                                        taille = taille / 100;
                                    }

                                    float result = poids / (taille * taille);

                                    TextView resultView = (TextView) findViewById(R.id.result);
                                    resultView.setText("Ton resultat est de " + result);
                                } else {
                                    error("Taille");
                                }
                            } else {
                                error("Taille");
                            }

                        } else {
                            error("Poids");
                        }

                    } else {
                        error("Poids");
                    }
                }

            }
        });

        */



    }

    protected void onConnected() {

        setContentView(R.layout.remotecontrol);

        Button buttonUp= (Button) findViewById(R.id.up);
        Button buttonLeft= (Button) findViewById(R.id.left);
        Button buttonRight= (Button) findViewById(R.id.right);
        Button buttonDown= (Button) findViewById(R.id.down);
        Button buttonDisconnect = (Button) findViewById(R.id.disconnect);

        VideoView videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setVideoURI(Uri.parse("udp//@" + actionThread.getIp() +":56988/"));
        videoView.start();


        final TextView textViewV = (TextView) findViewById(R.id.vertical);
        final TextView textViewH = (TextView) findViewById(R.id.horizontale);

        actionThread.setButtonUp(buttonUp);
        actionThread.setButtonDown(buttonDown);
        actionThread.setButtonLeft(buttonLeft);
        actionThread.setButtonRight(buttonRight);

        System.out.printf("Lapin");





        buttonUp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(textViewV.getText().toString().equals("Stop")) {
                    Log.d("", "Button UP On Touch");
                    textViewV.setText("Up");
                }
                return false;
            }
        });

        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewV.getText().toString().equals("Up")) {
                    Log.d("", "Button UP On Click");
                    textViewV.setText("Stop");
                }

            }
        });





        buttonLeft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(textViewH.getText().toString().equals("Stop")) {
                    Log.d("", "Button Left On Touch");
                    textViewH.setText("Left");
                }
                return false;
            }
        });

        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewH.getText().toString().equals("Left")) {
                    Log.d("", "Button LEFT On Click");
                    textViewH.setText("Stop");
                }
            }
        });

        buttonRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(textViewH.getText().toString().equals("Stop")) {
                    Log.d("", "Button RIGHT On Touch");
                    textViewH.setText("Right");
                }
                return false;
            }
        });

        buttonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewH.getText().toString().equals("Right")) {
                    Log.d("", "Button RIGHT On Click");
                    textViewH.setText("Stop");
                }
            }
        });
        buttonDown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(textViewV.getText().toString().equals("Stop")) {
                    Log.d("", "Button DOWN On Touch");
                    textViewV.setText("Down");
                }
                return false;
            }
        });
        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textViewV.getText().toString().equals("Down")) {
                    Log.d("", "Button DOWN On Click");
                    textViewV.setText("Stop");
                }
            }
        });

        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionThread.setBite(false);
                actionThread.disconnect();
                actionThread = null;
                mainThread.setActionThread(null);
                onDisconnected();
            }
        });

    }

    protected void onDisconnected() {

        setContentView(R.layout.connect);

        Button connectButton = (Button) findViewById(R.id.connectButton);
        final EditText ipText = (EditText) findViewById(R.id.ip);



        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                try
                {
                    if(oos != null) oos.close();
                    if(socket != null) socket.close();
                } catch (IOException e) {e.printStackTrace();}
                */
                actionThread = new ActionThread();
                mainThread.setActionThread(actionThread);

                //;
                //String ip = ;
                //InetAddress inetAddress = InetAddress.getByName("192.168.1.137");

                actionThread.setIp(ipText.getText().toString());

                actionThread.start();


                while(actionThread.getWait());

                if(actionThread.getSocket()==null) {
                    Toast.makeText(MainActivity.this, "Could not connect to this ip", Toast.LENGTH_SHORT).show();
                    actionThread.setIp(null);
                    actionThread.setBite(false);
                    actionThread = null;
                    mainThread.setActionThread(null);

                } else {
                    onConnected();
                }

                //oos = new DataOutputStream(socket.getOutputStream());


                //return true;

            }
        });
    }




    protected void error(String string) {
        makeText(MainActivity.this, "Mauvaise valeur de : " + string, LENGTH_SHORT).show();
    }



}
