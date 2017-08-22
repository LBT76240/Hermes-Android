package trailin.myfirstapplication;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by lbt on 01/08/17.
 */

public class RequeteThread extends Thread {
    private TextToSpeech tts = null;
    private TextView txtSpeechInput = null;
    private TextView txtSpeechOutput = null;
    Socket socket = null;
    String ip;
    private DataOutputStream oos = null;
    private DataInputStream iis = null;
    public RequeteThread(TextToSpeech tts,TextView txtSpeechInput,TextView txtSpeechOutput,String ip) {
        this.tts = tts;
        this.txtSpeechInput = txtSpeechInput;
        this.txtSpeechOutput = txtSpeechOutput;
        this.ip=ip;
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
                //txtSpeechOutput.setText(toRecv);
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

    private synchronized void send(String toSend)
    {
        if(oos == null || socket == null || !socket.isConnected()) return;

        System.out.println(toSend);

        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("type", "question");
            jsonObj.put("msg", toSend);
            String msg = jsonObj.toString();
            byte[] r = Arrays.copyOfRange(msg.getBytes(), 0, msg.getBytes().length);
            int a = msg.getBytes().length;
            System.out.print(a);
            oos.write(r);
            oos.flush();

            Thread.sleep(20);
        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private synchronized String recv(String toRecv)
    {
        if(iis == null || socket == null || !socket.isConnected()) return null;

        System.out.println(toRecv);

        try {
            byte[] r = Arrays.copyOfRange(toRecv.getBytes(), 0, 1024);

            iis.read(r,0,1014);



            toRecv = new String(r);


            try {
                JSONObject jsonObj = new JSONObject(toRecv);
                String msg = jsonObj.getString("msg");
                Thread.sleep(20);

                return msg;
            } catch (JSONException e) {
                return "Le server est manifestement en colère envers Json";
            }


        } catch (IOException |InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }

}
