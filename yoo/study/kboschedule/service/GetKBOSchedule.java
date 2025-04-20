package study.kboschedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.kboschedule.api.GetKboScheduleAPI;
import study.kboschedule.api.SearchNaverAPI;
import study.kboschedule.model.KboSchedule;
import study.kboschedule.model.getKboScheduleRequest;
import study.kboschedule.model.getKboScheduleResult;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetKBOSchedule {

    private final GetKboScheduleAPI getKboScheduleAPI;

    public List<KboSchedule> getKBOSchedule(String text, getKboScheduleRequest request) {
        getKboScheduleResult getKboScheduleResult = getKboScheduleAPI.getKboSchedule(text, request);

        String currentDay = null;

        List<KboSchedule> kboSchedules = new ArrayList<>();

        for (study.kboschedule.model.getKboScheduleResult.RowWrapper rowWrapper : getKboScheduleResult.getRows()) {
            List<getKboScheduleResult.Cell> row = rowWrapper.getRow();

            if (row == null || row.isEmpty()) continue;

            if (row.size() > 0 && row.get(0).getRowSpan() != null) {
                currentDay = cleanHtml(row.get(0).getText());

                if (row.size() > 8) {

                    KboSchedule result = new KboSchedule();
                    result.setDay(currentDay);
                    result.setTime(cleanHtml(row.get(1).getText()));
                    result.setPlay(cleanHtml(row.get(2).getText()));
                    result.setStadiumName(cleanHtml(row.get(7).getText()));
                    kboSchedules.add(result);
                }

                continue;
            }

            if (currentDay != null && row.size() > 7) {
                KboSchedule result = new KboSchedule();
                result.setDay(currentDay);
                result.setTime(cleanHtml(row.get(0).getText()));
                result.setPlay(cleanHtml(row.get(1).getText()));
                result.setStadiumName(cleanHtml(row.get(6).getText()));
                kboSchedules.add(result);
            }
        }
        return kboSchedules;
    }

    private String cleanHtml(String text) {
        return text == null ? null : text.replaceAll("<[^>]*>", "").trim();
    }
}
