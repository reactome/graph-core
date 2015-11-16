package uk.ac.ebi.reactome.repository;

import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;

import java.util.Collection;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 */
@Repository
public class GenericRepositoryImpl implements GenericRepository {

    @Autowired
    private Session session;

    @Override
    public void cleanDatabase() {
        session.purgeDatabase();
    }

    @Override
    public DatabaseObject findByDbId(Long dbId) {
        Collection<DatabaseObject> collection =  session.loadAll(DatabaseObject.class, new Filter("dbId", dbId));
        if (collection!=null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    @Override
    public DatabaseObject findByDbId(Long dbId, Integer depth) {
        Collection<DatabaseObject> collection =  session.loadAll(DatabaseObject.class, new Filter("dbId", dbId), depth);
        if (collection!=null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    @Override
    public DatabaseObject findByStId(String stId) {
        Collection<DatabaseObject> collection =  session.loadAll(DatabaseObject.class, new Filter("stId", stId));
        if (collection!=null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }

    @Override
    public DatabaseObject findByStId(String stId, Integer depth) {
        Collection<DatabaseObject> collection =  session.loadAll(DatabaseObject.class, new Filter("stId", stId), depth);
        if (collection!=null && !collection.isEmpty()) {
            return collection.iterator().next();
        }
        return null;
    }
}
