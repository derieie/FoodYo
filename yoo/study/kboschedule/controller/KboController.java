package study.kboschedule.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.kboschedule.model.KboSchedule;
import study.kboschedule.model.getKboScheduleRequest;
import study.kboschedule.model.getKboScheduleResult;
import study.kboschedule.service.GetKBOSchedule;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KboController {

    private final GetKBOSchedule getKBOSchedule;

    @PostMapping("/v1/KboSchedule/{text}")
    public List<KboSchedule> getKboSchedule(@ModelAttribute getKboScheduleRequest request, @PathVariable String text) {
        return getKBOSchedule.getKBOSchedule(text, request);
    }
}
