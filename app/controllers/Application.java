package controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import models.Connection;
import models.Peer;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.node.ObjectNode;

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

    public static Result findAllRelatedPeersAndConnections(Long id) {
        Peer peer = Peer.find.byId(id);
        Set<Peer> peers = new HashSet<>();
        peers.add(peer);
        Set<Connection> connections = new HashSet<>();
        Stack<Connection> working = new Stack<Connection>();
        working.addAll(Connection.getRelations(id));
        while (!working.isEmpty()) {
            Connection connection = working.pop();
            if (!connections.contains(connection)) {
                connections.add(connection);
                Peer producer = Peer.find.byId(connection.producerId);
                Peer consumer = Peer.find.byId(connection.consumerId);
                peers.add(producer);
                peers.add(consumer);
                List<Connection> tmp = new ArrayList<>();
                tmp.addAll(Connection.getRelations(producer.id));
                tmp.addAll(Connection.getRelations(consumer.id));
                for (Connection c : tmp) {
                    if (!connections.contains(c)) {
                        working.push(c);
                    }
                }
            }
        }

        ObjectNode result = Json.newObject();
        result.set("peers", Json.toJson(peers));
        result.set("connections", Json.toJson(connections));

        return ok(Json.toJson(result));
    }
}
