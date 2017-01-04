package com.brainmatics.pelatihan.dao;

import com.brainmatics.pelatihan.entity.Kelas;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface KelasDao extends PagingAndSortingRepository<Kelas, String>{
    
}
