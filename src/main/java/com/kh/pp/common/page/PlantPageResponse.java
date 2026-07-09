package com.kh.pp.common.page;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlantPageResponse<T> {
	private List<T> content;
    private long totalElements;
    private int size;
    private double smallPlantCap;
    private double middlePlantCap;
    private double bigPlantCap;
    private double countAllPlantCap;
    
    public static <T> PlantPageResponse<T> empty(int size){
        return new PlantPageResponse<>(
            List.of(),
            0,
            size,
            0,
            0,
            0,
            0
        );
    }
    
    public PlantPageResponse(List<T> content, long totalElements, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.size = size;
    }
    
    
}
