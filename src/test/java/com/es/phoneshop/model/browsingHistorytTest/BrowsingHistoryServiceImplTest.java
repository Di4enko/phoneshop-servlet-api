package com.es.phoneshop.model.browsingHistorytTest;

import com.es.phoneshop.model.browsingHistory.BrowsingHistory;
import com.es.phoneshop.service.browsingHistoryService.BrowsingHistoryService;
import com.es.phoneshop.service.browsingHistoryService.browsingHistoryServiceImp.BrowsingHistoryServiceImpl;
import com.es.phoneshop.model.product.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BrowsingHistoryServiceImplTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private BrowsingHistory browsingHistory;
    @Mock
    private Product product;
    @Mock
    private HttpSession httpSession;
    private BrowsingHistoryService browsingHistoryService = BrowsingHistoryServiceImpl.getInstance();

    @Before
    public void setup() {
        when(request.getSession()).thenReturn(httpSession);
    }

    @Test
    public void addProductTest() {
        browsingHistoryService.add(browsingHistory, product);
        verify(browsingHistory).add(eq(product));
    }

    @Test
    public void getBrowsingHistoryTest() {
        browsingHistoryService.getBrowsingHistory(request);
        verify(request,times(2)).getSession();
    }
}
