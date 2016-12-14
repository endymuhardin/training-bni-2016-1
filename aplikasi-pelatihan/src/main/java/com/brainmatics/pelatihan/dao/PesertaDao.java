package com.brainmatics.pelatihan.dao;

import com.brainmatics.pelatihan.entity.Peserta;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PesertaDao extends PagingAndSortingRepository<Peserta, String>{
    
}
