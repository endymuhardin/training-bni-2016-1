package com.brainmatics.pelatihan.dao;

import com.brainmatics.pelatihan.entity.HasilTest;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HasilTestDao extends PagingAndSortingRepository<HasilTest, String> {

    public Iterable<HasilTest> findByPeserta(String peserta);
    
}
