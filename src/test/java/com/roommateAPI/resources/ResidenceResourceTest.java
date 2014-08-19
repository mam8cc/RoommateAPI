package com.roommateAPI.resources;

import com.roommateAPI.dao.ResidenceDao;
import com.roommateAPI.models.Residence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResidenceResourceTest {

    @InjectMocks ResidenceResource residenceResource;

    @Mock ResidenceDao residenceDao;

    @Test
    public void itShouldReturnAResidenceModel() throws Exception {
        Residence residence = setupGoodResidence();
        when(residenceDao.selectResidence(anyInt())).thenReturn(residence);

        Response response = residenceResource.getResidence(0);

        assertEquals(residence, response.getEntity());
    }

    @Test(expected = NotFoundException.class)
    public void itShouldThrowANotFoundException() throws Exception {
        when(residenceDao.selectResidence(anyInt())).thenReturn(null);

        residenceResource.getResidence(0);
    }

    private Residence setupGoodResidence() {
        return new Residence();
    }
}
