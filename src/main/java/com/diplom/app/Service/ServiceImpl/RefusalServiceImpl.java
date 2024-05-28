package com.diplom.app.Service.ServiceImpl;

import com.diplom.app.Entity.Refusal;
import com.diplom.app.Repository.RefusalRepository;
import com.diplom.app.Service.RefusalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefusalServiceImpl implements RefusalService {

    private final RefusalRepository refusalRepository;

    @Autowired
    public RefusalServiceImpl(RefusalRepository refusalRepository) {
        this.refusalRepository = refusalRepository;
    }

    public List<Refusal> findAll(){
        return refusalRepository.findAll();
    }

    public Refusal findById(Long id){
        return refusalRepository.findById(id).get();
    }
}
