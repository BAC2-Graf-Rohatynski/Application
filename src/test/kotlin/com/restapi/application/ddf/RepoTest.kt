package com.restapi.application.ddf


import com.restapi.application.database.ManufacturerRepository
import com.restapi.application.manufacturer.ManufacturerEntity
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class RepoTest(val entityManager: TestEntityManager, val manu: ManufacturerRepository){

    @Test
    fun `When findByIdOrNull then return Manufacturer`(){
        val man = ManufacturerEntity()
        man.setName("Test Manufacturer")
        entityManager.persist(man)

    }


}