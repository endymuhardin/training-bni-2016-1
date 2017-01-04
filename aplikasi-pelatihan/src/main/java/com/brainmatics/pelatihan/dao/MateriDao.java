package com.brainmatics.pelatihan.dao;

import com.brainmatics.pelatihan.entity.Materi;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MateriDao extends PagingAndSortingRepository<Materi, String>{
    
}
