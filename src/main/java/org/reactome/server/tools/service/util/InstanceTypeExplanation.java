package org.reactome.server.tools.service.util;

import org.reactome.server.tools.domain.model.*;

public abstract class InstanceTypeExplanation {

    public static String getExplanation(String schemaClass ) {
        String explanation;
        switch (schemaClass) {
            case "BlackBoxEvent":
                explanation = "Shortcut reactions that make the connection between input and output, but don't provide complete mechanistic detail. " +
                        "Used for reactions that do not balance, or complicated processes for which we either don't know all the details, or we choose not to represent every step. (e.g. degradation of a protein)";
                break;
            //case CONCEPTUAL_EVENT: //DEPRECATED
//            case DEPOLYMERISATION:
//                explanation = "Reactions that follow the pattern: Polymer -> Polymer + Unit  (there may be a catalyst involved). " +
//                        "Used to describe the mechanistic detail of depolymerisation";
//                break;
//            //case EQUIVALENT_EVENT_SET: //DEPRECATED
//            case PATHWAY:
//                explanation = "A collection of related Events. These events can be ReactionLikeEvents or Pathways";
//                break;
//            case POLYMERISATION:
//                explanation = "Reactions that follow the pattern: Polymer + Unit -> Polymer (there may be a catalyst involved). " +
//                        "Used to describe the mechanistic detail of a polymerisation";
//                break;
//            case REACTION:
//                explanation = "Defines a change of state for one or more molecular entities. " +
//                        "Most reactions in Reactome involve either a) the interaction of entities to form a complex, or b) the movement of entities between compartments, or c) the chemical conversion of entities as part of a metabolic process. Reactions have a molecular balance between input and output entities";
//                break;
//            case REACTION_LIKE_EVENT:
//                explanation = "Has four subclasses: Reaction, BlackBoxEvent, Polymerisation and Depolymerisation. " +
//                        "All involve the conversion of one or more input molecular entities to an output entity, possibly facilitated by a catalyst";
//                break;
//            case FAILED_REACTION:
//                explanation = "Defines an event where genetic mutations in the nucleotide sequence produces a protein with a very little or no activity. " +
//                        "The consequence of this is that substrates are not converted to products and can therefore build up to cause pathological conditions. " +
//                        "It could also mean entities are not moved between compartments again causing imbalances in entity concentrations which can lead to pathological conditions.";
//                break;
//            case CANDIDATE_SET:
//                explanation = "A set of entities that are interchangeable in function, with two subclasses, members that are hypothetical and members that have been demonstrated. " +
//                        "Hypothetical members are identified as values of the hasCandidate slot. Members that have been demonstrated are identified in the hasMember slot. " +
//                        "At least one hasCandidate value is required; hasMember values are optional";
//                break;
//            case COMPLEX:
//                explanation = "An entity formed by the association of two or more component entities (these components can themselves be complexes). " +
//                        "At least one component must be specified. Complexes represent all experimentally verified components and their stoichiometry where this is known but may not include as yet unidentified components";
//                break;
//            case DEFINED_SET:
//                explanation = "Two or more entities that are interchangeable in function";
//                break;
//            case ENTITY_SET:
//                explanation = "Two or more entities grouped because of a shared molecular feature. " +
//                        "The superclass for CandidateSet, DefinedSet, and OpenSet";
//                break;
//            case ENTITY_WITH_ACCESSIONED_SEQUENCE:
//                explanation = "A protein, RNA, or DNA molecule or fragment thereof in a specified cellular compartment and specific post-translational state. " +
//                        "Must be linked to an external database reference, given as the value of referenceSequence. An EWAS typically corresponds to the entire protein or polynucleotide described in the external database. " +
//                        "Fragments are defined by setting the first and last residue using the numbering scheme of the external database, entered as startCoordinate and endCoordinate values. Values of 1 and -1 respectively indicate that the true start and end are unconfirmed. " +
//                        "EWAS instances are specific to a subcellular compartment; if the same molecule is found in two cellular components it will have two EWASes. " +
//                        "EWAS instances by default define an unmodified protein sequence, any post-translational modification (PTM), such as phosphorylation, requires a new EWAS instance. " +
//                        "The location and type of any PTM are defined in the hasModifiedResidue slot";
//                break;
//            case GENOME_ENCODED_ENTITY:
//                explanation = "A peptide or polynucleotide whose sequence is unknown and thus cannot be linked to external sequence databases or used for orthology inference";
//                break;
//            case OPEN_SET:
//                explanation = "A group of entities that could be listed in principle but not in practice, such as mRNA or long-chain fatty acid. " +
//                        "Examples can be specified as values of the hasMember slot, but are not an exhaustive list of the possible members. The referenceEntity slot indicates the chemical feature that is common to all the members of this class, e.g. The OpenSet  for Alcohol  would have the -OH group defined for referenceEntity";
//                break;
//            case OTHER_ENTITY:
//                explanation = "Entities that we are unable or unwilling to describe in chemical detail and cannot be put in any other class. " +
//                        "Can be used to represent complex structures in the cell that take part in a reaction but which we cannot or do not want to define molecularly, e.g. cell membrane, Holliday structure";
//                break;
//            case POLYMER:
//                explanation = "Molecules that consist of an indeterminate number of repeated units. Includes complexes whose stoichiometry is variable or unknown. " +
//                        "The repeated unit(s) is(are) identified in the repeatedUnit slot";
//                break;
//            case SIMPLE_ENTITY:
//                explanation = "A chemical species not encoded directly or indirectly in the genome, typically small molecules such as ATP or ethanol. " +
//                        "The detailed structure of a simpleEntity is specified by linking it to details of the molecule in the ChEBI or KEGG databases via the referenceEntity slot. Use of KEGG is deprecated";
//                break;
//            //case SMALL_MOLECULE: //DEPRECATED
//            case NEGATIVE_REGULATION:
//                explanation = "This describes an Event/CatalystActivity that is negatively regulated by the Regulator (e.g., allosteric inhibition, competitive inhibition";
//                break;
//            case POSITIVE_REGULATION:
//                explanation = "This describes an Event/CatalystActivity that is positively regulated by the Regulator (e.g., allosteric activation)";
//                break;
//            case REQUIREMENT:
//                explanation = "A regulator that is required for an Event/CatalystActivity to happen";
//                break;
            default:
                explanation = "Not available";
        }
        return explanation;
    }

}
