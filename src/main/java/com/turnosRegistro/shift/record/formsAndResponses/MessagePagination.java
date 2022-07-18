package com.turnosRegistro.shift.record.formsAndResponses;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MessagePagination {
    @JsonProperty("content")
    private List<Object> content;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("nextPath")
    private String nextPath;
    @JsonProperty("prevPath")
    private String prevPath;
}
