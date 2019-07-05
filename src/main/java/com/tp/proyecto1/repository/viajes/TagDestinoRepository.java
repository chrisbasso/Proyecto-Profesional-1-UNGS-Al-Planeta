package com.tp.proyecto1.repository.viajes;

import com.tp.proyecto1.model.viajes.TagDestino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDestinoRepository extends JpaRepository<TagDestino, Long> {


}
