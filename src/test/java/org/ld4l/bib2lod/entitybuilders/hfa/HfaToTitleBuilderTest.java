/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.hfa;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.hfa.HfaToTitleBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.hfa.HfaRecord;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractHfaTest;
import org.ld4l.bib2lod.testing.HfaTestData;

/**
 * Tests the HfaToTitleBuilder class.
 */
public class HfaToTitleBuilderTest extends AbstractHfaTest {
    
	private EntityBuilder titleBuilder;
	private HfaRecord hfaRecord;
	private Entity parentEntity;

    @Before
    public void setUp() throws RecordException {
        titleBuilder = new HfaToTitleBuilder();
        hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TITLE_HFA_RECORD);
        parentEntity = new Entity(Ld4lWorkType.MOVING_IMAGE);
    }
	
	@Test
	public void validTitleOnlyRecord() throws Exception {
		
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		Entity titleEntity = titleBuilder.build(params);

		Assert.assertNotNull(titleEntity);
		List<Type> types = titleEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lTitleType.TITLE));
		
		List<String> labels = titleEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		Assert.assertEquals(HfaTestData.TITLE_TEXT, labels.get(0));
	}
	
	@Test
	public void validTitleAndPrefixRecord() throws Exception {
		
		hfaRecord = buildHfaRecordFromString(HfaTestData.VALID_TITLE_AND_PREFIX_HFA_RECORD);
		
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		Entity titleEntity = titleBuilder.build(params);

		Assert.assertNotNull(titleEntity);
		List<Type> types = titleEntity.getTypes();
		Assert.assertNotNull(types);
		Assert.assertTrue(types.contains(Ld4lTitleType.TITLE));
		
		List<String> labels = titleEntity.getValues(Ld4lDatatypeProp.LABEL);
		Assert.assertNotNull(labels);
		Assert.assertEquals(1, labels.size());
		String prefixAndTitle = HfaTestData.PREFIX_TEXT + " " + HfaTestData.TITLE_TEXT;
		Assert.assertEquals(prefixAndTitle, labels.get(0));
	}
	
	@Test
	public void missingTitle_ThrowsException() throws Exception {
		
		hfaRecord = buildHfaRecordFromString(HfaTestData.INVALID_PREFIX_ONLY_HFA_RECORD);
		
		expectException(EntityBuilderException.class, "A HfaField with column attribute [" + HfaRecord.ColumnAttributeText.TITLE.getColumnAttributeText() + "] is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(parentEntity);
		
		titleBuilder.build(params);
	}
	
	@Test
	public void nullRecord_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A HfaRecord is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(null);
		params.setParent(parentEntity);
		
		titleBuilder.build(params);
	}
	
	@Test
	public void nullRelatedEntity_ThrowsException() throws Exception {
		expectException(EntityBuilderException.class, "A parent Entity is required to build a title.");
		BuildParams params = new BuildParams();
		params.setRecord(hfaRecord);
		params.setParent(null);
		
		titleBuilder.build(params);
	}
}