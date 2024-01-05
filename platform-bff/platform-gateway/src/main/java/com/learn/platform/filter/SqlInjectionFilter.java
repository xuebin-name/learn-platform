package com.learn.platform.filter;

import com.learn.platform.util.XssFilterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

/**
 * @ClassName SqlInjectionFilter
 * @Description 防止xss 攻击过滤器
 * @Author xue
 * @Date 2024/1/5 14:41
 */
@Slf4j
@Component
public class SqlInjectionFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if(XssFilterUtils.isIgnore(request.getHeaders())) {
            return chain.filter(exchange);
        }

        return DataBufferUtils.join(request.getBody())
                .flatMap(d -> Mono.just(Optional.of(d))).defaultIfEmpty(Optional.empty())
                .flatMap(optional -> {
                    try {
                        // URI(path & query) XSS清理
                        URI uri = XssFilterUtils.uriXssClean(request.getURI());

                        // Header XSS清理
                        HttpHeaders headers = XssFilterUtils.headerXssClean(request.getHeaders());

                        // Body XSS清理
                        byte[] bodyBytes = null;
                        if (optional.isPresent()) {
                            byte[] oldBytes = new byte[optional.get().readableByteCount()];
                            optional.get().read(oldBytes);
                            bodyBytes = XssFilterUtils.bodyXssClean(request.getHeaders(), oldBytes);
                        }

                        // 无Body请求重写
                        if (ArrayUtils.isEmpty(bodyBytes)) {
                            return chain.filter(exchange.mutate().request(new ServerHttpRequestDecorator(request.mutate().uri(uri).build()) {
                                @Override
                                public HttpHeaders getHeaders() {
                                    return headers;
                                }
                            }).build());
                        }

                        // 有Body请求重写
                        if (!ArrayUtils.isEmpty(bodyBytes)) {
                            XssFilterUtils.resetContentLength(headers, bodyBytes.length);
                        }

                        final byte[] finalBodyBytes = bodyBytes;
                        return chain.filter(exchange.mutate().request(new ServerHttpRequestDecorator(request.mutate().uri(uri).build()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                assert finalBodyBytes != null;
                                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(finalBodyBytes);
                                DataBufferUtils.retain(buffer);

                                return Flux.just(buffer);
                            }

                            @Override
                            public HttpHeaders getHeaders() {
                                return headers;
                            }
                        }).build());
                    } catch (Exception ex) {
                        log.error("XssRequestFilter.filter() error!", ex);
                        return chain.filter(exchange);
                    } finally {
                        optional.ifPresent(DataBufferUtils::release);
                    }
                });
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
