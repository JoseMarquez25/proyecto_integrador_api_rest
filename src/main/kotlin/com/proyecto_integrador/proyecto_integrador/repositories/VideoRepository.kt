package com.proyecto_integrador.repositories

import com.proyecto_integrador.models.entities.VideoEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoRepository : JpaRepository<VideoEntity, Long>
