package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Connection extends Model {

    private static final long serialVersionUID = 3383704615007624074L;

    @Id
    public Long id;

    @Required
    public Long producerId;

    @Required
    public Long consumerId;

    @Required
    public String description;

    public static Finder<Long, Connection> find = new Finder<Long, Connection>(Long.class, Connection.class);

    public Connection(Long id, Long producerId, Long consumerId, String description) {
        this.id = id;
        this.producerId = producerId;
        this.consumerId = consumerId;
        this.description = description;
    }

    public static List<Connection> getConsumerRelations(Long peerId) {
        return find.where().eq("consumerId", peerId).findList();
    }

    public static List<Connection> getProducerRelations(Long peerId) {
        return find.where().eq("producerId", peerId).findList();
    }

    public static List<Connection> getRelations(Long peerId) {
        return find.where("producerId = :producerId OR consumerId = :consumerId")
                .setParameter("producerId", peerId)
                .setParameter("consumerId", peerId)
                .findList();
    }

}
