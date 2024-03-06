package com;


public class ITXStaffwareHandler {
    private  static ITXStaffwareHandler iTXStaffwareHandler =null;
    public static synchronized ITXStaffwareHandler getInstance()
    {
        if (iTXStaffwareHandler ==null)
        {
            iTXStaffwareHandler =new ITXStaffwareHandler();
        }
        return iTXStaffwareHandler;
    }

    public String startCase(String procedureName,String caseDescription,String dstInParam)
    {
        return "First Method called with Thread "+Thread.currentThread().getName();

    }
    public String triggerCase(long caseNumber,String procedureName,String stepName,String dstxInParam)
    {
        return "Second Method called with Thread "+Thread.currentThread().getName();

    }
    public String findExistingCases(String procedureName,String filter)
    {
        return "Third Method called with Thread "+Thread.currentThread().getName();

    }
    public void closeHandler()
    {

    }
}
