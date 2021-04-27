/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.geode.server.wan.event.server;

import lombok.extern.apachecommons.CommonsLog;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;

import org.apache.geode.cache.wan.GatewayTransportFilter;

import org.springframework.stereotype.Component;

/**
 * @author Patrick Johnson
 */
@Component
@CommonsLog
public class WanTransportEncryptionListener implements GatewayTransportFilter {

	private final Adler32 CHECKER = new Adler32();

	@Override
	public InputStream getInputStream(InputStream stream) {
		log.info("CheckedTransportFilter: Getting input stream");
		return new CheckedInputStream(stream, CHECKER);
	}

	@Override
	public OutputStream getOutputStream(OutputStream stream) {
		log.info("CheckedTransportFilter: Getting output stream");
		return new CheckedOutputStream(stream, CHECKER);
	}
}
