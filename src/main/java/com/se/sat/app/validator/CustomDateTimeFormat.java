package com.se.sat.app.validator;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDateTimeFormat extends DateFormat {
	
	
	private static final Logger log = LoggerFactory.getLogger(CustomDateTimeFormat.class);


	private static final List<? extends DateFormat> DATE_FORMATS = Arrays.asList(new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("HH:mm:ss"));

	@Override
	public StringBuffer format(final Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		throw new UnsupportedOperationException("This custom date formatter can only be used to *parse* Dates.");

	}

	@Override
	public Date parse(final String source, final ParsePosition pos) {
		Date res = null;
		for (final DateFormat dateFormat : DATE_FORMATS) {
			log.info("..dateFormat: "+dateFormat);
			if ((res = dateFormat.parse(source, pos)) != null) {
				log.info("..!null");
				return res;
			}
		}

		return null;
	}
}
