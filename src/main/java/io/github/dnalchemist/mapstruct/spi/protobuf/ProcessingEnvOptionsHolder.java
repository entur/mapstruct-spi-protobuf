package io.github.dnalchemist.mapstruct.spi.protobuf;

/*-
 * #%L
 * protobuf-spi-impl
 * %%
 * Copyright (C) 2019 - 2021 Entur
 * %%
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 * 
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl5
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * #L%
 */

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.google.common.collect.ImmutableMap;

/**
 * This is not a true processor. It merely exists to pass the defined supported options to a global context that is accessible by the MapStruct classes which
 * would otherwise not have visibility to these.
 */
@SupportedAnnotationTypes({})
@SupportedOptions({ ProcessingEnvOptionsHolder.ENUM_POSTFIX_OVERRIDES })
public class ProcessingEnvOptionsHolder extends AbstractProcessor {

	static final String ENUM_POSTFIX_OVERRIDES = "mapstructSpi.enumPostfixOverrides";

	private static Map<String, String> OPTIONS;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		OPTIONS = ImmutableMap.copyOf(processingEnv.getOptions());
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		return false;
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	static boolean containsKey(String key) {
		if (OPTIONS == null) {
			throw new IllegalStateException("ProcessingEnvOptionsHolder not initialized yet.");
		}
		return OPTIONS.containsKey(key);
	}

	static String getOption(String key) {
		if (OPTIONS == null) {
			throw new IllegalStateException("ProcessingEnvOptionsHolder not initialized yet.");
		}
		return OPTIONS.get(key);
	}
}
