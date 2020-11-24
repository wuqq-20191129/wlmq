/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.etmcs.service;

import com.goldsign.etmcs.vo.MakeCardParam;
import com.goldsign.etmcs.vo.MakeCardVo;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author lenovo
 */
public class IMakeCardServiceTest {
    
    public IMakeCardServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getMakeCards method, of class IMakeCardService.
     */
    @Test
    public void testGetMakeCards() {
        System.out.println("getMakeCards");
        MakeCardParam makeCardParam = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        List expResult = null;
        List result = instance.getMakeCards(makeCardParam);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMakeCardsByEmployeeId method, of class IMakeCardService.
     */
    @Test
    public void testGetMakeCardsByEmployeeId() {
        System.out.println("getMakeCardsByEmployeeId");
        MakeCardVo vo = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        MakeCardVo expResult = null;
        MakeCardVo result = instance.getMakeCardsByEmployeeId(vo);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of isExistsMakeCard method, of class IMakeCardService.
     */
    @Test
    public void testIsExistsMakeCard() {
        System.out.println("isExistsMakeCard");
        MakeCardVo vo = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        int expResult = 0;
        int result = instance.isExistsMakeCard(vo);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeMakeCard method, of class IMakeCardService.
     */
    @Test
    public void testWriteMakeCard() {
        System.out.println("writeMakeCard");
        MakeCardVo vo = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        Boolean expResult = null;
        Boolean result = instance.writeMakeCard(vo);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeReturnCard method, of class IMakeCardService.
     */
    @Test
    public void testWriteReturnCard() {
        System.out.println("writeReturnCard");
        MakeCardVo vo = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        Boolean expResult = null;
        Boolean result = instance.writeReturnCard(vo);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMakeCardsCount method, of class IMakeCardService.
     */
    @Test
    public void testGetMakeCardsCount() {
        System.out.println("getMakeCardsCount");
        MakeCardParam callParam = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        List expResult = null;
        List result = instance.getMakeCardsCount(callParam);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeLocalFile method, of class IMakeCardService.
     */
    @Test
    public void testWriteLocalFile() throws Exception {
        System.out.println("writeLocalFile");
        MakeCardVo vo = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        File expResult = null;
        File result = instance.writeLocalFile(vo);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of readLocalFile method, of class IMakeCardService.
     */
    @Test
    public void testReadLocalFile() throws Exception {
        System.out.println("readLocalFile");
        File file = null;
        IMakeCardService instance = new IMakeCardServiceImpl();
        Boolean expResult = null;
        Boolean result = instance.readLocalFile(file);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    public class IMakeCardServiceImpl implements IMakeCardService {

        @Override
        public List<Object[]> getMakeCards(MakeCardParam makeCardParam) {
            return null;
        }

        public MakeCardVo getMakeCardsByEmployeeId(MakeCardVo vo) {
            return null;
        }

        public int isExistsMakeCard(MakeCardVo vo) {
            return 0;
        }

        public Boolean writeMakeCard(MakeCardVo vo) {
            return null;
        }

        public Boolean writeReturnCard(MakeCardVo vo) {
            return null;
        }

        public List<Object[]> getMakeCardsCount(MakeCardParam callParam) {
            return null;
        }

        public File writeLocalFile(MakeCardVo vo) throws IOException {
            return null;
        }

        public Boolean readLocalFile(File file) throws IOException {
            return null;
        }

        @Override
        public Boolean editCard(MakeCardVo vo) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Boolean isCardReturned(MakeCardVo vo) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public MakeCardVo getEmployeeInfoService(MakeCardVo vo) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
