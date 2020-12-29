package servlets;

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDAO;
import forms.ModifierProfilForm;
import tools.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.util.Base64;

@MultipartConfig
public class ModifierProfil extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;


    @Override
    public void init() throws ServletException {
        this.utilisateurDAO = ((DAOFactory)getServletContext().getAttribute(Util.ATT_DAO_FACTORY)).getUtilisateurDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute(Util.ATT_SESSION_USER);

        if(utilisateur == null) { // if no user, redirect to login page
            resp.sendRedirect(req.getContextPath()+"/connexion");
        } else {
            req.setAttribute("utilisateur",utilisateur);
            this.getServletContext().getRequestDispatcher( "/user-restricted/modifier_profil.jsp" ).forward( req, resp );
        }
    }

    //FIXME : check autorizations before posting
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute("sessionUtilisateur");

        ModifierProfilForm form = new ModifierProfilForm(utilisateurDAO);
        form.modifierUtilisateur(req);
        req.setAttribute("form", form);
        req.setAttribute("utilisateur", utilisateur);

        if(form.getErrors().isEmpty()) {
            resp.sendRedirect(req.getContextPath()+"/user-restricted/profil"); // Returns to the main page
        } else {
            req.getRequestDispatcher("/user-restricted/modifier_profil.jsp").forward(req, resp);
        }
     }



}
