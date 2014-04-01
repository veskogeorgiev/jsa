/**
 * 
 */
package jsa.inject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.google.inject.servlet.ServletModule;

/**
 * 
 * @author <a href="mailto:vesko.georgiev@uniscon.de">Vesko Georgiev</a>
 */
@Slf4j
public class RedirectModule extends ServletModule {

    private Map<String, String> sourceDestinationMapping = new HashMap<String, String>();

    public RedirectModule() {
        //
    }

    public RedirectModule(String from, String to) {
        fromTo(from, to);
    }

    public RedirectModule fromTo(String from, String to) {
        sourceDestinationMapping.put(from, to);
        return this;
    }

    @AllArgsConstructor
    @Slf4j
    private static class RedirectFilter implements Filter {
        private String destinationUrl;

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            log.info(String.format("Redirecting %s to %s", req.getRequestURL(), destinationUrl));

            req.getRequestDispatcher(destinationUrl).forward(req, resp);
        }

        @Override
        public void destroy() {
        }
    }

    @Override
    protected void configureServlets() {
        log.info(sourceDestinationMapping.toString());
        for (Entry<String, String> e : sourceDestinationMapping.entrySet()) {
            RedirectFilter rf = new RedirectFilter(e.getValue());
            filter(e.getKey()).through(rf);
        }
    }
}
