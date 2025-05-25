package com.example.demo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.util.StringUtils;
import java.util.Enumeration;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // リクエストをラップ
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        // レスポンス内容のログ出力（簡易版: ステータスのみ）
        try {
            filterChain.doFilter(wrappedRequest, response);
        } finally {
            logger.info("[Request] {} {}", request.getMethod(), request.getRequestURI());
            request.getParameterMap().forEach((k, v) -> logger.info("[RequestParam] {}={}", k, String.join(",", v)));

            String contentType = request.getContentType();
            if (contentType != null && contentType.startsWith("multipart/form-data")) {
                // multipartの場合はファイル名・サイズのみ出力
                Enumeration<String> partNames = request.getParameterNames();
                while (partNames.hasMoreElements()) {
                    String name = partNames.nextElement();
                    logger.info("[MultipartParam] {}={}", name, request.getParameter(name));
                }
                // ファイル情報はMultipartControllerで処理されるため、ここでは省略
            } else {
                byte[] buf = wrappedRequest.getContentAsByteArray();
                if (buf.length > 0) {
                    String body = new String(buf, wrappedRequest.getCharacterEncoding());
                    logger.info("[RequestBody] {}", StringUtils.truncate(body, 200));
                }
            }
            logger.info("[Response] status={}", response.getStatus());
        }
    }
} 