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

import java.util.List;

import com.hastybox.linkpreview.analyzer.ShortUrlResolver;
import com.hastybox.linkpreview.common.LinkAnalyzerException;

/**
 * Wraps a given {@link ShortUrlResolver}. It matches the given URL with a given
 * {@link List} of hostnames of short URL provides. Only if a match is found the
 * wrapped {@link ShortUrlResolver} instance is used to resolve the short URL.
 * 
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public class HostnameMappedShortUrlResolverWrapper implements ShortUrlResolver {

	private ShortUrlResolver shortUrlResolver;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hastybox.linkpreview.analyzer.ShortUrlTracer#trace(java.lang.String)
	 */
	public String trace(String url) throws LinkAnalyzerException {
		// TODO Auto-generated method stub
		return null;
	}

}
