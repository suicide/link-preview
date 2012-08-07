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
import org.jsoup.select.Elements;

import com.hastybox.linkpreview.common.LinkAnalyzerException;
import com.hastybox.linkpreview.model.LinkPreview;
import com.hastybox.linkpreview.model.factory.LinkPreviewFactory;

/**
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public class JsoupHtmlHandler implements HtmlHandler {
	
	private LinkPreviewFactory linkPreviewFactory;
	
	/**
	 * @param linkPreviewFactory the linkPreviewFactory to set
	 */
	public void setLinkPreviewFactory(LinkPreviewFactory linkPreviewFactory) {
		this.linkPreviewFactory = linkPreviewFactory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.analyzer.generic.HtmlHandler#process(java.io
	 * .InputStream, java.lang.String, java.lang.String)
	 */
	public LinkPreview process(InputStream htmlDataStream, String encoding,
			String baseUrl) throws LinkAnalyzerException {
		Document document;

		try {
			document = Jsoup.parse(htmlDataStream, encoding, baseUrl);
		} catch (IOException e) {
			throw new LinkAnalyzerException(e);
		}

		LinkPreview preview = linkPreviewFactory.create();
		
		// set data to preview
		Elements title = document.getElementsByTag("title");
		
		return preview;
	}

}
