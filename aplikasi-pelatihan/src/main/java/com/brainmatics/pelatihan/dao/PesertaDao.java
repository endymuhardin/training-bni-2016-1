package com.brainmatics.pelatihan.dao;

import com.brainmatics.pelatihan.entity.Peserta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PesertaDao extends PagingAndSortingRepository<Peserta, String>{
    public Page<Peserta> findByNamaContaining(String keyword, Pageable page);
}
