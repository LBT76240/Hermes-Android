package trailin.myfirstapplication;

/**
 * Created by trail on 2017-03-19.
 */

public class People {



    private String nom;
    private String job;
    private String salle;
    private int avatar;
    public People(int avatar, String nom, String job, String salle) {
        this.nom=nom;
        this.job=job;
        this.avatar=avatar;
        this.salle=salle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}