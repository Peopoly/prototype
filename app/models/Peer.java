package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Peer extends Model {

    private static final long serialVersionUID = 6192758909687316049L;

    @Id
    public Long id;

    @Required
    public String name;

    @Required
    public String description;

    public static Finder<Long, Peer> find = new Finder<Long, Peer>(Long.class, Peer.class);

    public Peer(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static List<Peer> matching(String name) {
        return find.where().ilike("name", "%" + name + "%").findList();
    }
}
