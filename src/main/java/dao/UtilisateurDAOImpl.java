package dao;

import beans.Utilisateur;
import exceptions.DAOException;
import tools.PasswordHasher;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {

    private DAOFactory daoFactory;

    public UtilisateurDAOImpl(DAOFactory factory) {
        this.daoFactory = factory;
    }


    //TODO implement method
    @Override
    public Utilisateur getById(int id) {
        return null;
    }

    //TODO implement method
    @Override
    public List<Utilisateur> getAll() {
        return null;
    }


    //TODO implement method
    @Override
    public boolean delete(Utilisateur utilisateur) {
        return false;
    }


    @Override
    public boolean update(Utilisateur utilisateur,InputStream finput,Object... data) {
        boolean updated = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = null;
        try {
            connection = daoFactory.getConnection();
            if(finput != null) { // updating user with image
                request = "UPDATE utilisateur SET image = ?, prenom = ?, nom = ?, email = ?, date_naissance = ?, nomimage = ? WHERE email = ? ";
                preparedStatement = connection.prepareStatement(request);
                preparedStatement.setBlob(1, finput);
                preparedStatement.setObject(2,data[0]);
                preparedStatement.setObject(3,data[1]);
                preparedStatement.setObject(4,data[2]);
                preparedStatement.setObject(5,data[3]);
                preparedStatement.setObject(6,data[4]);
                preparedStatement.setObject(7,data[5]);
            } else { // normal update
                request = "UPDATE utilisateur SET prenom = ?, nom = ?, email = ?, date_naissance = ? WHERE email = ? ";
                preparedStatement = SQLTools.initPreparedRequest(connection,request,false, data);
            }
            preparedStatement.executeUpdate();
            updated = true;
        } catch(Exception e) {
            System.err.println("Cannot update the user : " + e.getMessage());
            System.err.println("User object : " + utilisateur.toString());
            System.err.println("New data : " + Arrays.toString(data));
            updated =false;
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return updated;
    }


    @Override
    public Utilisateur getByEmail(String email) {
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        String request = "SELECT * FROM utilisateur WHERE email = ? ";
        Utilisateur utilisateur = new Utilisateur();
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false, email);
            resultat = preparedStatement.executeQuery();
            if(resultat.next()) {
                utilisateur = UtilisateurDAOImpl.map(resultat);
            }
        } catch (Exception e) {
            System.err.println("Cannot get the user : " + e.getMessage());
            System.err.println("Email given : " + email);
            e.printStackTrace();
        } finally {
            SQLTools.close(connection,resultat,preparedStatement);
        }
        return utilisateur;
    }



    @Override
    public boolean add(Utilisateur user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedValue = null;
        boolean created = false;
        String request = "INSERT INTO utilisateur (email, pseudo, mot_de_passe, nom , prenom, date_naissance) VALUES (?, ?, ? ,?, ?, ?)";
        try {
            connection = daoFactory.getConnection();
            byte[] passwordHash = PasswordHasher.getPasswordHash(user.getPass());
            Instant instant = user.getDate().toInstant();
            ZonedDateTime frDateTime = ZonedDateTime.ofInstant( instant, ZoneId.of("Europe/Paris"));
            preparedStatement = SQLTools.initPreparedRequest(connection, request, true,user.getEmail(), user.getPseudo(), passwordHash, user.getNom(), user.getPrenom(), frDateTime.toLocalDate());
            preparedStatement.executeUpdate();
            generatedValue = preparedStatement.getGeneratedKeys();
            if(generatedValue.next()) user.setId(generatedValue.getInt(1));
            else throw new DAOException("Cannot get new ID");
            created = true;
        } catch (Exception e) {
            System.err.println("Cannot create the user : " + e.getMessage());
            System.err.println("User Object : " + user.toString());
            created = false;
        } finally {
            SQLTools.close(connection,preparedStatement);
        }
        return created;
    }


    @Override
    public boolean canLogin(Utilisateur user) {

        boolean logged = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String request = "SELECT * FROM utilisateur WHERE email = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request, false, user.getEmail());
            result = preparedStatement.executeQuery();
            if(result.next()) {
                byte[] userPassword = result.getBytes(4);
                byte[] givenPassword = PasswordHasher.getPasswordHash(user.getPass());
                if(Arrays.equals(userPassword, givenPassword)) {
                    logged = true;
                }
            }
        } catch(Exception e) {
            System.err.println("Cannot log the user : " + e.getMessage());
            System.err.println("User Object : " + user.toString());
            logged = false;
        }  finally {
            SQLTools.close(connection,result,preparedStatement);
        }
        return logged;
    }

    @Override
    public boolean nicknameExists(String nickname) {
        boolean exists = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT count(*) FROM utilisateur WHERE pseudo = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false,nickname);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                if(resultSet.getInt(1) >= 1) exists = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot check if user exists : " + e.getMessage());
            System.err.println("Nickname given : " + nickname);
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return exists;
    }

    @Override
    public boolean emailExists(String email) {
        boolean exists = false;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        String request = "SELECT count(*) FROM utilisateur WHERE email = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request,false,email);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                if(resultSet.getInt(1) >= 1) exists = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot check if user exists : " + e.getMessage());
            System.err.println("Email given : " + email);
        } finally {
            SQLTools.close(connection,resultSet,preparedStatement);
        }
        return exists;
    }

    @Override
    public boolean idExists(int idUser) {
        boolean exists = false;
        Connection connection = null;
        ResultSet resultat = null;
        PreparedStatement preparedStatement = null;
        String request = "SELECT count(*) FROM utilisateur WHERE id = ?";
        try {
            connection = daoFactory.getConnection();
            preparedStatement = SQLTools.initPreparedRequest(connection,request, false, idUser);
            resultat = preparedStatement.executeQuery();
            while(resultat.next()) {
                if(resultat.getInt(1) >= 1) exists = true;
            }
        } catch (Exception e) {
            System.err.println("Cannot get the user : " + e);
            System.err.println("Place ID given : " + idUser);
            exists = false;
        }
        return exists;
    }

//

    private static Utilisateur map(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(resultSet.getInt(1));
        utilisateur.setEmail(resultSet.getString(2));
        utilisateur.setPseudo(resultSet.getString(3));
        utilisateur.setNom(resultSet.getString(5));
        utilisateur.setPrenom(resultSet.getString(6));
        utilisateur.setDate(resultSet.getDate(7));
        utilisateur.setImage(resultSet.getBytes(11));
        return utilisateur;
    }
}
