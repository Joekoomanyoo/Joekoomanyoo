package com.project.common.service;

import com.project.common.dto.HeritageDto;
import com.project.common.dto.HeritageMapper;
import com.project.common.entity.HeritageEntity;
import com.project.common.repository.HeritageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HeritageService{

    private final HeritageRepository heritageRepository;


    @Transactional
    public List<HeritageDto> listInfo() throws Exception{
        List<HeritageDto> list = new ArrayList<>();
        for(HeritageEntity entity : heritageRepository.findAll()){
            list.add(HeritageMapper.MAPPER.toDto(entity));
        }
        return list;
    }
}
