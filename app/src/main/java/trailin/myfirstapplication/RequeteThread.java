package trailin.myfirstapplication;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by lbt on 01/08/17.
 */

public class RequeteThread extends Thread {
    private TextToSpeech tts = null;
    private TextView txtSpeechInput = null;
    private TextView txtSpeechOutput = null;
    Socket socket = null;
    String ip = "127.0.0.1";
    private DataOutputStream oos = null;
    private DataInputStream iis = null;
    public RequeteThread(TextToSpeech tts,TextView txtSpeechInput,TextView txtSpeechOutput) {
        this.tts = tts;
        this.txtSpeechInput = txtSpeechInput;
        this.txtSpeechOutput = txtSpeechOutput;
    }


    public void run() {
        try {
            //Ouverture socket
            socket = new Socket(ip, 15555);
            oos = new DataOutputStream(socket.getOutputStream());
            iis = new DataInputStream(socket.getInputStream());

            //Envoie requete
            String toSend = (String) txtSpeechInput.getText();
            send(toSend);


            //Reception réponse
            String toRecv = "";
            toRecv = recv(toRecv);

            if(toRecv != null) {
                txtSpeechOutput.setText(toRecv);
                String utteranceId = this.hashCode() + "";
                tts.speak(toRecv, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
            }
            //Fermeture socket
            oos.close();
            iis.close();
            socket.close();

        } catch (IOException e) {
            Log.d("", "Socket Stop");
            e.printStackTrace();
            return;
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
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized String recv(String s)
    {
        if(iis == null || socket == null || !socket.isConnected()) return null;

        System.out.println(s);

        try {
            byte[] r = Arrays.copyOfRange(s.getBytes(), 0, 1024);

            iis.read(r,0,1014);

            s = r.toString();

            Thread.sleep(20);

            return s;
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

}
