package com.brainmatics.pelatihan.dao;

import com.brainmatics.pelatihan.entity.Instruktur;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InstrukturDao extends PagingAndSortingRepository<Instruktur, String>{
    
}
