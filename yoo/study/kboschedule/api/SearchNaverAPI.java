package study.kboschedule.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import study.kboschedule.config.AppProperties;

@RequiredArgsConstructor
@Component
public class SearchNaverAPI {

    private final AppProperties appProperties;
    private final WebClient naverWebClient;

    public String newsSearch(String text) {
        return naverWebClient.get()
                .uri("/search/news?query="+text)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
