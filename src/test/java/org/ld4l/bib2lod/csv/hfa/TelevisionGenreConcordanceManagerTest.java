package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TelevisionGenreConcordanceManagerTest {

	private static final String TEST_CSV_FILE = "/test_television_genre.csv";
    private static final Logger LOGGER = LogManager.getLogger();

	@Test
	public void readFileIntoManager() {
		try {
			new TelevisionGenreConcordanceManager(TEST_CSV_FILE);
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

	@Test(expected=FileNotFoundException.class)
	public void readMissingFileIntoManager() throws URISyntaxException, IOException {
		new FilmGenreConcordanceManager("/missing-file.csv");
		Assert.fail("File should be missing and exception thrown");
	}
	
	@Test
	public void readValidTestGenreData() {
		try {
			TelevisionGenreConcordanceManager mgr = new TelevisionGenreConcordanceManager(TEST_CSV_FILE);
			Map<String, TelevisionGenreConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(3, map.size());
			
			TelevisionGenreConcordanceBean bean = mgr.getConcordanceEntry("TEST_TAG");
			Assert.assertNotNull(bean);
			List<String> testValues = bean.getExternalUris();
			Assert.assertEquals("TEST_TAG", bean.getHfaTag());
			Assert.assertEquals("LC1", bean.getLocGenreForm1());
			Assert.assertTrue(testValues.contains("LC1"));
			Assert.assertEquals("LC2", bean.getLocGenreForm2());
			Assert.assertTrue(testValues.contains("LC2"));
			Assert.assertEquals("LC3", bean.getLocGenreForm3());
			Assert.assertTrue(testValues.contains("LC3"));
			Assert.assertEquals("FAST1", bean.getFastForm1());
			Assert.assertTrue(testValues.contains("FAST1"));
			Assert.assertEquals("FAST2", bean.getFastForm2());
			Assert.assertTrue(testValues.contains("FAST2"));
			Assert.assertEquals("FAST3", bean.getFastForm3());
			Assert.assertTrue(testValues.contains("FAST3"));
			Assert.assertEquals("GETTY", bean.getGettyGenre());
			Assert.assertTrue(testValues.contains("GETTY"));
			Assert.assertEquals("ONTCLASS", bean.getMovingImageOntClass());

		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void readMissinGenreData() {
		try {
			TelevisionGenreConcordanceManager mgr = new TelevisionGenreConcordanceManager(TEST_CSV_FILE);
			Map<String, TelevisionGenreConcordanceBean> map = mgr.getMap();
			Assert.assertNotNull(map);
			Assert.assertFalse(map.isEmpty());
			Assert.assertEquals(3, map.size());
			
			TelevisionGenreConcordanceBean bean = mgr.getConcordanceEntry("not-in-concordance");
			Assert.assertNull(bean);
			
		} catch (Exception e) {
			LOGGER.error(e, e);
			Assert.fail(e.getMessage());
		}
	}

}