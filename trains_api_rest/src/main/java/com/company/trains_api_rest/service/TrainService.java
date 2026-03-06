package com.company.trains_api_rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.trains_api_rest.dtos.train_dto.TrainCreateRequest;
import com.company.trains_api_rest.dtos.train_dto.TrainResponse;
import com.company.trains_api_rest.dtos.train_dto.TrainUpdateRequest;
import com.company.trains_api_rest.exception.ResourceNotFoundException;
import com.company.trains_api_rest.model.Train;
import com.company.trains_api_rest.model.TrainType;
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

    public TrainResponse patchTrain(Long id, TrainUpdateRequest req) {
        Train t = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tren no encontrado"+id));

        if(req.getName() != null){
            t.setName(req.getName());
        }

        if(req.getCapacity() != null){
            t.setCapacity(req.getCapacity());
        }

        if(req.getType() != null){
            t.setType(req.getType());
        }

        return toResponse(t);
    }

    public void deleteTrain(Long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Tren no encontrado: "+id);
        }
    }

    public List<TrainResponse> listType(TrainType type){
        var data = (type == null) ? repo.findAll() : repo.findByType(type);
        return data.stream().map(this::toResponse).toList();
    }

}
