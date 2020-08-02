package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.example.demo.entity.Work;
import com.example.demo.enums.Status;
import com.example.demo.service.impl.WorkServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WorkControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private WorkServiceImpl workServiceImpl;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testPaginationWorks() {
        int pageNumber = 0;
        int pageSize = 1;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Work work = new Work();
        work.setName("work_01");

        Page<Work> workPage = new PageImpl<>(Collections.singletonList(work));

        when(workServiceImpl.getAllWorks(pageable)).thenReturn(workPage);
        Page<Work> works = workServiceImpl.getAllWorks(pageable);
        assertEquals(works.getNumberOfElements(), 1);
    }

    @Test
    public void testGetAllWorks() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/works", HttpMethod.GET, entity,
                String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetWorkById() {
        Work work = restTemplate.getForObject(getRootUrl() + "/works/1", Work.class);
        System.out.println(work.getName());
        assertNotNull(work);
    }

    @Test
    public void testCreateWork() {
        Work work = new Work();
        work.setName("Anh Doan");
        work.setStartingDate(new Date());
        work.setEndingDate(new Date());
        work.setStatus(Status.COMPLETE);

        ResponseEntity<Work> postResponse = restTemplate.postForEntity(getRootUrl() + "/works", work, Work.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdateWork() {
        int id = 1;
        Work work = restTemplate.getForObject(getRootUrl() + "/works/" + id, Work.class);
        work.setName("Anh Doan Updated");
        work.setStartingDate(new Date());
        work.setEndingDate(new Date());
        work.setStatus(Status.DOING);

        restTemplate.put(getRootUrl() + "/works/" + id, work);

        Work updatedWork = restTemplate.getForObject(getRootUrl() + "/works/" + id, Work.class);
        assertNotNull(updatedWork);
    }

    @Test
    public void testDeleteWork() {
        int id = 2;
        Work work = restTemplate.getForObject(getRootUrl() + "/works/" + id, Work.class);
        assertNotNull(work);

        restTemplate.delete(getRootUrl() + "/works/" + id);

        try {
            work = restTemplate.getForObject(getRootUrl() + "/works/" + id, Work.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}
