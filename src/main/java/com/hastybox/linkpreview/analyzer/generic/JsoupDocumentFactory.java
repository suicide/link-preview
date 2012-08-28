/* Copyright 2011-2012 The Cellador Hastybox Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hastybox.linkpreview.analyzer.generic;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Class to wrap fugly static call to Jsoup
 * 
 * @author Patrick Sy (psy@get-it.us)
 *
 */
public class JsoupDocumentFactory {
	
	/**
	 * parses HTML InputStream. It hides the static call to Jsoup.
	 * 
	 * @param htmlDataStream
	 * @param encoding
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public Document retrieveDocument(InputStream htmlDataStream, String encoding,
			String url) throws IOException {
		
		return Jsoup.parse(htmlDataStream, encoding, url);
	}

}
