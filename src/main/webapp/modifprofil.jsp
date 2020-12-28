<%@ page import="beans.Utilisateur" %><%--
  Created by IntelliJ IDEA.
  User: florian
  Date: 28/12/2020
  Time: 13:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modification Profil</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <div class="view-account">
        <section class="module">
            <div class="module-inner">
                <div class="content-panel">
                    <form method="post" action="modifprofil" class="form-horizontal">
                        <fieldset class="fieldset">
                            <div class="form-group avatar">
                                <figure class="figure col-md-2 col-sm-3 col-xs-12">
                                    <img class="img-thumbnail" src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="">
                                </figure>
                                <div class="form-inline col-md-10 col-sm-9 col-xs-12">
                                    <input type="file" class="file-uploader pull-left">
                                    <button type="submit" class="btn btn-sm btn-default-alt pull-left">Valider</button>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Prénom</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifprenom" type="text" class="form-control" value="${utilisateur.prenom}">

                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Nom</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifnom" type="text" class="form-control" value="${utilisateur.nom}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2  col-sm-3 col-xs-12 control-label">Email</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifemail" type="email" class="form-control" value="${utilisateur.email}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Date de naissance</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name = "modifdate" type="date" class="form-control" value="${utilisateur.date}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Mot de passe</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name="motdepasse" type="password" class="form-control" value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-md-2 col-sm-3 col-xs-12 control-label">Confirmation</label>
                                <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input name="confirmation" type="password" class="form-control" value="">
                                </div>
                            </div>
                        </fieldset>
                        <hr>
                        <div class="form-group">
                            <div class="col-md-10 col-sm-9 col-xs-12 col-md-push-2 col-sm-push-3 col-xs-push-0">
                                <input class="btn btn-primary" type="submit" value="Valider">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </section>
    </div>
</div>
</body>
</html>