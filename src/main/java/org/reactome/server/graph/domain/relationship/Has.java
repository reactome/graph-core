package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.service.helper.StoichiometryObject;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class Has<E extends DatabaseObject> implements Comparable<Has<E>> {
    /*-------- Essential fields ---------*/
    @Id
    @GeneratedValue
    protected Long id;
    protected int stoichiometry = 1;
    protected int order = 0;

    @TargetNode
    protected E element;

    @JsonProperty(index = 0)
    public abstract String getType();

    /*-------- Important methods ---------*/

    public StoichiometryObject toStoichiometryObject() {
        return new StoichiometryObject(stoichiometry, element);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Has)) return false;
        Has<?> has = (Has<?>) o;
        return Objects.equals(element, has.element);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(element);
    }

    @Override
    public int compareTo(Has<E> o) {
        return order - o.order;
    }

    @Override
    public String toString() {
        return "Has{" +
                "type=" + getType() +
                ", stoichiometry=" + stoichiometry +
                ", order=" + order +
                ", element=" + element +
                '}';
    }

    public static class Util {

        /**
         * Used for relationship fetchers (Don't forget the @JsonIgnore)
         *
         * @param collection Collection of relationships to convert and sort by name
         * @param <T>        The relationship target node type
         * @param <H>        The relationship type (extends Has)
         * @return A sorted list of simple StoichiometryObject, ordered by name instead of curator defined order. Never null
         */
        @NonNull
        public static <T extends DatabaseObject, H extends Has<T>> List<StoichiometryObject> simplifiedSort(Collection<H> collection) {
            if (collection == null) return new ArrayList<>();
            return collection.stream().map(Has::toStoichiometryObject).sorted().collect(Collectors.toList());
        }

        /**
         * Used for relationship simplified setters
         *
         * @param itemsWithDuplication List containing duplicated elements
         * @param noArgsConstructor    No args constructor of the desired relationship type.
         * @param <T>                  The relationship target node type
         * @param <H>                  The relationship type (extends Has)
         * @return An ordered set of relationships aggregated by their target node, with stoichiometry
         */
        public static <T extends DatabaseObject, H extends Has<T>> SortedSet<H> aggregateStoichiometry(List<T> itemsWithDuplication, Supplier<H> noArgsConstructor) {
            if (itemsWithDuplication == null) return new TreeSet<>();
            // Using LinkedHashMap in order to keep the Collection Sorted previously by AOP
            Map<Long, H> map = new LinkedHashMap<>();
            int order = 0;
            for (T item : itemsWithDuplication) {
                H has = map.get(item.getDbId());
                if (has == null) {
                    has = noArgsConstructor.get();
                    has.setElement(item);
                    has.setOrder(order++);
                    map.put(item.getDbId(), has);
                } else {
                    has.stoichiometry++;
                }
            }
            return new TreeSet<>(map.values());
        }

        /**
         * Used for relationship simplified getters
         *
         * @param aggregatedItems Collection of relationships
         * @param <T>             The relationship target node type
         * @param <H>             The relationship type (extends Has)
         * @return A list of target node types in a correct order, with elements duplicated according to the relationship stoichiometry
         */
        public static <T extends DatabaseObject, H extends Has<T>> List<T> expandStoichiometry(Collection<H> aggregatedItems) {
            if (aggregatedItems == null) return null;
            List<T> items = new ArrayList<>();
            for (H h : aggregatedItems) {
                for (int i = 0; i < h.stoichiometry; i++) {
                    items.add(h.getElement());
                }
            }
            return items;
        }

        /**
         * Used to wrap a usual relationship (no relationship wrapper) inside a relationship wrapper, to support {@link CompositionAggregator}
         * @param collection The collection of database objects to be wrapped
         * @param type the name of the relationship type of the parent object to the collection items
         * @return A list of wrapped relationships supporting {@link CompositionAggregator}
         */
        @NonNull
        public static List<Has<DatabaseObject>> wrapUniqueElements(Collection<? extends DatabaseObject> collection, String type) {
            if (collection == null) return new ArrayList<>();
            return collection.stream().map(element -> new Has.Default(type, element)).collect(Collectors.toList());
        }
    }

    private static class Default extends Has<DatabaseObject> {
        private final String type;

        public Default(String type, DatabaseObject element) {
            this.type = type;
            this.element = element;
        }

        @Override
        public String getType() {
            return type;
        }
    }

    /*-------- Getters and Setters ---------*/

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStoichiometry() {
        return stoichiometry;
    }

    public void setStoichiometry(int stoichiometry) {
        this.stoichiometry = stoichiometry;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }
}

