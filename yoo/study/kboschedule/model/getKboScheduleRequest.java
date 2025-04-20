package study.kboschedule.model;

import lombok.Data;

import java.util.List;

@Data
public class getKboScheduleRequest {

    private int leId;
    private List<Long> srIdList;
    private int seasonId;
    private String gameMonth;
    private String teamId;
}