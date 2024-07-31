package com.monjane.beprepared.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class StatsResponse {
    Long citizens;
    Long totalAlerts;
    Long activeAlerts;


}
