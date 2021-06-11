package br.com.bruno.biometria

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository

@Repository
interface BiometriaRepository: JpaRepository<Biometria, Long> {
}