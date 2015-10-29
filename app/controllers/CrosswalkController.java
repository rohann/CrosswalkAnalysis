package controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import play.mvc.Controller;
import play.mvc.Result;

public class CrosswalkController extends Controller {

	public Result readData() throws IOException {
		Set<String> foursquareFactualIDs = new HashSet<>();
		Set<String> twitterIDs = new HashSet<>();
		File theFile = new File(
				"/Users/rohan/Downloads/us_crosswalk.factual.2015_08_25.1440511200.tab");
		LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
		try {
			while (it.hasNext()) {
				String line = it.next();
				String[] columns = line.split("\t");

				if (columns[1] != null
						&& columns[1].toLowerCase().equals("zagat")) {
					twitterIDs.add(columns[0]);
				} else if (columns[1] != null && columns[1].equals("foursquare")) {
					foursquareFactualIDs.add(columns[0]);
				}
			}

			System.out.println("Total foursquare IDs:"
					+ foursquareFactualIDs.size());
			System.out.println("Total zagat IDs:" + twitterIDs.size());

			foursquareFactualIDs.retainAll(twitterIDs);

			System.out.println("Common IDs:" + foursquareFactualIDs.size());
		} finally {
			LineIterator.closeQuietly(it);
		}

		return ok();
	}
}
