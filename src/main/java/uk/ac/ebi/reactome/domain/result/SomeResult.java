package uk.ac.ebi.reactome.domain.result;

import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.Set;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 11.11.15.
 * TODO
 *
 *
 * There's no support at the moment to map entities into a @QueryResult.
 * So instead of returning User and Role you'll have to return user id's and role id's and
 * then hydrate them using load for example.

Alternatively, if you're looking for a simple way to find users by roles,
you might try a method such as  List<User> findByRolesName(String roleName) on the UserRepository
 *
 */
@QueryResult
public class SomeResult {

    Long pathway;
    Set<Long> entitySet;

    int count;

    public Long getPathway() {
        return pathway;
    }

    public void setPathway(Long pathway) {
        this.pathway = pathway;
    }

    public Set<Long> getEntitySet() {
        return entitySet;
    }

    public void setEntitySet(Set<Long> entitySet) {
        this.entitySet = entitySet;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //    TODO

}
