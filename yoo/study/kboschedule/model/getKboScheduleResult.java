package study.kboschedule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class getKboScheduleResult {

    private List<Object> colgroup;
    private List<Object> headers;
    private List<RowWrapper> rows;
    private List<Object> tfoot;

    private String totalCnt;
    private String headerClass;
    private String tbodyClass;
    private String tfootClass;
    private String title;
    private String caption;
    private String result_cd;
    private String result_msg;
    private String code;
    private String msg;

    @Data
    public static class RowWrapper {
        @JsonProperty("OnClick")
        private String onClick;

        @JsonProperty("Class")
        private String className;

        @JsonProperty("Style")
        private String style;

        @JsonProperty("Value")
        private String value;

        @JsonProperty("Id")
        private String id;

        @JsonProperty("row")
        private List<Cell> row;
    }

    @Data
    public static class Cell {
        @JsonProperty("Text")
        private String text;

        @JsonProperty("Class")
        private String className;

        @JsonProperty("Scope")
        private String scope;

        @JsonProperty("RowSpan")
        private String rowSpan;

        @JsonProperty("ColSpan")
        private String colSpan;

        @JsonProperty("Width")
        private String width;

        @JsonProperty("TypeObj")
        private String typeObj;
    }
}
