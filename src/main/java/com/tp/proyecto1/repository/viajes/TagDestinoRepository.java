package com.tp.proyecto1.repository.viajes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tp.proyecto1.model.viajes.TagDestino;

@Repository
public interface TagDestinoRepository extends JpaRepository<TagDestino, Long> {


}
