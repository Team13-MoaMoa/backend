package sku.moamoa.fixture;

import sku.moamoa.domain.post.dto.TechStackDto;

import java.util.ArrayList;
import java.util.List;

public class TechStackFixtures {

    public static final TechStackDto.InfoResponse[] techStackArray = new TechStackDto.InfoResponse[] {
            TechStackDto.InfoResponse.builder()
                    .id(1L)
                    .build(),
            TechStackDto.InfoResponse.builder()
                    .id(2L)
                    .build(),
            TechStackDto.InfoResponse.builder()
                    .id(3L)
                    .build(),
    };
}
