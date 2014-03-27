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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 * 
 */
@Stateless
public class ResourceDependencyFinderBean implements ResourceDependencyFinder {

	private Logger LOGGER;
	
	private ResourceDependencyFinderV2 v2Instance;
	
	@Resource(name = "ejb-jndi-name")
	private String ejbJndiName = "java:global/myapp/classes/ejb/ResourceDependencyFinderV2";
	
	@PostConstruct
	public void setUp() {
		
		this.LOGGER = Logger.getLogger(ResourceDependencyFinderBean.class);
	
		try {
			
			InitialContext ctx = new InitialContext();
			
			LOGGER.debug(String.format("ejbJndiName is %s.", ejbJndiName));
			
			this.v2Instance = (ResourceDependencyFinderV2) ctx.lookup(ejbJndiName);
					
		} catch(Exception e) {
			throw new EJBException(e.getMessage());
		}
		
	}

	@Override
	public ResourceDependencyFinderV2 getResource() {
		return v2Instance;
	}

}
