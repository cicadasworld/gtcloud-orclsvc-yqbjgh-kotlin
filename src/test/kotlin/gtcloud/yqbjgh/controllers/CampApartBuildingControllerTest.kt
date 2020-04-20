package gtcloud.yqbjgh.controllers

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import java.io.File

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class CampApartBuildingControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `getCampApartBuildingByCampId`() {
        val result = mockMvc.perform(get("/camp-apart-building/campid/{campId}", "FID01701")).andReturn().response
        val expected = File("src/test/resources/CampApartBuildingController/camp-apart-building-campid-FID01701.txt").readText()
        assertEquals(expected, result.contentAsString)
    }
}