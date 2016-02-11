package uk.ac.ebi.reactome.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;
import uk.ac.ebi.reactome.domain.model.DatabaseObject;
import uk.ac.ebi.reactome.domain.model.ReferenceEntity;
import uk.ac.ebi.reactome.domain.result.LabelsCount;
import uk.ac.ebi.reactome.domain.result.Participant;
import uk.ac.ebi.reactome.domain.result.Participant2;
import uk.ac.ebi.reactome.repository.DatabaseObjectRepository;
import uk.ac.ebi.reactome.service.helper.AttributeProperties;
import uk.ac.ebi.reactome.service.helper.Node;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by:
 *
 * @author Florian Korninger (florian.korninger@ebi.ac.uk)
 * @since 10.11.15.
 */
@Service
public class DatabaseObjectServiceImpl extends ServiceImpl<DatabaseObject> implements DatabaseObjectService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseObjectServiceImpl.class);

    private Map<String,Node> map = new HashMap<>();

    @Autowired
    private DatabaseObjectRepository databaseObjectRepository;

    @Override
    public GraphRepository<DatabaseObject> getRepository() {
        return databaseObjectRepository;
    }

    @Override
    public DatabaseObject findByDbId(Long dbId) {
        return databaseObjectRepository.findByDbId(dbId);
    }

    @Override
    public DatabaseObject findByDbIdNoRelations(Long dbId) {
        return databaseObjectRepository.findByDbIdNoRelations(dbId);
    }

    @Override
    public DatabaseObject findByStableIdentifier(String stableIdentifier) {
        return databaseObjectRepository.findByStableIdentifier(stableIdentifier);
    }

    @Override
    public Collection<ReferenceEntity> getParticipatingMolecules(Long dbId) {
        return databaseObjectRepository.getParticipatingMolecules(dbId);
    }

    @Override
    public Collection<Participant> getParticipatingMolecules2(Long dbId) {
        return databaseObjectRepository.getParticipatingMolecules2(dbId);
    }


    @Override
    public Collection<Participant2> getParticipatingMolecules3(Long dbId) {
        return databaseObjectRepository.getParticipatingMolecules3(dbId);
    }

    @Override
    public Collection<LabelsCount> getLabelsCount() {
        return databaseObjectRepository.getLabelsCount();
    }

    @Override
    public Node getGraphModelTree() throws ClassNotFoundException {

        Collection<LabelsCount> labelsCounts = databaseObjectRepository.getLabelsCount();
        String packageName = DatabaseObject.class.getPackage().getName() + ".";
        for (LabelsCount labelsCount : labelsCounts) {
            Class lowestClass = Object.class;
            for (String label : labelsCount.getLabels()) {
                Class clazz = Class.forName(packageName + label);
                if (lowestClass.isAssignableFrom(clazz)) {
                    lowestClass = clazz;
                }
            }
            recursion(lowestClass, null, labelsCount.getCount());
        }
        Node n = map.get(DatabaseObject.class.getSimpleName());
        correctCounts(n);
        return n;
    }

    public Set<AttributeProperties> getAttributeTable(Class clazz) {
        Set<AttributeProperties> propertiesList = new TreeSet<>();
        Set<Method> declaredMethods = new HashSet<>(Arrays.asList(clazz.getDeclaredMethods()));
        for (Method method : clazz.getMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("get")
                    && !methodName.startsWith("getSuper")
                    && !methodName.equals("getClass")
                    && !methodName.equals("getId")) {

                AttributeProperties properties = (getAttributeProperties(method));
                if (declaredMethods.contains(method)) {
                    properties.setDeclaredMethod(true);
                } else {
                    properties.setDeclaredMethod(false);
                }
                propertiesList.add(properties);
            }
        }
        return propertiesList;
    }

    private AttributeProperties getAttributeProperties(Method method) {
        AttributeProperties properties = new AttributeProperties();
        properties.setName(lowerFirst(method.getName().substring(3)));
        Type returnType = method.getGenericReturnType();
        if (returnType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            properties.setCardinality("+");
            if (typeArguments.length>0) {
                Class clazz = (Class) typeArguments[0];
                properties.setValueType(clazz.getSimpleName());
            }
        } else {
            Class clazz = (Class) returnType;
            properties.setCardinality("1");
            properties.setValueType(clazz.getSimpleName());
        }
        return properties;
    }

    private void correctCounts(Node node) {
        if (node.getChildren()!=null) {
            for (Node node1 : node.getChildren()) {
                correctCounts(node1);
                node.setCount(node.getCount() + node1.getCount());
            }
        }
    }

    private void recursion(Class clazz,Node oldnode, int count) {

        if (!clazz.equals(Object.class)) {
            Node node = new Node(clazz,count);
            if (map.containsKey(clazz.getSimpleName())) {
                if (oldnode != null) {
                    map.get(clazz.getSimpleName()).addChild(oldnode);
                } else {
                    Node existingNode = map.get(clazz.getSimpleName());
                    existingNode.setCount(existingNode.getCount() + count);
                }
            } else {
                if (oldnode != null) {
                    node.addChild(oldnode);
                }
                map.put(clazz.getSimpleName(),node);
                recursion(clazz.getSuperclass(),node,0);
            }
        }
    }

    private String lowerFirst(String str) {
        if(StringUtils.isAllUpperCase(str)) {
            return str;
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

}
