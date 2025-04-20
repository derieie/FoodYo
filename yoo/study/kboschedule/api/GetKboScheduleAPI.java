package study.kboschedule.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import study.kboschedule.model.getKboScheduleRequest;
import study.kboschedule.model.getKboScheduleResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GetKboScheduleAPI {

    @Qualifier("KboWebClient")
    private final WebClient kboScheduleWebClient;

    public getKboScheduleResult getKboSchedule(String text, getKboScheduleRequest request) {

        MultiValueMap<String, String> bodyRequest = getStringStringMultiValueMap(request);

        String rawResponse = kboScheduleWebClient.post()
                .uri(text)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData(bodyRequest))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String cleanJson = rawResponse
                .replaceAll("(?s)<string[^>]*>", "")  // <string ...>
                .replaceAll("</string>", "")          // </string>
                .trim();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(cleanJson, getKboScheduleResult.class);
        } catch (Exception e) {
            throw new RuntimeException("응답 파싱 실패: " + e.getMessage(), e);
        }
    }

    private static MultiValueMap<String, String> getStringStringMultiValueMap(getKboScheduleRequest request) {
        MultiValueMap<String, String> bodyRequest = new LinkedMultiValueMap<>();
        bodyRequest.add("leId", String.valueOf(request.getLeId()));
        bodyRequest.add("srIdList", request.getSrIdList().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")));
        bodyRequest.add("seasonId", String.valueOf(request.getSeasonId()));
        bodyRequest.add("gameMonth", request.getGameMonth());
        bodyRequest.add("teamId", request.getTeamId() != null ? request.getTeamId() : "");
        return bodyRequest;
    }
}
