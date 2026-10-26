package org.reactome.server.graph.domain.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class PhysicalEntityCellType extends DatabaseObject {
    
    @Relationship(type = "physicalEntity")
    private PhysicalEntity physicalEntity;
    
    @Relationship(type = "cell")
    private Cell cell;
    
    public PhysicalEntityCellType() {}
    
    public PhysicalEntity getPhysicalEntity() {
        return physicalEntity;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setPhysicalEntity(PhysicalEntity physicalEntity) {
        this.physicalEntity = physicalEntity;
    }

}
