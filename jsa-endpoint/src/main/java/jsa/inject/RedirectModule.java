/**
 * 
 */
package jsa.inject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import com.google.inject.servlet.ServletModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Slf4j
public class RedirectModule extends ServletModule {

    private List<Redirect> redirects = new LinkedList<Redirect>();

    public RedirectModule with(String from, String to) {
        redirects.add(new Redirect(from, to, false));
        return this;
    }

    public RedirectModule with(String from, String to, boolean wildcard) {
        redirects.add(new Redirect(from, to, wildcard));
        return this;
    }

    @AllArgsConstructor
    @Data
    private static class Redirect {
        private String from;
        private String to;
        private boolean wildcard;
    }

    @AllArgsConstructor
    @Slf4j
    private static class RedirectFilter implements Filter {
        private String to;

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            log.info(String.format("Redirecting %s to %s", req.getRequestURL(), to));

            req.getRequestDispatcher(to).forward(req, resp);
        }

        @Override
        public void destroy() {
        }
    }

    @AllArgsConstructor
    private class WildcardRedirectFilter implements Filter {
        private String from;
        private String to;

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            log.info(String.format("Redirecting %s to %s",
                    req.getRequestURL(), to));

            String uri = req.getRequestURI();
            uri = uri.replaceFirst(from, "");

            req.getRequestDispatcher(to + uri).forward(req, resp);
        }

        @Override
        public void destroy() {
        }
    }

    @Override
    protected void configureServlets() {
        log.info(redirects.toString());
        for (Redirect r : redirects) {
            if (r.isWildcard()) {
                WildcardRedirectFilter wrf = new WildcardRedirectFilter(r.getFrom(), r.getTo());
                filter(r.getFrom() + "*").through(wrf);
            }
            else {
                RedirectFilter rf = new RedirectFilter(r.getTo());
                filter(r.getFrom()).through(rf);
            }
        }
    }
}
