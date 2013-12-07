package controllers;

import models.Connection;
import models.Peer;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {

    public static Result index() {
        // return ok(index.render("Your new application is ready."));
        return redirect("/assets/assets/index.html");
        // return ok(Assets.at("/public", "/html/index.html")).as;
    }

    public static Result getPeers() {
        return ok(Json.toJson(Peer.find.all()));
    }

    public static Result getPeer(Long id) {
        Peer peer = Peer.find.byId(id);
        return peer == null ? notFound() : ok(Json.toJson(peer));
    }

    public static Result getConnection(Long id) {
        Connection connection = Connection.find.byId(id);
        return connection == null ? notFound() : ok(Json.toJson(connection));
    }

    public static Result findConnectionsForPeer(Long id) {
        return ok(Json.toJson(Connection.getRelations(id)));
    }

    public static Result findPeerByName(String name) {
        return ok(Json.toJson(Peer.matching(name)));
    }

    public static Result getConnections() {
        return ok(Json.toJson(Connection.find.all()));
    }
}
