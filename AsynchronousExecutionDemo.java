package com;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsynchronousExecutionDemo {
    public static String startCase(String procedureName, String caseDescription, String dstInParam) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture<String> threadFuture = new CompletableFuture<>();
          CompletableFuture.supplyAsync(() -> {
                    startedThreadFutue("startCase",threadFuture);
                    return ITXStaffwareHandler.getInstance().startCase(procedureName, caseDescription, dstInParam,Thread.currentThread().getName());
                }, executorService)
                .whenComplete((response, error) -> {
                    printMessageOrException(error, response, executorService,future);
        });
        return printMessageOrException(future,threadFuture);
    }


    public static String triggerCase(long caseNumber, String procedureName, String stepName, String dstxInParam) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture<String> threadFuture = new CompletableFuture<>();
        CompletableFuture.supplyAsync(() -> {
                    startedThreadFutue("trigger Case",threadFuture);
                        return ITXStaffwareHandler.getInstance().triggerCase(caseNumber, procedureName, stepName, dstxInParam,Thread.currentThread().getName());
                }, executorService)
                .whenComplete((response, error) -> {
                    printMessageOrException(error, response, executorService,future);
                });
        return printMessageOrException(future,threadFuture);
    }

    public static String findExistingCases(String procedureName, String filter) {
        ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        CompletableFuture<String> future = new CompletableFuture<>();
        CompletableFuture<String> threadFuture = new CompletableFuture<>();
        CompletableFuture.supplyAsync(() -> {
                    startedThreadFutue("find Existing Cases",threadFuture);
                        return ITXStaffwareHandler.getInstance().findExistingCases(procedureName, filter,Thread.currentThread().getName());

                }, executorService)
                .whenComplete((response, error) -> {
                    printMessageOrException(error, response, executorService,future);
                });
        return printMessageOrException(future,threadFuture);

    }

    private static void startedThreadFutue( String processName , CompletableFuture<String> threadFuture) {

        String activeThreadName=Thread.currentThread().getName();
        System.out.println("Start Case Method is called "+activeThreadName);
        threadFuture.complete(activeThreadName);

    }
    private static void printMessageOrException(Throwable error, String response, ExecutorService executorService, CompletableFuture<String> future) {
        executorService.shutdownNow();
        if (error == null) {
            future.complete(response);
        } else {
            future.completeExceptionally(error);
        }
    }

    public static String printMessageOrException(CompletableFuture<String> future,CompletableFuture<String> threadFuture)
    {
        String result="";
        String actithread="";
        try {
            result = future.get();
            actithread=threadFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Exception while getting the result of the future. " + e.getMessage());
        }
        System.out.println("Thread "+actithread+"Successfully completed printing result below \n");
        return result;
    }
    public static void close()throws Exception{
        ITXStaffwareHandler.getInstance().closeHandler();
    }
   public static void main_demo(String[] args) {
        // TODO Auto-generated method stub
        System.clearProperty("sw_log4j_config");
        long start=System.currentTimeMillis();
        try {
            //1.start 2.find 3.trigger

            switch(args[0]) {
                case "1":
                    System.out.println( startCase(args[1],args[2],args[3]));
                    break;
                case "2":
                    System.out.println(triggerCase(Long.parseLong(args[1]),args[2],args[3],args[4]));
                    break;
                case "3":
                    System.out.println(findExistingCases(args[1],args[2]));
                    break;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }

        long end=System.currentTimeMillis();
        System.out.println("Elapsed Time:" +(end - start));
    }


    public static void main(String[] args) {
        long start=System.currentTimeMillis();
        for (int i = 0; i < 2; i++) {
            System.out.println("============================================================================================\n\n");
            System.out.println( startCase("procedureName", "caseDescription", "dstInParam"));
            System.out.println("============================================================================================\n\n");
             System.out.println( triggerCase(1L, "procedureName", "caseDescription", "dstInParam"));
            System.out.println("============================================================================================\n\n");
             System.out.println( findExistingCases("procedureName", "filter"));
            System.out.println("============================================================================================\n\n");
        }

        long end=System.currentTimeMillis();
        System.out.println("Elapsed Time:" +(end - start));
    }
}
