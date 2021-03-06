/**
 * Copyright (c) 2009 Farata Systems  http://www.faratasystems.com
 *
 * Licensed under The MIT License
 * Re-distributions of files must retain the above copyright notice.
 *
 * @license http://www.opensource.org/licenses/mit-license.php The MIT License
 *
 */
package com.farata.dto2extjs.asap;

import java.io.IOException;

import java.net.URL;
import java.util.concurrent.Callable;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;

import javax.xml.transform.TransformerException;

import javax.xml.transform.stream.StreamSource;

public abstract class XsltOperation {

	final protected Templates templates;

	protected XsltOperation(final String templatesUri) {
		this(loadTemplates(templatesUri));
	}

	protected XsltOperation(final Templates templates) {
		this.templates = templates;
	}
	
	public static Templates loadTemplates(final Class<?> clazz, final String uri) {
		final Package pkg = clazz.getPackage();
		final String path = pkg == null || pkg.getName().length() == 0 ? uri : pkg.getName()
				.replace(".", "/")
				+ "/" + uri;

		return loadTemplates(path);
	}

	public static Templates loadTemplates(final String uri) {
		final URL templateUrl = XsltOperation.class.getResource(uri);
		return withCurrentClassLoader(new Callable<Templates>() {
			public Templates call() throws Exception {
				BASE_URL.set( templateUrl );
				try {
					return TRANSFORMER_FACTORY.newTemplates( new StreamSource(templateUrl.openStream(), templateUrl.toExternalForm()) );
				} finally {
					BASE_URL.set(null);
				}
			}
		});
	}
	
	public static <T> T withCurrentClassLoader(final Callable<T> block) {
		return withCurrentClassLoader(block, XsltOperation.class.getClassLoader());
	}
	
	public static void withCurrentClassLoader(final Runnable block) {
		withCurrentClassLoader(block, XsltOperation.class.getClassLoader());
	}

	
	public static void withCurrentClassLoader(final Runnable block, final ClassLoader classLoader) {
		withCurrentClassLoader(new Callable<Void>() {
			public Void call() {
				block.run();
				return null;
			}
		}, classLoader);
	}
	
	public static <T> T withCurrentClassLoader(final Callable<T> block, final ClassLoader classLoader) {
		//Try to execute without calling of setContextClassLoader
		try {
			return block.call();
		} catch (Exception e) {
		}
		final Thread currentThread = Thread.currentThread();
		final ClassLoader currentContextClassLoader = currentThread.getContextClassLoader();
		currentThread.setContextClassLoader(classLoader);
		try {
			return block.call();
		} catch (final Error ex) {
			throw ex;
		} catch (final RuntimeException ex) {
			throw ex;
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			currentThread.setContextClassLoader(currentContextClassLoader);
		}
	}
	
	static String composeClassName(final String mainClassName, final String targetClassName) {
		final int lastDot = mainClassName.lastIndexOf('.');
		if (lastDot > 0)
			return mainClassName.substring(0, lastDot + 1) + targetClassName;
		else
			return targetClassName;
	}

	final private static ThreadLocal<URL> BASE_URL = new ThreadLocal<URL>();
	final protected static TransformerFactory TRANSFORMER_FACTORY;
	static {
//		TRANSFORMER_FACTORY = TransformerFactory.newInstance("org.apache.xalan.xsltc.trax.TransformerFactoryImpl", XsltOperation.class.getClassLoader());
//		TRANSFORMER_FACTORY = TransformerFactory.newInstance("com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl", XsltOperation.class.getClassLoader());
		TRANSFORMER_FACTORY = TransformerFactory.newInstance();
		TRANSFORMER_FACTORY.setURIResolver(new URIResolver() {
			public Source resolve(final String href, final String base) throws TransformerException {
				try {
					final URL realBase = BASE_URL.get();
					if (null == realBase) {
						final String message = "Coding error, thread local for base url not set";
						System.err.println(message);
						throw new IllegalStateException(message);
					}
					final URL related = new URL(realBase, href);
					return new StreamSource( related.openStream(), related.toExternalForm() );
				} catch (final IOException ex) {
					throw new TransformerException(ex);
				}
			}
		});
	}
}

