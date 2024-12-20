package org.reactome.server.graph.domain.relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.reactome.server.graph.domain.annotations.ReactomeSchemaIgnore;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.model.DatabaseObject;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CompositionAggregator {


    @JsonIgnore
     Stream<? extends Collection<? extends Has<? extends DatabaseObject>>> defineCompositionRelations();

    @JsonView(StoichiometryView.NestedAggregated.class)
    @ReactomeSchemaIgnore
    default List<Has<? extends DatabaseObject>> getComposedOf() {
        return defineCompositionRelations()
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
