/* 
The MIT License (MIT)

Copyright (c) 2013 Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

/**
 * 
 */
package com.andreiribas.ejb;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 * 
 */
public class ResourceDependencyFinderTest {

	private static Logger LOGGER;

	private static EJBContainer ejbContainer;

	private static Context ctx;
	
	private ResourceDependencyFinder resourceDependencyFinder;
	
	@BeforeClass
	public static void setUpClass() {

		LOGGER = Logger.getLogger(ResourceDependencyFinderTest.class);

		Map<String, Object> properties = new HashMap<String, Object>();
		
		properties.put(EJBContainer.APP_NAME, "myapp");

		properties.put(EJBContainer.MODULES, new File("target/classes"));

		ejbContainer = EJBContainer.createEJBContainer(properties);

		ctx = ejbContainer.getContext();
		
	}
	
	@Before
	public void setUp() throws NamingException {
		this.resourceDependencyFinder = (ResourceDependencyFinder) ctx
				.lookup("java:global/myapp/classes/ResourceDependencyFinderBean");
	}

	@AfterClass
	public static void tearDown() {
		ejbContainer.close();
	}

	@Test
	public void testGetResource() {

		TestCase.assertNotNull(resourceDependencyFinder);

		TestCase.assertNotNull(resourceDependencyFinder.getResource());
		
		TestCase.assertEquals(ResourceDependencyFinderBeanV2.DEFAULT_MESSAGE, resourceDependencyFinder.getResource().getMessage());
		
		LOGGER.debug(String.format("Resource message is: %s.", resourceDependencyFinder.getResource().getMessage()));
		
	}
	
}
