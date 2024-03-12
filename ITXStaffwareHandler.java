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

    public String startCase(String procedureName,String caseDescription,String dstInParam,String activeThread)
    {
        for (int i=0;i<10;i++) {
            System.out.println(activeThread+" of  startCase counter is :---------------------- " + i);
        }
            return "First Method called with Thread "+activeThread;
    }
    public String triggerCase(long caseNumber,String procedureName,String stepName,String dstxInParam,String activeThread)
    {
        for (int i=0;i<10;i++) {
            System.out.println(activeThread+" of  triggerCase counter is :---------------------- " + i);
        }
        return "Second Method called with Thread "+activeThread;
    }
    public String findExistingCases(String procedureName,String filter,String activeThread)
    {

        for (int i=0;i<10;i++) {
            System.out.println(activeThread+" of  findExistingCases counter is :---------------------- " + i);
        }
        return "Third Method called with Thread "+activeThread;

    }
    public void closeHandler()
    {

    }
}
