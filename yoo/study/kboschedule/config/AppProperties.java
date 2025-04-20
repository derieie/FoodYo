package study.kboschedule.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Naver naver;

    @Data
    public static class Naver {
        private Api api;

        @Data
        public static class Api {
            private String clientId;
            private String clientSecret;
        }
    }
}
