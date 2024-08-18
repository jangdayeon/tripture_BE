package com.photoChallenger.tripture.domain.point.dto;

import com.photoChallenger.tripture.domain.point.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PointDto {
    private String pointTitle;
    private LocalDate pointDate;
    private String pointChange;

    public static PointDto from(Point point){
        return new PointDto(point.getPointTitle(),point.getPointDate(),point.getPointChange());
    }
}
