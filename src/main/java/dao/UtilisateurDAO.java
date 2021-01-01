package dao;

import beans.Utilisateur;

import java.io.InputStream;
import java.util.List;


public interface UtilisateurDAO{


    Utilisateur getById(int id);

    Utilisateur getByEmail(String email);

    List<Utilisateur> getAll();

    boolean add(Utilisateur utilisateur);

    boolean update(Utilisateur utilisateur, InputStream finput, Object... data);

    boolean delete(Utilisateur utilisateur);

    boolean canLogin(Utilisateur utilisateur);

    boolean nicknameExists(String nickname);

    boolean emailExists(String email);

    boolean idExists(int idUser);



}
