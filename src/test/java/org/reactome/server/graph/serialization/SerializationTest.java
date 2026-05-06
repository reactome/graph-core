package org.reactome.server.graph.serialization;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reactome.server.graph.domain.annotations.StoichiometryView;
import org.reactome.server.graph.domain.model.CandidateSet;
import org.reactome.server.graph.domain.model.Complex;
import org.reactome.server.graph.domain.model.DatabaseObject;
import org.reactome.server.graph.domain.relationship.Has;
import org.reactome.server.graph.service.BaseTest;
import org.reactome.server.graph.service.PhysicalEntityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class SerializationTest extends BaseTest {
    @JsonIdentityInfo(generator = JSOGGenerator.class)
    public abstract static class DatabaseObjectJSOGMixin {
    }

    private static PhysicalEntityService service;
    private static Complex toSerialize;
    private static ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .addMixIn(DatabaseObject.class, DatabaseObjectJSOGMixin.class);

    private static ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
    private static ObjectReader reader = mapper.reader();


    @BeforeAll
    public static void before(@Autowired PhysicalEntityService physicalEntityService) {
        service = physicalEntityService;
        toSerialize = (Complex) physicalEntityService.getPhysicalEntityInDepth("R-FLU-195925", 12);
    }

    @Test
    public void testNoView() throws IOException {
        String json = writer
                .writeValueAsString(toSerialize);

        Map<String, Object> jsonMap = reader.readValue(json, Map.class);

        Assertions.assertThat(jsonMap.get("components")).isNotNull();
        Assertions.assertThat(jsonMap.get("hasComponent")).isNotNull();
    }

    @Test
    public void testNestedView() throws IOException {
        String json = writer
                .withView(StoichiometryView.Nested.class)
                .writeValueAsString(toSerialize);

        Map<String, Object> jsonMap = reader.readValue(json, Map.class);

        Object components = jsonMap.get("components");
        Assertions.assertThat(components).isNotNull().isInstanceOf(Collection.class);
        assert ((Collection<?>) components).size() <= 17;
    }

    @Test
    public void testCompositionAggregator() throws IOException {
        CandidateSet set = (CandidateSet) service.getPhysicalEntityInDepth("R-HSA-9842597", -1);
        List<Has<? extends DatabaseObject>> composition = set.getComposedOf();
        Set<? extends DatabaseObject> composingElements = composition.stream().map(Has::getElement).collect(Collectors.toSet());

        Assertions.assertThat(set.getHasCandidate()).allMatch(composingElements::contains);
        Assertions.assertThat(set.getHasMember()).allMatch(composingElements::contains);

        String json = writer
                .withView(StoichiometryView.NestedAggregated.class)
//                .withView(CompositionAggregator.class)
                .writeValueAsString(set);

        Map<String, Object> jsonMap = reader.readValue(json, Map.class);

        Object components = jsonMap.get("composedOf");
        Assertions.assertThat(components).isNotNull().isInstanceOf(Collection.class);
        assert ((Collection<?>) components).size() ==
                ((Collection<?>) jsonMap.get("candidates")).size() +
                        ((Collection<?>) jsonMap.get("members")).size();
    }

    @Test
    public void testDuplicatedView() throws IOException {
        String json = writer
                .withView(StoichiometryView.Flatten.class)
                .writeValueAsString(toSerialize);

        Map<String, Object> jsonMap = reader.readValue(json, Map.class);

        Object components = jsonMap.get("hasComponent");
        Assertions.assertThat(components).isNotNull().isInstanceOf(Collection.class);
        assert ((Collection<?>) components).size() >= 4_000;

        assert !jsonMap.containsKey("composedOf");
    }
}
