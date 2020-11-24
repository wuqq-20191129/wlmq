/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goldsign.rule.bo;

import com.goldsign.rule.vo.OperResult;
import java.util.Vector;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Administrator
 */
public class GenerateODServiceTest  extends BaseServiceTest{
    
    public GenerateODServiceTest() {
    }
    
    /**
     * Test of insertOD method, of class GenerateODService.
     */
    @Test
    public void testInsertOD() throws Exception {
        System.out.println("insertOD");
        GenerateODService instance = new GenerateODService();
        OperResult expResult = null;
        OperResult result = instance.insertOD();
        assertEquals(expResult, result);
    }

    /**
     * Test of generateOD method, of class GenerateODService.
     */
    @Test
    public void testGenerateOD() throws Exception {
        System.out.println("generateOD");
        GenerateODService instance = new GenerateODService();
        OperResult expResult = null;
        OperResult result = instance.generateOD();
//        assertEquals(expResult, result);
    }
}
