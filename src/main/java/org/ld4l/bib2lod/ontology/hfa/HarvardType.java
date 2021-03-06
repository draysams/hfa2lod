package org.ld4l.bib2lod.ontology.hfa;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the cartography types used in the LD4L BIBFRAME 2 extensions and
 * application profile.
 */
//TODO This should be moved to someplace common for both FGDC and HFA use.
public enum HarvardType implements Type {
    
    HGLID(HarvardNamespace.METAL, "HGLID");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    HarvardType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    } 

}
