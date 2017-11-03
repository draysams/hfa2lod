/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.csv.hfa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.ld4l.bib2lod.csv.AbstractConcordanceManager;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

public class LanguageConcordanceManager extends AbstractConcordanceManager<LanguageConcordanceBean> {
	
	private enum ConcordanceCsvColumn {
		
		MATCHING_TEXT("matchingText"),
		LABEL("label"),
		URI("uri");
		
		private String columnName;
		
		private ConcordanceCsvColumn(String columnName) {
			this.columnName = columnName;
		}
		
		/**
		 * Must match getters/setters for JavaBean reflection used by OpenCSV.
		 */
		public String toString() {
			return columnName;
		}
		
	}
	
	private static final String CONCORANCE_FILE_NAME = "/language.csv";
	
    /**
     * Constructor which loads default CSV file.
     * 
	 * @throws FileNotFoundException - If file not found on classpath.
     */
	public LanguageConcordanceManager() throws URISyntaxException, IOException {
		this(CONCORANCE_FILE_NAME);
	}
	
	/**
	 * This constructor can be used for unit tests.
	 * 
	 * @param fileName - Name of CSV file in classpath to load.
	 * @throws URISyntaxException 
	 * @throws FileNotFoundException - If file not found on classpath.
	 */
	public LanguageConcordanceManager(String fileName) throws URISyntaxException, IOException {
		super(fileName);
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#initBeanMap(com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy, com.opencsv.CSVReader)
	 */
	@Override
	protected Map<String, LanguageConcordanceBean> initBeanMap(
			HeaderColumnNameTranslateMappingStrategy<LanguageConcordanceBean> strat,
			CSVReader reader) {
		
	    CsvToBean<LanguageConcordanceBean> csv = new CsvToBean<>();
	    List<LanguageConcordanceBean> list = csv.parse(strat, reader);
	    // populate local map of keyword name to Bean
	    Map<String, LanguageConcordanceBean> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); // could be looking up with keys in mixed case
	    for (LanguageConcordanceBean item : list) {
	    	map.put(item.getMatchingText(), item);
	    }
	    return map;
	}

	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#getCsvColumnEnums()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List<Enum> getCsvColumnEnums() {
		List<Enum> list = new ArrayList<>();
		for (ConcordanceCsvColumn val : ConcordanceCsvColumn.values()) {
			list.add(val);
		}
		return list;
	}
	
	/**
	 * @see org.ld4l.bib2lod.csv.AbstractConcordanceManager#getBeanClass()
	 */
	@Override
	protected Class<LanguageConcordanceBean> getBeanClass() {
		return LanguageConcordanceBean.class;
	}
	
}