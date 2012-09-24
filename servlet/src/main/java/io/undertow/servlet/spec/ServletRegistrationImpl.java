/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.undertow.servlet.spec;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;

import io.undertow.servlet.api.ServletInfo;

/**
 * @author Stuart Douglas
 */
public class ServletRegistrationImpl implements ServletRegistration, ServletRegistration.Dynamic {

    private final ServletInfo servletInfo;

    public ServletRegistrationImpl(final ServletInfo servletInfo) {
        this.servletInfo = servletInfo;
    }

    @Override
    public void setLoadOnStartup(final int loadOnStartup) {
        servletInfo.setLoadOnStartup(loadOnStartup);
    }

    @Override
    public Set<String> setServletSecurity(final ServletSecurityElement constraint) {
        return null;
    }

    @Override
    public void setMultipartConfig(final MultipartConfigElement multipartConfig) {
        servletInfo.setMultipartConfig(multipartConfig);
    }

    @Override
    public void setRunAsRole(final String roleName) {
        servletInfo.setRunAs(roleName);
    }

    @Override
    public void setAsyncSupported(final boolean isAsyncSupported) {
        servletInfo.setAsyncSupported(isAsyncSupported);
    }

    @Override
    public Set<String> addMapping(final String... urlPatterns) {
        final Set<String> ret = new HashSet<String>();
        for(String pattern : urlPatterns) {
            if(servletInfo.getMappings().contains(pattern)) {
                ret.add(pattern);
                servletInfo.addMapping(pattern);
            }
        }
        return ret;
    }

    @Override
    public Collection<String> getMappings() {
        return servletInfo.getMappings();
    }

    @Override
    public String getRunAsRole() {
        return servletInfo.getRunAs();
    }

    @Override
    public String getName() {
        return servletInfo.getName();
    }

    @Override
    public String getClassName() {
        return servletInfo.getServletClass().getName();
    }


    @Override
    public boolean setInitParameter(final String name, final String value) {
        if (servletInfo.getInitParams().containsKey(name)) {
            return false;
        }
        servletInfo.addInitParam(name, value);
        return true;
    }

    @Override
    public String getInitParameter(final String name) {
        return servletInfo.getInitParams().get(name);
    }

    @Override
    public Set<String> setInitParameters(final Map<String, String> initParameters) {
        final Set<String> ret = new HashSet<String>();
        for (Map.Entry<String, String> entry : initParameters.entrySet()) {
            if (!setInitParameter(entry.getKey(), entry.getValue())) {
                ret.add(entry.getKey());
            }
        }
        return ret;
    }

    @Override
    public Map<String, String> getInitParameters() {
        return servletInfo.getInitParams();
    }
}