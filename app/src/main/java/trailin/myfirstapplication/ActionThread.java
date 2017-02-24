package trailin.myfirstapplication;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by trail on 2017-02-04.
 */

public class ActionThread extends Thread {
    Button buttonUp = null;
    Button buttonLeft = null;
    Button buttonRight = null;
    Button buttonDown = null;
    SeekBar sliderVitesse=null;
    int vitesseInt = 1500;
    Socket socket = null;
    String ip = null;
    Boolean bite = true;
    boolean wait = true;
    private DataOutputStream oos = null;
    int verticaleInt = 0;   // de 0 à 9, ni go ni gor        // de 10 à 19 go     // de 20 à 29 gor
    int horizontaleInt = 0; // de 0 à 9, ni sweepR ni sweepL // de 10 à 19 sweepL // de 20 à 29 sweepR

    public ActionThread(){


    }

    public boolean getWait() {
        return wait;
    }
    public void setIp (String ip) {
        this.ip=ip;
    }
    public String getIp() {return ip;}

    public void setBite (boolean bite) {
        this.bite=bite;
    }

    public void setButtonDown(Button buttonDown) {
        this.buttonDown = buttonDown;
    }

    public void setButtonRight(Button buttonRight) {
        this.buttonRight = buttonRight;
    }

    public void setButtonLeft(Button buttonLeft) {
        this.buttonLeft = buttonLeft;
    }

    public void setButtonUp(Button buttonUp) {
        this.buttonUp = buttonUp;
    }

    public void setSliderVitesse(SeekBar sliderVitesse) {
        this.sliderVitesse = sliderVitesse;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run(){
        while(bite){
            if(socket == null ) {

                try {
                    socket = new Socket(ip, 56987);
                    wait=false;
                    oos = new DataOutputStream(socket.getOutputStream());
                    send("sets 1500");



                } catch (IOException e) {
                    Log.d("", "Socket Stop");
                    e.printStackTrace();
                    wait=false;
                    return;
                }
                wait=false;


            } else {
                if (buttonDown == null || buttonLeft == null || buttonRight == null || buttonUp == null || sliderVitesse == null) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {

                    if (vitesseInt!=sliderVitesse.getProgress()) {
                        vitesseInt=sliderVitesse.getProgress();
                        send("sets " + vitesseInt);
                    }

                    if ((!(buttonUp.isPressed() || buttonDown.isPressed())) || (buttonUp.isPressed() && buttonDown.isPressed())) {
                        if(verticaleInt > 9 && horizontaleInt <9) {
                            Log.d("", "Send Stop");
                            send("stop");
                            verticaleInt=0;
                        } else {
                            if(verticaleInt < 5) {
                                verticaleInt++;
                                if(horizontaleInt >9) {
                                    verticaleInt = 0;
                                }
                            } else {
                                if(horizontaleInt <9) {
                                    Log.d("", "Send Stop");
                                    send("stop");
                                    verticaleInt = 0;
                                } else {
                                    verticaleInt = 0;
                                }
                            }
                        }


                    } else if (buttonUp.isPressed()) {
                        if(verticaleInt > 19 || verticaleInt < 10 ) {
                            Log.d("", "Send Up");
                            send("go");
                            verticaleInt=10;
                        } else {
                            if (verticaleInt < 15) {
                                verticaleInt++;
                            } else {
                                Log.d("", "Send Up");
                                send("go");
                                verticaleInt = 10;
                            }
                        }

                    } else if (buttonDown.isPressed()) {
                        if(verticaleInt < 20) {
                            Log.d("", "Send Down");
                            send("gor");
                            verticaleInt=20;
                        } else {
                            if (verticaleInt < 25) {
                                verticaleInt++;
                            } else {
                                Log.d("", "Send Down");
                                send("gor");
                                verticaleInt = 20;
                            }
                        }
                    }

                    if ((!(buttonLeft.isPressed() || buttonRight.isPressed())) || (buttonLeft.isPressed() && buttonRight.isPressed())) {
                        if(horizontaleInt > 9) {
                            Log.d("", "Send SweepStop");
                            send("sweepstop");
                            horizontaleInt=0;
                        }  else {
                            if (horizontaleInt < 5) {
                                horizontaleInt++;
                            } else {
                                Log.d("", "Send SweepStop");
                                send("sweepstop");
                                horizontaleInt = 0;
                            }
                        }
                    }   else if (buttonLeft.isPressed()) {
                        if(horizontaleInt!=10) {
                            Log.d("", "Send Left");
                            send("sweepL");
                            horizontaleInt=10;
                        }
                    } else if (buttonRight.isPressed()) {
                        if(horizontaleInt !=20) {
                            Log.d("", "Send Right");
                            send("sweepR");
                            horizontaleInt=20;
                        }
                    }

                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private synchronized void send(String s)
    {
        if(oos == null || socket == null || !socket.isConnected()) return;

        System.out.println(s);

        try {
            byte[] r = Arrays.copyOfRange(s.getBytes(), 0, 1024);

            oos.write(r);
            oos.flush();

            Thread.sleep(20);
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setSocket(Socket socket) {
        this.socket=socket;
    }

    public Socket getSocket() {
        return socket;
    }
}
