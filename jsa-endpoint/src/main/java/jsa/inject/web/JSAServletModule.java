/*
 * Copyright (C) 2013 <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package jsa.inject.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;

import org.apache.cxf.Bus;

import com.google.inject.servlet.ServletModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
public class JSAServletModule extends ServletModule {

    protected String context;
    protected Bus bus;
    protected JSAServlet servlet;
    protected Map<String, Filter> filters = new HashMap<String, Filter>();

    public JSAServletModule(String context, Bus bus) {
        this.context = context;
        this.bus = bus;
        servlet = new JSAServlet(bus, context);
    }

    public JSAServletModule addFilter(String path, Filter filter) {
        filters.put(path, filter);
        return this;
    }

    @Override
    protected void configureServlets() {
        serve(context + "/*").with(servlet);
        for (Entry<String, Filter> e : filters.entrySet()) {
            filter(e.getKey()).through(e.getValue());
        }
    }

}
