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
package com.hastybox.linkpreview.analyzer;

import com.hastybox.linkpreview.common.LinkAnalyzerException;

/**
 * Follows redirects of short URLs like bit.ly and resolves the actually linked URL
 * 
 * @author Patrick Sy (psy@get-it.us)
 *
 */
public interface ShortUrlResolver {
	
	/**
	 * traces the given short {@code url} and follows its redirects to the actually linked content
	 * @param url the url to follow
	 * @return the resolved URL or the given {@code url} if the given url was no short url
	 * @throws LinkAnalyzerException if something went wrong
	 */
	String trace(String url) throws LinkAnalyzerException;

}
