/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.record.xml.hfa.HfaTextField;

/**
 * Builds a Title Entity.
 */
public class HfaToTitleBuilder extends HfaToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
    	HfaRecord record = (HfaRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A HfaRecord is required to build a title.");
        }

        Entity parentEntity = params.getParent();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent Entity is required to build a title.");
        }
        
        HfaTextField hfaTitleField = (HfaTextField) params.getField();
        if (hfaTitleField == null) {
            throw new EntityBuilderException(
                    "A HfaField with is required to build a title.");
        }

        StringBuilder fullTitle = new StringBuilder();
        if (HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText().equals(hfaTitleField.getFieldName())) {
        	// look for Prefix field only fot this one title type
        	HfaTextField hfaPrefix = record.getField(HfaRecord.ColumnAttributeText.PREFIX);
        	if (hfaPrefix != null) {
        		fullTitle.append(hfaPrefix.getTextValue().trim());
        		fullTitle.append(' ');
        	}
        }
        fullTitle.append(hfaTitleField.getTextValue().trim());

        Entity titleEntity = new Entity(Ld4lTitleType.TITLE);
        titleEntity.addAttribute(Ld4lDatatypeProp.LABEL, fullTitle.toString().trim());
    	parentEntity.addRelationship(Ld4lObjectProp.HAS_TITLE, titleEntity);
        
        return titleEntity;
    }
    
}
