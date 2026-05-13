package com.project.Breakiing_Bias.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatResponse {
        private String classification;
        private int confidence;
        private List<String> facts;
        private String reason;
        private List<String> indicators;
}
