package com.company.trains_api_rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.trains_api_rest.dtos.train_dto.TrainCreateRequest;
import com.company.trains_api_rest.dtos.train_dto.TrainResponse;
import com.company.trains_api_rest.exception.ResourceNotFoundException;
import com.company.trains_api_rest.model.Train;
import com.company.trains_api_rest.repository.TrainRepository;

@Service
public class TrainService {

    private final TrainRepository repo;

    public TrainService(TrainRepository repo) {
        this.repo = repo;
    }
    
    private TrainResponse toResponse(Train t){
        return new TrainResponse(t.getId(), t.getName(), t.getCapacity(), t.getType());
    }

    public TrainResponse createTrain(TrainCreateRequest req) {
        Train t = new Train();
        t.setName(req.getName());
        t.setCapacity(req.getCapacity());
        t.setType(req.getType());
        return toResponse(repo.save(t));
    }

    public List<TrainResponse> listTrain() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    public TrainResponse getTrain(Long id){
        Train t = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tren no encontrado: "+id));
        return toResponse(t);
    }

    public TrainResponse updateTrain(Long id, TrainCreateRequest req){
        Train t = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tren no encontrado"+id));

        t.setName(req.getName());
        t.setCapacity(req.getCapacity());
        return toResponse(repo.save(t));
    }

    public void deleteTrain(Long id){
        if(!repo.existsById(id)){
            throw new ResourceNotFoundException("Tren no encontrado: "+id);
        }
        repo.deleteById(id);
    }

}
