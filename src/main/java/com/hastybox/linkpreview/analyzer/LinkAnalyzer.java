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
import com.hastybox.linkpreview.model.LinkPreview;

/**
 * Can handle given URLs and creates {@link LinkPreview} objects. The
 * {@code canProcess()} method is called first to check whether this analyzer
 * can actually process a given URL. Only if the result returns {@code true} the
 * {@code process()} method is called to create a {@link LinkPreview} object.
 * 
 * @author Patrick Sy (psy@get-it.us)
 * 
 */
public interface LinkAnalyzer {

	/**
	 * checks whether or not the given {@code url} can be processed.
	 * @param url a URL
	 * @return the result
	 */
	boolean canProcess(String url);

	/**
	 * processes the given {@code url} and creates a {@link LinkPreview}
	 * @param url a URL to process
	 * @return the resulting {@link LinkPreview}
	 * @throws LinkAnalyzerException if the {@code url} could not be processed successfully
	 */
	LinkPreview process(String url) throws LinkAnalyzerException;

}
