package commf.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/*
 크로스도메인 정책 허용 설정
 * @ by ebi
 */

public class CorsDomainFilter extends  OncePerRequestFilter {

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    		throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");

        // Http Header Untrusted - 18.08.01
        // [조치] - CORS(Cross Origin Resource Sharing)을 위해 읽는 Header임. 
        //          클라이언트에서 스크립트를 통해 Cros site HTTP request가 가능하게 하기 위해 정해진 권고사항 임
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())); {

            // CORS "pre-flight" request
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Max-Age", "1728000");
        }

        filterChain.doFilter(request, response);
    }
}
